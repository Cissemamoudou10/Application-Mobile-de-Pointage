package com.cit.pointage.ui.controleur;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.cit.pointage.R;
import com.cit.pointage.databinding.ActivityScanBinding;
import com.cit.pointage.model.response.PointageResponse;
import com.cit.pointage.viewmodel.PointageViewModel;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

public class ScanActivity extends AppCompatActivity {

    private ActivityScanBinding binding;
    private PointageViewModel pointageViewModel;
    private String typePointage;

    // Launcher pour le scan ZXing
    private final ActivityResultLauncher<ScanOptions>
            barcodeLauncher = registerForActivityResult(
            new ScanContract(),
            result -> {
                if (result.getContents() != null) {
                    // QR code détecté
                    String qrCode = result.getContents();
                    traiterScan(qrCode);
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityScanBinding
                .inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Récupère le type de pointage passé par
        // ControleurActivity
        typePointage = getIntent()
                .getStringExtra("TYPE_POINTAGE");

        pointageViewModel = new ViewModelProvider(this)
                .get(PointageViewModel.class);

        setupToolbar();
        setupUI();
        observerViewModel();
    }

    private void setupToolbar() {
        setSupportActionBar(binding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar()
                    .setDisplayHomeAsUpEnabled(true);
            getSupportActionBar()
                    .setDisplayShowTitleEnabled(false);
        }
        binding.toolbar.setNavigationOnClickListener(
                v -> finish());
    }

    private void setupUI() {
        // Configure l'affichage selon le type de pointage
        switch (typePointage) {
            case "ENTREE":
                binding.tvIconeType.setText("→");
                binding.tvIconeType.setTextColor(
                        getColor(R.color.status_ok_fg));
                binding.tvTypePointage.setText("Entrée");
                binding.tvInstruction.setText(
                        "Présentez le badge à scanner.");
                binding.btnScanner.setText(
                        "Scanner le badge");
                break;

            case "SORTIE":
                binding.tvIconeType.setText("←");
                binding.tvIconeType.setTextColor(
                        getColor(R.color.status_err_fg));
                binding.tvTypePointage.setText("Sortie");
                binding.tvInstruction.setText(
                        "Présentez le badge à scanner.");
                binding.btnScanner.setText(
                        "Scanner le badge");
                break;

            case "DEPART_PERMISSION":
                binding.tvIconeType.setText("↗");
                binding.tvIconeType.setTextColor(
                        getColor(R.color.status_warn_fg));
                binding.tvTypePointage.setText(
                        "Départ permission");
                binding.tvInstruction.setText(
                        "Présentez le titre de permission.");
                binding.btnScanner.setText(
                        "Scanner le titre");
                break;

            case "RETOUR_PERMISSION":
                binding.tvIconeType.setText("↙");
                binding.tvIconeType.setTextColor(
                        getColor(R.color.status_info_fg));
                binding.tvTypePointage.setText(
                        "Retour permission");
                binding.tvInstruction.setText(
                        "Présentez le titre de permission.");
                binding.btnScanner.setText(
                        "Scanner le titre");
                break;
        }

        // Lance le scan au clic
        binding.btnScanner.setOnClickListener(
                v -> lancerScanner());
    }

    private void lancerScanner() {
        ScanOptions options = new ScanOptions();
        options.setPrompt(
                "Placez le QR code dans le cadre");
        options.setBeepEnabled(true);
        options.setOrientationLocked(true);
        options.setBarcodeImageEnabled(false);
        barcodeLauncher.launch(options);
    }

    private void traiterScan(String qrCode) {
        // Affiche le chargement
        afficherChargement(true);

        // Envoie le pointage au backend
        pointageViewModel.scannerBadge(
                qrCode, typePointage, false);
    }

    private void observerViewModel() {

        // ✅ Succès
        pointageViewModel.pointageSuccess.observe(
                this, this::afficherResultat);

        // ❌ Erreur
        pointageViewModel.error.observe(this, erreur -> {
            afficherChargement(false);
            if (erreur != null) {
                afficherErreur(erreur);
            }
        });
    }

    private void afficherResultat(
            PointageResponse pointage) {
        afficherChargement(false);

        // Affiche la carte résultat
        binding.cardResultat.setVisibility(View.VISIBLE);

        // Photo
        if (pointage.getPhotoUrl() != null
                && !pointage.getPhotoUrl().isEmpty()) {
            Glide.with(this)
                    .load(pointage.getPhotoUrl())
                    .circleCrop()
                    .placeholder(R.color.olive_100)
                    .into(binding.ivPhoto);
        }

        // Nom et matricule
        binding.tvNomComplet.setText(
                pointage.getNomCompletUtilisateur());
        binding.tvMatricule.setText(
                pointage.getMatricule());

        // Statut
        if (pointage.isEstRetard()) {
            binding.tvStatutPointage.setText(
                    "Pointage enregistré — retard signalé.");
            binding.tvStatutPointage.setTextColor(
                    getColor(R.color.status_warn_fg));
            binding.tvStatutPointage
                    .setBackgroundResource(
                            R.drawable.bg_statut_warn);
        } else {
            binding.tvStatutPointage.setText(
                    "Pointage enregistré.");
            binding.tvStatutPointage.setTextColor(
                    getColor(R.color.status_ok_fg));
            binding.tvStatutPointage
                    .setBackgroundResource(
                            R.drawable.bg_statut_ok);
        }
    }

    private void afficherErreur(String message) {
        binding.cardResultat.setVisibility(View.VISIBLE);
        binding.tvStatutPointage.setText(message);
        binding.tvStatutPointage.setTextColor(
                getColor(R.color.status_err_fg));
        binding.tvStatutPointage.setBackgroundResource(
                R.drawable.bg_statut_err);
        binding.tvNomComplet.setText("");
        binding.tvMatricule.setText("");
    }

    private void afficherChargement(boolean afficher) {
        binding.progressBar.setVisibility(
                afficher ? View.VISIBLE : View.GONE);
        binding.btnScanner.setEnabled(!afficher);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}