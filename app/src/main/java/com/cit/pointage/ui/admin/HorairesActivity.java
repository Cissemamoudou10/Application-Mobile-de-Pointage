package com.cit.pointage.ui.admin;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.cit.pointage.databinding.ActivityHorairesBinding;
import com.cit.pointage.model.response.HoraireResponse;
import com.cit.pointage.ui.auth.LoginActivity;
import com.cit.pointage.utils.SessionManager;
import com.cit.pointage.viewmodel.HoraireViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * HorairesActivity — Gestion des horaires de pointage.
 * Accessible à l'Admin uniquement.
 * Permet : configurer, lister et désactiver les horaires par catégorie.
 */
public class HorairesActivity extends AppCompatActivity {

    private ActivityHorairesBinding binding;
    private HoraireViewModel viewModel;
    private HoraireAdapter adapter;

    // Heure sélectionnée (heure, minute)
    private int heureSelectionnee = 7;
    private int minuteSelectionnee = 45;

    // Mapping catégories : libellé affiché → valeur enum backend
    private static final String[] CATEGORIES_LIBELLES = {
            "Personnel administratif",
            "Personnel encadrant",
            "Officiers stagiaires",
            "Élèves"
    };
    private static final String[] CATEGORIES_VALEURS = {
            "PERSONNEL_ADMIN",
            "PERSONNEL_ENCADRANT",
            "OFFICIER_STAGIAIRE",
            "ELEVE"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHorairesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(HoraireViewModel.class);

        setupToolbar();
        setupSpinnerCategorie();
        setupHeurePicker();
        setupBoutonEnregistrer();
        setupRecyclerView();
        observerViewModel();

        // Charge les horaires existants au démarrage
        viewModel.chargerHoraires();
    }

    // ════════ Setup ════════

    private void setupToolbar() {
        setSupportActionBar(binding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        binding.toolbar.setNavigationOnClickListener(v -> finish());
    }

    private void setupSpinnerCategorie() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                CATEGORIES_LIBELLES);
        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerCategorie.setAdapter(adapter);
    }

    private void setupHeurePicker() {
        // Affiche l'heure par défaut
        mettreAJourAffichageHeure();

        // Ouvre le TimePickerDialog au clic
        binding.btnChoisirHeure.setOnClickListener(v -> {
            TimePickerDialog dialog = new TimePickerDialog(
                    this,
                    (TimePicker view, int heure, int minute) -> {
                        heureSelectionnee = heure;
                        minuteSelectionnee = minute;
                        mettreAJourAffichageHeure();
                    },
                    heureSelectionnee,
                    minuteSelectionnee,
                    true // Format 24h
            );
            dialog.show();
        });
    }

    private void mettreAJourAffichageHeure() {
        binding.tvHeureLimite.setText(
                String.format(Locale.getDefault(), "%02d:%02d",
                        heureSelectionnee, minuteSelectionnee));
    }

    private void setupBoutonEnregistrer() {
        binding.btnEnregistrer.setOnClickListener(v -> {
            // 1. Catégorie sélectionnée
            int pos = binding.spinnerCategorie.getSelectedItemPosition();
            String categorie = CATEGORIES_VALEURS[pos];

            // 2. Heure limite format HH:mm
            String heureLimite = String.format(Locale.getDefault(),
                    "%02d:%02d", heureSelectionnee, minuteSelectionnee);

            // 3. Construction de la chaîne jours
            String jours = construireJours();
            if (jours.isEmpty()) {
                afficherMessage("Sélectionnez au moins un jour");
                return;
            }

            viewModel.configurerHoraire(categorie, heureLimite, jours);
        });
    }

    /** Construit la chaîne jours ex: "MONDAY,TUESDAY,WEDNESDAY" */
    private String construireJours() {
        List<String> jours = new ArrayList<>();
        if (binding.cbLundi.isChecked())    jours.add("MONDAY");
        if (binding.cbMardi.isChecked())    jours.add("TUESDAY");
        if (binding.cbMercredi.isChecked()) jours.add("WEDNESDAY");
        if (binding.cbJeudi.isChecked())    jours.add("THURSDAY");
        if (binding.cbVendredi.isChecked()) jours.add("FRIDAY");
        if (binding.cbSamedi.isChecked())   jours.add("SATURDAY");
        return String.join(",", jours);
    }

    private void setupRecyclerView() {
        adapter = new HoraireAdapter(horaire ->
                viewModel.desactiverHoraire(horaire.getId()));

        binding.rvHoraires.setLayoutManager(
                new LinearLayoutManager(this));
        binding.rvHoraires.setAdapter(adapter);
    }

    // ════════ Observations LiveData ════════

    private void observerViewModel() {

        // Liste des horaires chargée
        viewModel.horaires.observe(this, horaires -> {
            afficherChargement(false);
            if (horaires == null || horaires.isEmpty()) {
                binding.tvVide.setVisibility(View.VISIBLE);
                binding.rvHoraires.setVisibility(View.GONE);
            } else {
                binding.tvVide.setVisibility(View.GONE);
                binding.rvHoraires.setVisibility(View.VISIBLE);
                adapter.setHoraires(horaires);
            }
        });

        // Configuration réussie
        viewModel.configurerSuccess.observe(this, horaire -> {
            afficherChargement(false);
            if (horaire != null) {
                afficherMessage("Horaire enregistré pour "
                        + horaire.getCategorieLibelle());
                // Recharge la liste
                viewModel.chargerHoraires();
            }
        });

        // Désactivation réussie
        viewModel.desactiverSuccess.observe(this, succes -> {
            afficherChargement(false);
            if (Boolean.TRUE.equals(succes)) {
                afficherMessage("Horaire désactivé");
                viewModel.chargerHoraires();
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

    // ════════ Utilitaires UI ════════

    private void afficherChargement(boolean afficher) {
        binding.progressBar.setVisibility(
                afficher ? View.VISIBLE : View.GONE);
        binding.btnEnregistrer.setEnabled(!afficher);
    }

    private void afficherMessage(String message) {
        Snackbar.make(binding.getRoot(), message,
                Snackbar.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}