package com.cit.pointage.ui.superadmin;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.cit.pointage.databinding.ActivityJournalPertesBinding;
import com.cit.pointage.viewmodel.PerteViewModel;
import com.google.android.material.snackbar.Snackbar;

public class JournalPertesActivity extends AppCompatActivity {

    private ActivityJournalPertesBinding binding;
    private PerteViewModel viewModel;
    private PerteAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityJournalPertesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(PerteViewModel.class);

        setupToolbar();
        setupRecyclerView();
        observerViewModel();

        // Charge les données
        viewModel.chargerTout();
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
        adapter = new PerteAdapter();
        binding.rvPertes.setLayoutManager(new LinearLayoutManager(this));
        binding.rvPertes.setAdapter(adapter);
    }

    private void observerViewModel() {
        // Liste
        viewModel.pertes.observe(this, pertes -> {
            afficherChargement(false);
            if (pertes == null || pertes.isEmpty()) {
                binding.layoutVide.setVisibility(View.VISIBLE);
                binding.rvPertes.setVisibility(View.GONE);
            } else {
                binding.layoutVide.setVisibility(View.GONE);
                binding.rvPertes.setVisibility(View.VISIBLE);
                adapter.setPertes(pertes);
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