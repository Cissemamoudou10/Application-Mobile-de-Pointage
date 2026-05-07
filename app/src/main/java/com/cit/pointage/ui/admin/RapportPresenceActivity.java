package com.cit.pointage.ui.admin;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.cit.pointage.databinding.ActivityRapportPresenceBinding;
import com.cit.pointage.viewmodel.PointageViewModel;
import com.google.android.material.snackbar.Snackbar;

public class RapportPresenceActivity extends AppCompatActivity {

    private ActivityRapportPresenceBinding binding;
    private PointageViewModel viewModel;
    private PointageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRapportPresenceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(PointageViewModel.class);

        setupToolbar();
        setupRecyclerView();
        observerViewModel();

        // Charge les données
        viewModel.chargerPresenceRecente();
    }

    private void setupToolbar() {
        setSupportActionBar(binding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        binding.toolbar.setNavigationOnClickListener(v -> finish());
    }

    private void setupRecyclerView() {
        adapter = new PointageAdapter();
        binding.rvPresence.setLayoutManager(new LinearLayoutManager(this));
        binding.rvPresence.setAdapter(adapter);
    }

    private void observerViewModel() {
        // Liste de présence
        viewModel.presenceRecente.observe(this, presences -> {
            afficherChargement(false);
            if (presences == null || presences.isEmpty()) {
                binding.layoutVide.setVisibility(View.VISIBLE);
                binding.rvPresence.setVisibility(View.GONE);
            } else {
                binding.layoutVide.setVisibility(View.GONE);
                binding.rvPresence.setVisibility(View.VISIBLE);
                adapter.setPointages(presences);
            }
        });

        // Erreur
        viewModel.error.observe(this, erreur -> {
            afficherChargement(false);
            if (erreur != null) afficherMessage(erreur);
        });

        // Chargement
        viewModel.loading.observe(this, this::afficherChargement);
    }

    private void afficherChargement(boolean afficher) {
        binding.progressBar.setVisibility(afficher ? View.VISIBLE : View.GONE);
    }

    private void afficherMessage(String message) {
        Snackbar.make(binding.getRoot(), message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}