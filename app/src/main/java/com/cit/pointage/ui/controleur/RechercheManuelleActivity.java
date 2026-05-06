package com.cit.pointage.ui.controleur;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.cit.pointage.databinding
        .ActivityRechercheManuelleBinding;
import com.cit.pointage.model.response.UtilisateurResponse;
import com.cit.pointage.viewmodel.UtilisateurViewModel;

public class RechercheManuelleActivity
        extends AppCompatActivity
        implements UtilisateurAdapter
        .OnUtilisateurClickListener {

    private ActivityRechercheManuelleBinding binding;
    private UtilisateurViewModel viewModel;
    private UtilisateurAdapter adapter;
    private String typePointage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityRechercheManuelleBinding
                .inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        typePointage = getIntent()
                .getStringExtra("TYPE_POINTAGE");

        viewModel = new ViewModelProvider(this)
                .get(UtilisateurViewModel.class);

        setupToolbar();
        setupRecyclerView();
        setupRecherche();
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

    private void setupRecyclerView() {
        adapter = new UtilisateurAdapter(this);
        binding.rvResultats.setLayoutManager(
                new LinearLayoutManager(this));
        binding.rvResultats.setAdapter(adapter);
    }

    private void setupRecherche() {
        binding.btnRechercher.setOnClickListener(
                v -> lancerRecherche()); // ← supprimez l'espace

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

        afficherChargement(true);
        viewModel.rechercher(critere);
    }

    private void observerViewModel() {

        viewModel.resultatsRecherche.observe(
                this, resultats -> {
                    afficherChargement(false);

                    if (resultats != null
                            && !resultats.isEmpty()) {
                        adapter.setUtilisateurs(resultats);
                        binding.rvResultats
                                .setVisibility(View.VISIBLE);
                        binding.tvVide
                                .setVisibility(View.GONE);
                    } else {
                        binding.rvResultats
                                .setVisibility(View.GONE);
                        binding.tvVide
                                .setVisibility(View.VISIBLE);
                    }
                });

        viewModel.error.observe(this, erreur -> {
            afficherChargement(false);
        });
    }

    @Override
    public void onUtilisateurClick(
            UtilisateurResponse u) {
        // Envoie l'utilisateur sélectionné
        // à ControleurActivity pour pointage manuel
        Intent intent = new Intent();
        intent.putExtra("UTILISATEUR_ID", u.getId());
        intent.putExtra("NOM_COMPLET",
                u.getNomComplet());
        intent.putExtra("MATRICULE", u.getMatricule());
        intent.putExtra("TYPE_POINTAGE", typePointage);
        setResult(RESULT_OK, intent);
        finish();
    }

    private void afficherChargement(boolean afficher) {
        binding.progressBar.setVisibility(
                afficher ? View.VISIBLE : View.GONE);
        binding.btnRechercher.setEnabled(!afficher);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}