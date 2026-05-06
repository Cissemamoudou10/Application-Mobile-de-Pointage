package com.cit.pointage.ui.superadmin;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.cit.pointage.R;
import com.cit.pointage.databinding
        .ActivitySuperAdminBinding;
import com.cit.pointage.ui.auth.LoginActivity;
import com.cit.pointage.utils.SessionManager;
import com.cit.pointage.viewmodel.AlerteViewModel;

public class SuperAdminActivity
        extends AppCompatActivity {

    private ActivitySuperAdminBinding binding;
    private SessionManager sessionManager;
    private AlerteViewModel alerteViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySuperAdminBinding
                .inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sessionManager = new SessionManager(this);
        alerteViewModel = new ViewModelProvider(this)
                .get(AlerteViewModel.class);

        setupToolbar();
        setupBoutons();
        observerAlertes();
        chargerAlertes();
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

    private void setupBoutons() {

        // ════════ ALERTES ════════
        binding.cardAlertes.setOnClickListener(v ->
                startActivity(new Intent(
                        this,
                        AlertesActivity.class)));

        // ════════ COMPTES ════════
        binding.cardCreerAdmin.setOnClickListener(v ->
                startActivity(new Intent(
                        this,
                        CreerCompteActivity.class)
                        .putExtra("ROLE", "ADMIN")));

        binding.cardCreerControleur
                .setOnClickListener(v ->
                        startActivity(new Intent(
                                this,
                                CreerCompteActivity.class)
                                .putExtra("ROLE", "CONTROLEUR")));

        // ════════ RAPPORTS ════════
        binding.btnPresenceGlobale
                .setOnClickListener(v ->
                        startActivity(new Intent(
                                this,
                                RapportGlobalActivity.class)));

        binding.btnJournalPertes
                .setOnClickListener(v ->
                        startActivity(new Intent(
                                this,
                                JournalPertesActivity.class)));

        binding.btnJournalAdmins
                .setOnClickListener(v ->
                        startActivity(new Intent(
                                this,
                                JournalAdminsActivity.class)));

        // ════════ SYSTÈME ════════
        binding.btnParametres
                .setOnClickListener(v ->
                        startActivity(new Intent(
                                this,
                                ParametresActivity.class)));
    }

    private void observerAlertes() {
        alerteViewModel.alertes.observe(
                this, alertes -> {
                    if (alertes != null) {
                        int nb = alertes.size();

                        if (nb > 0) {
                            // Affiche le badge rouge
                            binding.tvBadgeAlertes
                                    .setVisibility(View.VISIBLE);
                            binding.tvBadgeAlertes
                                    .setText(String.valueOf(nb));
                            binding.tvNombreAlertes.setText(
                                    nb + " alerte(s) non lue(s)");
                        } else {
                            binding.tvBadgeAlertes
                                    .setVisibility(View.GONE);
                            binding.tvNombreAlertes.setText(
                                    "Aucune alerte en attente");
                        }
                    }
                });
    }

    private void chargerAlertes() {
        alerteViewModel.chargerNonLues();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Recharge les alertes à chaque retour
        chargerAlertes();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(
                R.menu.menu_super_admin, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()
                == R.id.action_deconnecter) {
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