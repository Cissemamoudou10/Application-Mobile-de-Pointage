package com.cit.pointage.ui.controleur;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.cit.pointage.R;
import com.cit.pointage.databinding
        .ActivityControleurBinding;
import com.cit.pointage.ui.auth.LoginActivity;
import com.cit.pointage.utils.NetworkUtils;
import com.cit.pointage.utils.SessionManager;
import com.cit.pointage.viewmodel.PointageViewModel;

public class ControleurActivity extends AppCompatActivity {

    private ActivityControleurBinding binding;
    private SessionManager sessionManager;
    private PointageViewModel pointageViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityControleurBinding
                .inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sessionManager = new SessionManager(this);
        pointageViewModel = new ViewModelProvider(this)
                .get(PointageViewModel.class);

        setupToolbar();
        setupStatutConnexion();
        setupBoutons();
        observerViewModel();
    }

    private void setupToolbar() {
        setSupportActionBar(binding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar()
                    .setDisplayShowTitleEnabled(false);
        }
        binding.tvToolbarSousTitre.setText(
                sessionManager.getNomComplet());
    }

    private void setupStatutConnexion() {
        boolean enLigne = NetworkUtils.isConnecte(this);

        if (enLigne) {
            binding.tvStatutConnexion.setText("En ligne");
            binding.tvStatutConnexion.setTextColor(
                    getColor(R.color.status_ok_fg));
            binding.layoutStatutConnexion
                    .setBackgroundResource(
                            R.drawable.bg_statut_connexion);
        } else {
            binding.tvStatutConnexion.setText(
                    "Hors ligne — pointage désactivé");
            binding.tvStatutConnexion.setTextColor(
                    getColor(R.color.status_err_fg));
        }
    }

    private void setupBoutons() {

        binding.cardEntree.setOnClickListener(v ->
                lancerScan("ENTREE"));

        binding.cardSortie.setOnClickListener(v ->
                lancerScan("SORTIE"));

        binding.cardDepartPermission.setOnClickListener(
                v -> lancerScan("DEPART_PERMISSION"));

        binding.cardRetourPermission.setOnClickListener(
                v -> lancerScan("RETOUR_PERMISSION"));

        binding.btnPointageManuel.setOnClickListener(
                v -> afficherDialogueTypePointage());

        binding.btnSignalerPerte.setOnClickListener(v ->
                startActivity(new Intent(
                        this,
                        SignalerPerteActivity.class)));
    }

    private void lancerScan(String typePointage) {
        if (!NetworkUtils.isConnecte(this)) {
            afficherMessage(getString(
                    R.string.pointage_hors_ligne));
            return;
        }
        Intent intent = new Intent(
                this, ScanActivity.class);
        intent.putExtra("TYPE_POINTAGE", typePointage);
        startActivity(intent);
    }

    private void afficherDialogueTypePointage() {
        String[] types = {
                "Entrée",
                "Sortie",
                "Départ permission",
                "Retour permission"
        };
        String[] valeurs = {
                "ENTREE",
                "SORTIE",
                "DEPART_PERMISSION",
                "RETOUR_PERMISSION"
        };

        new androidx.appcompat.app.AlertDialog.Builder(this)
                .setTitle("Type de pointage")
                .setItems(types, (dialog, which) -> {
                    Intent intent = new Intent(
                            this,
                            RechercheManuelleActivity.class);
                    intent.putExtra(
                            "TYPE_POINTAGE",
                            valeurs[which]);
                    startActivityForResult(intent, 100);
                })
                .setNegativeButton(
                        getString(R.string.action_annuler),
                        null)
                .show();
    }

    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode, Intent data) {
        super.onActivityResult(
                requestCode, resultCode, data);

        if (requestCode == 100
                && resultCode == RESULT_OK
                && data != null) {

            String utilisateurId =
                    data.getStringExtra("UTILISATEUR_ID");
            String typePointage =
                    data.getStringExtra("TYPE_POINTAGE");

            afficherChargement(true);
            pointageViewModel.validerManuellement(
                    utilisateurId,
                    typePointage,
                    false
            );
        }
    }

    private void observerViewModel() {

        pointageViewModel.pointageSuccess.observe(
                this, pointage -> {
                    afficherChargement(false);
                    if (pointage != null) {
                        String message = pointage.isEstRetard()
                                ? getString(R.string.pointage_retard)
                                : getString(
                                R.string.pointage_success);
                        afficherMessage(message);
                    }
                });

        pointageViewModel.error.observe(this, erreur -> {
            afficherChargement(false);
            if (erreur != null) {
                afficherMessage(erreur);
            }
        });
    }

    private void afficherMessage(String message) {
        com.google.android.material.snackbar.Snackbar
                .make(binding.getRoot(),
                        message,
                        com.google.android.material
                                .snackbar.Snackbar.LENGTH_LONG)
                .show();
    }

    private void afficherChargement(boolean afficher) {
        binding.cardEntree.setEnabled(!afficher);
        binding.cardSortie.setEnabled(!afficher);
        binding.cardDepartPermission
                .setEnabled(!afficher);
        binding.cardRetourPermission
                .setEnabled(!afficher);
        binding.btnPointageManuel.setEnabled(!afficher);
        binding.btnSignalerPerte.setEnabled(!afficher);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(
                R.menu.menu_controleur, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_deconnecter) {
            sessionManager.deconnecter();
            startActivity(new Intent(
                    this, LoginActivity.class));
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}