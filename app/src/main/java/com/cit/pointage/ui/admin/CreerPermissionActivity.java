package com.cit.pointage.ui.admin;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.cit.pointage.R;
import com.cit.pointage.databinding.ActivityCreerPermissionBinding;
import com.cit.pointage.model.request.PermissionRequest;
import com.cit.pointage.model.response.PermissionResponse;
import com.cit.pointage.model.response.UtilisateurResponse;
import com.cit.pointage.repository.PermissionRepository;
import com.cit.pointage.viewmodel.UtilisateurViewModel;

import java.util.Calendar;

public class CreerPermissionActivity
        extends AppCompatActivity {

    private ActivityCreerPermissionBinding binding;
    private UtilisateurViewModel utilisateurViewModel;
    private PermissionRepository permissionRepository;

    private String utilisateurIdSelectionne = null;
    private boolean saisieManuelle = false;

    // ✅ Imports corrects — types simples
    private final MutableLiveData<PermissionResponse>
            permissionSuccess = new MutableLiveData<>();
    private final MutableLiveData<String>
            permissionError = new MutableLiveData<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCreerPermissionBinding
                .inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        utilisateurViewModel = new ViewModelProvider(this)
                .get(UtilisateurViewModel.class);
        permissionRepository = new PermissionRepository();

        setupToolbar();
        setupRecherche();
        setupDatePickers();
        setupTypeSaisie();
        setupBouton();
        observerViewModel();
    }

    private void setupToolbar() {
        setSupportActionBar(binding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar()
                    .setDisplayHomeAsUpEnabled(true);
        }
        binding.toolbar.setNavigationOnClickListener(
                v -> finish());
    }

    private void setupRecherche() {
        binding.btnRechercher.setOnClickListener(
                v -> lancerRecherche());

        binding.etRecherche.setOnEditorActionListener(
                (v, actionId, event) -> {
                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                        lancerRecherche();
                        return true;
                    }
                    return false;
                });
    }

    private void lancerRecherche() {
        String critere = binding.etRecherche
                .getText() != null
                ? binding.etRecherche.getText()
                .toString().trim()
                : "";
        if (critere.isEmpty()) return;
        utilisateurViewModel.rechercher(critere);
    }

    private void setupDatePickers() {
        binding.etDateDebut.setOnClickListener(
                v -> afficherDatePicker(true));
        binding.etDateFin.setOnClickListener(
                v -> afficherDatePicker(false));
    }

    private void afficherDatePicker(boolean isDebut) {
        Calendar cal = Calendar.getInstance();

        DatePickerDialog dialog = new DatePickerDialog(
                this,
                (view, year, month, dayOfMonth) -> {
                    String date = String.format(
                            "%d-%02d-%02d",
                            year, month + 1, dayOfMonth);
                    if (isDebut) {
                        binding.etDateDebut.setText(date);
                    } else {
                        binding.etDateFin.setText(date);
                    }
                    verifierFormulaire();
                },
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH));

        dialog.getDatePicker().setMinDate(
                cal.getTimeInMillis());
        dialog.show();
    }

    private void setupTypeSaisie() {

        binding.cardSaisieSysteme
                .setOnClickListener(v -> {
                    saisieManuelle = false;
                    binding.tvSystemeSelectionne.setText("●");
                    binding.tvSystemeSelectionne.setTextColor(
                            getColor(R.color.olive_500));
                    binding.tvManuelleSelectionne.setText("○");
                    binding.tvManuelleSelectionne.setTextColor(
                            getColor(R.color.ink_4));
                    verifierFormulaire();
                });

        binding.cardSaisieManuelle
                .setOnClickListener(v -> {
                    saisieManuelle = true;
                    binding.tvManuelleSelectionne.setText("●");
                    binding.tvManuelleSelectionne.setTextColor(
                            getColor(R.color.olive_500));
                    binding.tvSystemeSelectionne.setText("○");
                    binding.tvSystemeSelectionne.setTextColor(
                            getColor(R.color.ink_4));
                    verifierFormulaire();
                });
    }

    private void verifierFormulaire() {
        String dateDebut = binding.etDateDebut
                .getText() != null
                ? binding.etDateDebut.getText()
                .toString().trim() : "";
        String dateFin = binding.etDateFin
                .getText() != null
                ? binding.etDateFin.getText()
                .toString().trim() : "";

        binding.btnCreer.setEnabled(
                utilisateurIdSelectionne != null
                        && !dateDebut.isEmpty()
                        && !dateFin.isEmpty());
    }

    private void setupBouton() {
        binding.btnCreer.setOnClickListener(
                v -> creerPermission());
    }

    private void creerPermission() {
        String dateDebut = binding.etDateDebut
                .getText().toString().trim();
        String dateFin = binding.etDateFin
                .getText().toString().trim();

        cacherErreur();
        afficherChargement(true);

        PermissionRequest request =
                new PermissionRequest(
                        utilisateurIdSelectionne,
                        dateDebut,
                        dateFin,
                        saisieManuelle);

        permissionRepository.creer(
                request,
                permissionSuccess,
                permissionError);
    }

    private void observerViewModel() {

        // Résultats recherche
        utilisateurViewModel.resultatsRecherche
                .observe(this, resultats -> {
                    if (resultats != null
                            && !resultats.isEmpty()) {
                        selectionnerUtilisateur(
                                resultats.get(0));
                    }
                });

        // ✅ Permission créée
        permissionSuccess.observe(this, permission -> {
            afficherChargement(false);
            if (permission != null) {
                setResult(RESULT_OK);
                finish();
            }
        });

        // ❌ Erreur
        permissionError.observe(this, erreur -> {
            afficherChargement(false);
            if (erreur != null) {
                afficherErreur(erreur);
            }
        });
    }

    private void selectionnerUtilisateur(
            UtilisateurResponse u) {
        utilisateurIdSelectionne = u.getId();

        binding.cardUtilisateur
                .setVisibility(View.VISIBLE);
        binding.tvNomComplet.setText(u.getNomComplet());
        binding.tvMatricule.setText(u.getMatricule());

        if (u.getPhotoUrl() != null
                && !u.getPhotoUrl().isEmpty()) {
            Glide.with(this)
                    .load(u.getPhotoUrl())
                    .circleCrop()
                    .placeholder(R.color.olive_100)
                    .into(binding.ivAvatar);
        }

        verifierFormulaire();
    }

    private void afficherErreur(String message) {
        binding.tvErreur.setText(message);
        binding.tvErreur.setVisibility(View.VISIBLE);
    }

    private void cacherErreur() {
        binding.tvErreur.setVisibility(View.GONE);
    }

    private void afficherChargement(boolean afficher) {
        binding.progressBar.setVisibility(
                afficher ? View.VISIBLE : View.GONE);
        binding.btnCreer.setEnabled(!afficher);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}