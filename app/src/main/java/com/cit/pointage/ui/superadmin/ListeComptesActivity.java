package com.cit.pointage.ui.superadmin;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cit.pointage.R;
import com.cit.pointage.model.response.CompteResponse;
import com.cit.pointage.ui.admin.CompteAdapter;
import com.cit.pointage.viewmodel.AuthViewModel;
import com.google.android.material.snackbar.Snackbar;

public class ListeComptesActivity extends AppCompatActivity {

    private AuthViewModel authViewModel;
    private CompteAdapter adapter;
    private ProgressBar progressBar;
    private TextView tvEmpty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_comptes);

        // Toolbar
        Toolbar toolbar = findViewById(R.id.toolbarListeComptes);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> finish());

        progressBar = findViewById(R.id.progressBarListeComptes);
        tvEmpty = findViewById(R.id.tvEmptyListeComptes);

        // RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerViewComptes);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new CompteAdapter((compte, activer) -> {
            String nouveauStatut = activer ? "ACTIF" : "INACTIF";
            authViewModel.changerStatut(compte.getId(), nouveauStatut);
        });
        recyclerView.setAdapter(adapter);

        // ViewModel
        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        // Observers
        authViewModel.loading.observe(this, isLoading ->
                progressBar.setVisibility(isLoading != null && isLoading ? View.VISIBLE : View.GONE)
        );

        authViewModel.comptesListe.observe(this, comptes -> {
            authViewModel.loading.setValue(false);
            if (comptes == null || comptes.isEmpty()) {
                tvEmpty.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            } else {
                tvEmpty.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                adapter.setComptes(comptes);
            }
        });

        authViewModel.listeError.observe(this, error -> {
            if (error != null) {
                authViewModel.loading.setValue(false);
                Snackbar.make(findViewById(android.R.id.content), error, Snackbar.LENGTH_LONG).show();
            }
        });

        authViewModel.statutSuccess.observe(this, updated -> {
            if (updated != null) {
                String msg = "ACTIF".equals(updated.getStatut())
                        ? updated.getNomComplet() + " est maintenant ACTIF"
                        : updated.getNomComplet() + " a été désactivé";
                Snackbar.make(findViewById(android.R.id.content), msg, Snackbar.LENGTH_SHORT).show();
                // Recharger la liste pour refléter le changement
                authViewModel.getComptes();
            }
        });

        authViewModel.statutError.observe(this, error -> {
            if (error != null) {
                Snackbar.make(findViewById(android.R.id.content), error, Snackbar.LENGTH_LONG).show();
                // Recharger pour remettre le switch à sa vraie valeur
                authViewModel.getComptes();
            }
        });

        // Charger les données
        authViewModel.getComptes();
    }
}
