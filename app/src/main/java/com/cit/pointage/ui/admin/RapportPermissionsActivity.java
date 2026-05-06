package com.cit.pointage.ui.admin;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.cit.pointage.databinding.ActivityRapportPermissionsBinding;
import com.cit.pointage.viewmodel.PermissionViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.Locale;

/**
 * RapportPermissionsActivity — Liste et gestion des permissions en cours.
 * Permet à l'Admin de voir les permissions actives et de les annuler.
 */
public class RapportPermissionsActivity extends AppCompatActivity {

    private ActivityRapportPermissionsBinding binding;
    private PermissionViewModel viewModel;
    private PermissionAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRapportPermissionsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(PermissionViewModel.class);

        setupToolbar();
        setupRecyclerView();
        observerViewModel();

        // Charge les données
        viewModel.chargerPermissionsEnCours();
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
        adapter = new PermissionAdapter(permission -> {
            new AlertDialog.Builder(this)
                    .setTitle("Annuler la permission")
                    .setMessage("Voulez-vous vraiment annuler la permission de "
                            + permission.getNomCompletUtilisateur() + " ?")
                    .setPositiveButton("Annuler la permission", (d, w) ->
                            viewModel.annulerPermission(permission.getId()))
                    .setNegativeButton("Fermer", null)
                    .show();
        });

        binding.rvPermissions.setLayoutManager(new LinearLayoutManager(this));
        binding.rvPermissions.setAdapter(adapter);
    }

    private void observerViewModel() {
        // Liste
        viewModel.permissionsEnCours.observe(this, permissions -> {
            afficherChargement(false);
            if (permissions == null || permissions.isEmpty()) {
                binding.layoutVide.setVisibility(View.VISIBLE);
                binding.rvPermissions.setVisibility(View.GONE);
                binding.tvCompteur.setText("0");
            } else {
                binding.layoutVide.setVisibility(View.GONE);
                binding.rvPermissions.setVisibility(View.VISIBLE);
                binding.tvCompteur.setText(String.format(Locale.getDefault(), "%d", permissions.size()));
                adapter.setPermissions(permissions);
            }
        });

        // Succès Annulation
        viewModel.permissionSuccess.observe(this, permission -> {
            afficherChargement(false);
            if (permission != null) {
                afficherMessage("Permission annulée avec succès");
                viewModel.chargerPermissionsEnCours(); // Recharge la liste
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