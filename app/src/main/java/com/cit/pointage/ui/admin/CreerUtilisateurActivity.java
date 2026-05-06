package com.cit.pointage.ui.admin;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.cit.pointage.R;
import com.cit.pointage.databinding
        .ActivityCreerUtilisateurBinding;
import com.cit.pointage.model.request
        .UtilisateurRequest;
import com.cit.pointage.viewmodel.UtilisateurViewModel;

public class CreerUtilisateurActivity
        extends AppCompatActivity {

    private ActivityCreerUtilisateurBinding binding;
    private UtilisateurViewModel viewModel;
    private String categorieSelectionnee = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCreerUtilisateurBinding
                .inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this)
                .get(UtilisateurViewModel.class);

        setupToolbar();
        setupCategories();
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

    private void setupCategories() {

        binding.cardEleve.setOnClickListener(v ->
                selectionnerCategorie("ELEVE"));

        binding.cardOfficier.setOnClickListener(v ->
                selectionnerCategorie(
                        "OFFICIER_STAGIAIRE"));

        binding.cardPersonnelAdmin
                .setOnClickListener(v ->
                        selectionnerCategorie(
                                "PERSONNEL_ADMIN"));

        binding.cardPersonnelEncadrant
                .setOnClickListener(v ->
                        selectionnerCategorie(
                                "PERSONNEL_ENCADRANT"));
    }

    private void selectionnerCategorie(String cat) {
        categorieSelectionnee = cat;

        // Remet tous les indicateurs à ○
        binding.tvEleveSelectionne.setText("○");
        binding.tvEleveSelectionne.setTextColor(
                getColor(R.color.ink_4));
        binding.tvOfficierSelectionne.setText("○");
        binding.tvOfficierSelectionne.setTextColor(
                getColor(R.color.ink_4));
        binding.tvPersonnelAdminSelectionne.setText("○");
        binding.tvPersonnelAdminSelectionne
                .setTextColor(getColor(R.color.ink_4));
        binding.tvPersonnelEncadrantSelectionne
                .setText("○");
        binding.tvPersonnelEncadrantSelectionne
                .setTextColor(getColor(R.color.ink_4));

        // Active l'indicateur sélectionné
        switch (cat) {
            case "ELEVE":
                binding.tvEleveSelectionne.setText("●");
                binding.tvEleveSelectionne.setTextColor(
                        getColor(R.color.olive_500));
                break;
            case "OFFICIER_STAGIAIRE":
                binding.tvOfficierSelectionne
                        .setText("●");
                binding.tvOfficierSelectionne
                        .setTextColor(
                                getColor(R.color.olive_500));
                break;
            case "PERSONNEL_ADMIN":
                binding.tvPersonnelAdminSelectionne
                        .setText("●");
                binding.tvPersonnelAdminSelectionne
                        .setTextColor(
                                getColor(R.color.olive_500));
                break;
            case "PERSONNEL_ENCADRANT":
                binding.tvPersonnelEncadrantSelectionne
                        .setText("●");
                binding.tvPersonnelEncadrantSelectionne
                        .setTextColor(
                                getColor(R.color.olive_500));
                break;
        }

        verifierFormulaire();
    }

    private void verifierFormulaire() {
        String matricule = binding.etMatricule
                .getText() != null
                ? binding.etMatricule.getText()
                .toString().trim() : "";
        String nom = binding.etNom.getText() != null
                ? binding.etNom.getText()
                .toString().trim() : "";
        String prenom = binding.etPrenom
                .getText() != null
                ? binding.etPrenom.getText()
                .toString().trim() : "";

        binding.btnCreer.setEnabled(
                !matricule.isEmpty()
                        && !nom.isEmpty()
                        && !prenom.isEmpty()
                        && categorieSelectionnee != null);
    }

    private void setupBouton() {

        // Vérification dynamique des champs
        binding.etMatricule.addTextChangedListener(
                new SimpleTextWatcher(
                        () -> verifierFormulaire()));
        binding.etNom.addTextChangedListener(
                new SimpleTextWatcher(
                        () -> verifierFormulaire()));
        binding.etPrenom.addTextChangedListener(
                new SimpleTextWatcher(
                        () -> verifierFormulaire()));

        binding.btnCreer.setOnClickListener(
                v -> creerUtilisateur());
    }

    private void creerUtilisateur() {
        String matricule = binding.etMatricule
                .getText().toString().trim();
        String nom = binding.etNom
                .getText().toString().trim();
        String prenom = binding.etPrenom
                .getText().toString().trim();

        cacherErreur();
        afficherChargement(true);

        UtilisateurRequest request =
                new UtilisateurRequest(
                        matricule, nom, prenom,
                        null,
                        categorieSelectionnee);

        viewModel.creer(request);
    }

    private void observerViewModel() {

        viewModel.createSuccess.observe(
                this, utilisateur -> {
                    afficherChargement(false);
                    if (utilisateur != null) {
                        // Succès — retour avec résultat
                        setResult(RESULT_OK);
                        finish();
                    }
                });

        viewModel.error.observe(this, erreur -> {
            afficherChargement(false);
            if (erreur != null) {
                afficherErreur(erreur);
            }
        });
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