package com.cit.pointage.ui.controleur;

import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.cit.pointage.R;
import com.cit.pointage.databinding
        .ActivitySignalerPerteBinding;
import com.cit.pointage.model.response.UtilisateurResponse;
import com.cit.pointage.viewmodel.PerteViewModel;
import com.cit.pointage.viewmodel.UtilisateurViewModel;

public class SignalerPerteActivity
        extends AppCompatActivity {

    private ActivitySignalerPerteBinding binding;
    private UtilisateurViewModel utilisateurViewModel;
    private PerteViewModel perteViewModel;

    private String utilisateurIdSelectionne = null;
    private String typePerteSelectionne = null;

    // Dialogue de recherche
    private UtilisateurAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySignalerPerteBinding
                .inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        utilisateurViewModel = new ViewModelProvider(this)
                .get(UtilisateurViewModel.class);
        perteViewModel = new ViewModelProvider(this)
                .get(PerteViewModel.class);

        setupToolbar();
        setupRecherche();
        setupTypePerte();
        observerViewModels();
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

    private void setupTypePerte() {
        binding.cardBadge.setOnClickListener(v -> {
            typePerteSelectionne = "BADGE";
            binding.tvBadgeSelectionne.setText("●");
            binding.tvBadgeSelectionne.setTextColor(
                    getColor(R.color.olive_500));
            binding.tvTitreSelectionne.setText("○");
            binding.tvTitreSelectionne.setTextColor(
                    getColor(R.color.ink_4));
            verifierFormulaire();
        });

        binding.cardTitrePermission
                .setOnClickListener(v -> {
                    typePerteSelectionne = "TITRE_PERMISSION";
                    binding.tvTitreSelectionne.setText("●");
                    binding.tvTitreSelectionne.setTextColor(
                            getColor(R.color.olive_500));
                    binding.tvBadgeSelectionne.setText("○");
                    binding.tvBadgeSelectionne.setTextColor(
                            getColor(R.color.ink_4));
                    verifierFormulaire();
                });

        binding.btnConfirmer.setOnClickListener(
                v -> confirmerPerte());
    }

    private void verifierFormulaire() {
        binding.btnConfirmer.setEnabled(
                utilisateurIdSelectionne != null
                        && typePerteSelectionne != null);
    }

    private void confirmerPerte() {
        afficherChargement(true);
        perteViewModel.signaler(
                utilisateurIdSelectionne,
                typePerteSelectionne,
                "SORTIE"
        );
    }

    private void observerViewModels() {

        // Résultats recherche
        utilisateurViewModel.resultatsRecherche
                .observe(this, resultats -> {
                    if (resultats != null
                            && !resultats.isEmpty()) {
                        // Prend le premier résultat
                        // ou affiche une liste
                        selectionnerUtilisateur(
                                resultats.get(0));
                    }
                });

        // Succès signalement
        perteViewModel.signalerSuccess.observe(
                this, perte -> {
                    afficherChargement(false);
                    if (perte != null) {
                        afficherSucces();
                    }
                });

        // Erreur
        perteViewModel.error.observe(this, erreur -> {
            afficherChargement(false);
            if (erreur != null) {
                com.google.android.material.snackbar
                        .Snackbar.make(
                                binding.getRoot(),
                                erreur,
                                com.google.android
                                        .material.snackbar
                                        .Snackbar.LENGTH_LONG)
                        .show();
            }
        });
    }

    private void selectionnerUtilisateur(
            UtilisateurResponse u) {
        utilisateurIdSelectionne = u.getId();

        // Affiche la carte utilisateur
        binding.cardUtilisateur
                .setVisibility(View.VISIBLE);
        binding.tvNomComplet.setText(u.getNomComplet());
        binding.tvMatricule.setText(u.getMatricule());

        if (u.getPhotoUrl() != null
                && !u.getPhotoUrl().isEmpty()) {
            Glide.with(this)
                    .load(u.getPhotoUrl())
                    .circleCrop()
                    .placeholder(R.color.olive_100)
                    .into(binding.ivAvatar);
        }

        verifierFormulaire();
    }

    private void afficherSucces() {
        com.google.android.material.snackbar.Snackbar
                .make(binding.getRoot(),
                        getString(R.string.perte_success),
                        com.google.android.material
                                .snackbar.Snackbar.LENGTH_LONG)
                .show();

        // Retour après 2 secondes
        binding.getRoot().postDelayed(
                this::finish, 2000);
    }

    private void afficherChargement(boolean afficher) {
        binding.progressBar.setVisibility(
                afficher ? View.VISIBLE : View.GONE);
        binding.btnConfirmer.setEnabled(!afficher);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}