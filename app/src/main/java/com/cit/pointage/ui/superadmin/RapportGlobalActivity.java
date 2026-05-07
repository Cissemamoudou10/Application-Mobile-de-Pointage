package com.cit.pointage.ui.superadmin;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.cit.pointage.databinding.ActivityRapportGlobalBinding;
import com.cit.pointage.model.response.RapportGlobalResponse;
import com.cit.pointage.viewmodel.PointageViewModel;

public class RapportGlobalActivity extends AppCompatActivity {

    private ActivityRapportGlobalBinding binding;
    private PointageViewModel viewModel;
    private DetailPresenceAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRapportGlobalBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Bouton retour
        binding.toolbar.setNavigationOnClickListener(v -> finish());

        // Configuration RecyclerView
        adapter = new DetailPresenceAdapter();
        binding.rvDetails.setLayoutManager(new LinearLayoutManager(this));
        binding.rvDetails.setAdapter(adapter);

        // Initialisation ViewModel
        viewModel = new ViewModelProvider(this).get(PointageViewModel.class);

        // Observers
        viewModel.loading.observe(this, isLoading -> {
            if (isLoading) {
                binding.progressBar.setVisibility(View.VISIBLE);
                binding.layoutDashboard.setVisibility(View.GONE);
                binding.tvErreur.setVisibility(View.GONE);
                binding.rvDetails.setVisibility(View.GONE);
            } else {
                binding.progressBar.setVisibility(View.GONE);
            }
        });

        viewModel.error.observe(this, error -> {
            if (error != null) {
                binding.tvErreur.setText(error);
                binding.tvErreur.setVisibility(View.VISIBLE);
                Toast.makeText(this, error, Toast.LENGTH_LONG).show();
            }
        });

        viewModel.rapportGlobal.observe(this, rapport -> {
            if (rapport != null) {
                afficherRapport(rapport);
            }
        });

        // Charger les données
        viewModel.chargerRapportGlobal();
    }

    private void afficherRapport(RapportGlobalResponse rapport) {
        binding.layoutDashboard.setVisibility(View.VISIBLE);
        binding.rvDetails.setVisibility(View.VISIBLE);

        binding.tvTotal.setText(String.valueOf(rapport.getTotalUtilisateurs()));
        binding.tvPresents.setText(String.valueOf(rapport.getPresents()));
        binding.tvRetards.setText(String.valueOf(rapport.getEnRetard()));
        binding.tvAbsents.setText(String.valueOf(rapport.getAbsents()));
        binding.tvPermissions.setText(String.valueOf(rapport.getEnPermission()));

        if (rapport.getDetails() != null) {
            adapter.setDetails(rapport.getDetails());
        }
    }
}