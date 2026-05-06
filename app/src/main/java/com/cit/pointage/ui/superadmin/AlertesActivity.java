package com.cit.pointage.ui.superadmin;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.cit.pointage.databinding
        .ActivityAlertesBinding;
import com.cit.pointage.model.response.AlerteResponse;
import com.cit.pointage.viewmodel.AlerteViewModel;

public class AlertesActivity extends AppCompatActivity
        implements AlerteAdapter.OnAlerteClickListener {

    private ActivityAlertesBinding binding;
    private AlerteViewModel alerteViewModel;
    private AlerteAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAlertesBinding
                .inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        alerteViewModel = new ViewModelProvider(this)
                .get(AlerteViewModel.class);

        setupToolbar();
        setupRecyclerView();
        observerViewModel();
        chargerAlertes();
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
        adapter = new AlerteAdapter(this);
        binding.rvAlertes.setLayoutManager(
                new LinearLayoutManager(this));
        binding.rvAlertes.setAdapter(adapter);
    }

    private void chargerAlertes() {
        afficherChargement(true);
        alerteViewModel.chargerNonLues();
    }

    private void observerViewModel() {

        // ✅ Liste alertes
        alerteViewModel.alertes.observe(
                this, alertes -> {
                    afficherChargement(false);

                    if (alertes != null
                            && !alertes.isEmpty()) {
                        adapter.setAlertes(alertes);
                        binding.rvAlertes
                                .setVisibility(View.VISIBLE);
                        binding.layoutVide
                                .setVisibility(View.GONE);
                    } else {
                        binding.rvAlertes
                                .setVisibility(View.GONE);
                        binding.layoutVide
                                .setVisibility(View.VISIBLE);
                    }
                });

        // ✅ Alerte marquée lue
        alerteViewModel.alerteLue.observe(
                this, alerte -> {
                    if (alerte != null) {
                        // Recharge la liste
                        chargerAlertes();
                    }
                });

        // ❌ Erreur
        alerteViewModel.error.observe(this, erreur -> {
            afficherChargement(false);
        });
    }

    @Override
    public void onMarquerLue(AlerteResponse alerte) {
        alerteViewModel.marquerLue(alerte.getId());
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