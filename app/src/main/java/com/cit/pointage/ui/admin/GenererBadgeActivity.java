package com.cit.pointage.ui.admin;

import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.cit.pointage.R;
import com.cit.pointage.databinding.ActivityGenererBadgeBinding;
import com.cit.pointage.model.response.BadgeResponse;
import com.cit.pointage.model.response.UtilisateurResponse;
import com.cit.pointage.viewmodel.BadgeViewModel;
import com.cit.pointage.viewmodel.UtilisateurViewModel;
import com.google.android.material.snackbar.Snackbar;

/**
 * GenererBadgeActivity — Gestion complète des badges.
 * Fonctionnalités : Rechercher un utilisateur, Générer, Renouveler, Désactiver un badge.
 */
public class GenererBadgeActivity extends AppCompatActivity {

    private ActivityGenererBadgeBinding binding;
    private BadgeViewModel badgeViewModel;
    private UtilisateurViewModel utilisateurViewModel;

    private String utilisateurIdSelectionne = null;
    private String badgeIdActif = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGenererBadgeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        badgeViewModel = new ViewModelProvider(this).get(BadgeViewModel.class);
        utilisateurViewModel = new ViewModelProvider(this).get(UtilisateurViewModel.class);

        setupToolbar();
        setupRecherche();
        setupBoutons();
        observerViewModel();
    }

    // ════════ Setup ════════

    private void setupToolbar() {
        setSupportActionBar(binding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        binding.toolbar.setNavigationOnClickListener(v -> finish());
    }

    private void setupRecherche() {
        binding.btnRechercher.setOnClickListener(v -> lancerRecherche());
        binding.etRecherche.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                lancerRecherche();
                return true;
            }
            return false;
        });
    }

    private void lancerRecherche() {
        String critere = binding.etRecherche.getText() != null
                ? binding.etRecherche.getText().toString().trim() : "";
        if (critere.isEmpty()) return;
        utilisateurViewModel.rechercher(critere);
    }

    private void setupBoutons() {

        // Générer
        binding.btnGenerer.setOnClickListener(v -> {
            if (utilisateurIdSelectionne == null) return;
            badgeViewModel.generer(utilisateurIdSelectionne);
        });

        // Renouveler — avec confirmation
        binding.btnRenouveler.setOnClickListener(v -> {
            if (utilisateurIdSelectionne == null) return;
            new AlertDialog.Builder(this)
                    .setTitle("Renouveler le badge")
                    .setMessage("L'ancien badge sera désactivé et un nouveau QR Code sera généré. Confirmer ?")
                    .setPositiveButton("Renouveler", (d, w) ->
                            badgeViewModel.renouveler(utilisateurIdSelectionne))
                    .setNegativeButton("Annuler", null)
                    .show();
        });

        // Désactiver — avec confirmation
        binding.btnDesactiverBadge.setOnClickListener(v -> {
            if (badgeIdActif == null) return;
            new AlertDialog.Builder(this)
                    .setTitle("Désactiver le badge")
                    .setMessage("Ce badge ne pourra plus être utilisé pour le pointage. Confirmer ?")
                    .setPositiveButton("Désactiver", (d, w) ->
                            badgeViewModel.desactiver(badgeIdActif))
                    .setNegativeButton("Annuler", null)
                    .show();
        });
    }

    // ════════ Observations LiveData ════════

    private void observerViewModel() {

        // Résultats recherche utilisateur
        utilisateurViewModel.resultatsRecherche.observe(this, resultats -> {
            if (resultats != null && !resultats.isEmpty()) {
                selectionnerUtilisateur(resultats.get(0));
            }
        });

        // Badge généré ou renouvelé avec succès
        badgeViewModel.badgeSuccess.observe(this, badge -> {
            afficherChargement(false);
            if (badge != null) {
                afficherBadge(badge);
                afficherMessage("Badge enregistré avec succès");
            }
        });

        // Désactivation réussie
        badgeViewModel.desactiverSuccess.observe(this, succes -> {
            afficherChargement(false);
            if (Boolean.TRUE.equals(succes)) {
                afficherMessage("Badge désactivé");
                binding.cardBadge.setVisibility(View.GONE);
                binding.tvEtape2.setVisibility(View.GONE);
                badgeIdActif = null;
            }
        });

        // Erreur
        badgeViewModel.error.observe(this, erreur -> {
            afficherChargement(false);
            if (erreur != null) {
                binding.tvErreur.setText(erreur);
                binding.tvErreur.setVisibility(View.VISIBLE);
            }
        });

        // Chargement
        badgeViewModel.loading.observe(this, this::afficherChargement);
    }

    // ════════ Utilitaires UI ════════

    private void selectionnerUtilisateur(UtilisateurResponse u) {
        utilisateurIdSelectionne = u.getId();
        binding.cardUtilisateur.setVisibility(View.VISIBLE);
        binding.tvNomComplet.setText(u.getNomComplet());
        binding.tvMatricule.setText(u.getMatricule());
        binding.tvCategorie.setText(u.getCategorie());

        if (u.getPhotoUrl() != null && !u.getPhotoUrl().isEmpty()) {
            Glide.with(this).load(u.getPhotoUrl())
                    .circleCrop()
                    .placeholder(R.color.olive_100)
                    .into(binding.ivAvatar);
        }

        binding.btnGenerer.setEnabled(true);
        binding.cardBadge.setVisibility(View.GONE);
        binding.tvEtape2.setVisibility(View.GONE);
    }

    private void afficherBadge(BadgeResponse badge) {
        badgeIdActif = badge.getId();
        binding.tvEtape2.setVisibility(View.VISIBLE);
        binding.cardBadge.setVisibility(View.VISIBLE);
        binding.tvQrCode.setText(badge.getQrCode());
        binding.btnRenouveler.setEnabled(badge.isActif());
        binding.btnDesactiverBadge.setEnabled(badge.isActif());
    }

    private void afficherChargement(boolean afficher) {
        binding.progressBar.setVisibility(afficher ? View.VISIBLE : View.GONE);
        binding.btnGenerer.setEnabled(!afficher && utilisateurIdSelectionne != null);
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