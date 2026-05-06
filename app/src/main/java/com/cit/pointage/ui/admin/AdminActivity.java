package com.cit.pointage.ui.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import com.cit.pointage.R;
import com.cit.pointage.databinding
        .ActivityAdminBinding;
import com.cit.pointage.ui.auth.LoginActivity;
import com.cit.pointage.utils.SessionManager;

public class AdminActivity extends AppCompatActivity {

    private ActivityAdminBinding binding;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAdminBinding
                .inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sessionManager = new SessionManager(this);

        setupToolbar();
        setupBoutons();
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

        // ════════ UTILISATEURS ════════
        binding.cardCreerUtilisateur
                .setOnClickListener(v ->
                        startActivity(new Intent(
                                this,
                                CreerUtilisateurActivity.class)));

        binding.cardListeUtilisateurs
                .setOnClickListener(v ->
                        startActivity(new Intent(
                                this,
                                ListeUtilisateursActivity.class)));

        // ════════ BADGES ════════
        binding.cardGenererBadge
                .setOnClickListener(v ->
                        startActivity(new Intent(
                                this,
                                GenererBadgeActivity.class)));

        // ════════ PERMISSIONS ════════
        binding.cardCreerPermission
                .setOnClickListener(v ->
                        startActivity(new Intent(
                                this,
                                CreerPermissionActivity.class)));

        // ════════ RAPPORTS ════════
        binding.btnPresenceJour
                .setOnClickListener(v ->
                        startActivity(new Intent(
                                this,
                                RapportPresenceActivity.class)));

        binding.btnRetardataires
                .setOnClickListener(v ->
                        startActivity(new Intent(
                                this,
                                RapportRetardatairesActivity.class)));

        binding.btnPermissionsEnCours
                .setOnClickListener(v ->
                        startActivity(new Intent(
                                this,
                                RapportPermissionsActivity.class)));

        // ════════ HORAIRES ════════
        binding.btnHoraires
                .setOnClickListener(v ->
                        startActivity(new Intent(
                                this,
                                HorairesActivity.class)));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(
                R.menu.menu_admin, menu);
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