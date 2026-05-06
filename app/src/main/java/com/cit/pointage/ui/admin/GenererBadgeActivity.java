package com.cit.pointage.ui.admin;

import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.cit.pointage.R;
import com.cit.pointage.databinding
        .ActivityGenererBadgeBinding;
import com.cit.pointage.model.response
        .BadgeResponse;
import com.cit.pointage.model.response
        .UtilisateurResponse;
import com.cit.pointage.repository.BadgeRepository;
import com.cit.pointage.viewmodel.UtilisateurViewModel;

import androidx.lifecycle.MutableLiveData;

public class GenererBadgeActivity
        extends AppCompatActivity {

    private ActivityGenererBadgeBinding binding;
    private UtilisateurViewModel utilisateurViewModel;
    private BadgeRepository badgeRepository;

    private String utilisateurIdSelectionne = null;

    private MutableLiveData<BadgeResponse>
            badgeSuccess = new MutableLiveData<>();
    private MutableLiveData<String>
            badgeError = new MutableLiveData<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityGenererBadgeBinding
                .inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        utilisateurViewModel = new ViewModelProvider(this)
                .get(UtilisateurViewModel.class);
        badgeRepository = new BadgeRepository();

        setupToolbar();
        setupRecherche();
        setupBouton();
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

    private void setupRecherche() {
        binding.btnRechercher.setOnClickListener(
                v -> lancerRecherche());

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
        utilisateurViewModel.rechercher(critere);
    }

    private void setupBouton() {
        binding.btnGenerer.setOnClickListener(
                v -> genererBadge());
    }

    private void genererBadge() {
        if (utilisateurIdSelectionne == null) return;
        cacherErreur();
        afficherChargement(true);
        badgeRepository.generer(
                utilisateurIdSelectionne,
                badgeSuccess,
                badgeError);
    }

    private void observerViewModel() {

        // Résultats recherche utilisateur
        utilisateurViewModel.resultatsRecherche
                .observe(this, resultats -> {
                    if (resultats != null
                            && !resultats.isEmpty()) {
                        // Affiche le premier résultat
                        selectionnerUtilisateur(
                                resultats.get(0));
                    }
                });

        // Badge généré avec succès
        badgeSuccess.observe(this, badge -> {
            afficherChargement(false);
            if (badge != null) {
                afficherBadge(badge);
            }
        });

        // Erreur génération
        badgeError.observe(this, erreur -> {
            afficherChargement(false);
            if (erreur != null) {
                afficherErreur(erreur);
            }
        });
    }

    private void selectionnerUtilisateur(
            UtilisateurResponse u) {
        utilisateurIdSelectionne = u.getId();

        binding.cardUtilisateur
                .setVisibility(View.VISIBLE);
        binding.tvNomComplet.setText(u.getNomComplet());
        binding.tvMatricule.setText(u.getMatricule());
        binding.tvCategorie.setText(u.getCategorie());

        if (u.getPhotoUrl() != null
                && !u.getPhotoUrl().isEmpty()) {
            Glide.with(this)
                    .load(u.getPhotoUrl())
                    .circleCrop()
                    .placeholder(R.color.olive_100)
                    .into(binding.ivAvatar);
        }

        binding.btnGenerer.setEnabled(true);

        // Cache l'ancien badge si visible
        binding.cardBadge.setVisibility(View.GONE);
        binding.tvEtape2.setVisibility(View.GONE);
    }

    private void afficherBadge(BadgeResponse badge) {
        binding.tvEtape2.setVisibility(View.VISIBLE);
        binding.cardBadge.setVisibility(View.VISIBLE);
        binding.tvQrCode.setText(badge.getQrCode());
    }

    private void afficherErreur(String message) {
        binding.tvErreur.setText(message);
        binding.tvErreur.setVisibility(View.VISIBLE);
    }

    private void cacherErreur() {
        binding.tvErreur.setVisibility(View.GONE);
    }

    private void afficherChargement(boolean afficher) {
        binding.progressBar.setVisibility(
                afficher ? View.VISIBLE : View.GONE);
        binding.btnGenerer.setEnabled(!afficher);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}