package com.cit.pointage.ui.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.cit.pointage.databinding
        .ActivityListeUtilisateursBinding;
import com.cit.pointage.model.response
        .UtilisateurResponse;
import com.cit.pointage.ui.controleur
        .UtilisateurAdapter;
import com.cit.pointage.viewmodel.UtilisateurViewModel;

public class ListeUtilisateursActivity
        extends AppCompatActivity
        implements UtilisateurAdapter
        .OnUtilisateurClickListener {

    private ActivityListeUtilisateursBinding binding;
    private UtilisateurViewModel viewModel;
    private UtilisateurAdapter adapter;

    private static final int REQUEST_CREER = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityListeUtilisateursBinding
                .inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this)
                .get(UtilisateurViewModel.class);

        setupToolbar();
        setupRecyclerView();
        setupRecherche();
        setupFab();
        observerViewModel();
        chargerUtilisateurs();
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

    private void setupRecyclerView() {
        adapter = new UtilisateurAdapter(this);
        binding.rvUtilisateurs.setLayoutManager(
                new LinearLayoutManager(this));
        binding.rvUtilisateurs.setAdapter(adapter);
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

        afficherChargement(true);

        if (critere.isEmpty()) {
            chargerUtilisateurs();
        } else {
            viewModel.rechercher(critere);
        }
    }

    private void setupFab() {
        binding.fabAjouter.setOnClickListener(v -> {
            Intent intent = new Intent(this,
                    CreerUtilisateurActivity.class);
            startActivityForResult(intent,
                    REQUEST_CREER);
        });
    }

    private void chargerUtilisateurs() {
        afficherChargement(true);
        viewModel.chargerTous();
    }

    private void observerViewModel() {

        // Liste complète
        viewModel.utilisateurs.observe(
                this, utilisateurs -> {
                    afficherChargement(false);
                    afficherListe(utilisateurs != null
                            ? utilisateurs
                            : new java.util.ArrayList<>());
                });

        // Résultats recherche
        viewModel.resultatsRecherche.observe(
                this, resultats -> {
                    afficherChargement(false);
                    afficherListe(resultats != null
                            ? resultats
                            : new java.util.ArrayList<>());
                });

        viewModel.error.observe(this, erreur -> {
            afficherChargement(false);
        });
    }

    private void afficherListe(
            java.util.List<UtilisateurResponse> liste) {
        if (liste.isEmpty()) {
            binding.rvUtilisateurs
                    .setVisibility(View.GONE);
            binding.tvVide.setVisibility(View.VISIBLE);
        } else {
            adapter.setUtilisateurs(liste);
            binding.rvUtilisateurs
                    .setVisibility(View.VISIBLE);
            binding.tvVide.setVisibility(View.GONE);
        }
    }

    @Override
    public void onUtilisateurClick(
            UtilisateurResponse u) {
        // TODO — ouvrir détail utilisateur
        // Pour l'instant on affiche un dialogue
        new androidx.appcompat.app.AlertDialog.Builder(
                this)
                .setTitle(u.getNomComplet())
                .setMessage(
                        "Matricule : " + u.getMatricule()
                                + "\nCatégorie : "
                                + u.getCategorie()
                                + "\nStatut : " + u.getStatut())
                .setPositiveButton("Fermer", null)
                .show();
    }

    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode, Intent data) {
        super.onActivityResult(
                requestCode, resultCode, data);

        // Rechargement après création
        if (requestCode == REQUEST_CREER
                && resultCode == RESULT_OK) {
            chargerUtilisateurs();
        }
    }

    private void afficherChargement(boolean afficher) {
        binding.progressBar.setVisibility(
                afficher ? View.VISIBLE : View.GONE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}