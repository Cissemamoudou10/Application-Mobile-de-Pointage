package com.cit.pointage.ui.superadmin;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import com.cit.pointage.R;
import com.cit.pointage.viewmodel.AuthViewModel;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

public class CreerCompteActivity extends AppCompatActivity {

    private AuthViewModel authViewModel;
    private TextInputEditText etNom, etPrenom, etLogin, etPassword;
    private Spinner spinnerRole;
    private ProgressBar progressBar;
    private Button btnCreerCompte;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creer_compte);

        // Toolbar
        Toolbar toolbar = findViewById(R.id.toolbarCreerCompte);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> finish());

        // Initialisation des vues
        etNom = findViewById(R.id.etNom);
        etPrenom = findViewById(R.id.etPrenom);
        etLogin = findViewById(R.id.etLogin);
        etPassword = findViewById(R.id.etPassword);
        spinnerRole = findViewById(R.id.spinnerRole);
        progressBar = findViewById(R.id.progressBarCreerCompte);
        btnCreerCompte = findViewById(R.id.btnCreerCompte);

        // Configurer le Spinner
        String[] roles = {"CONTROLEUR", "ADMIN"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, roles);
        spinnerRole.setAdapter(adapter);

        // ViewModel
        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        // Observers
        authViewModel.loading.observe(this, isLoading -> {
            if (isLoading) {
                progressBar.setVisibility(View.VISIBLE);
                btnCreerCompte.setEnabled(false);
            } else {
                progressBar.setVisibility(View.GONE);
                btnCreerCompte.setEnabled(true);
            }
        });

        authViewModel.compteSuccess.observe(this, response -> {
            authViewModel.loading.setValue(false);
            Toast.makeText(this, "Compte " + response.getRole() + " créé avec succès !", Toast.LENGTH_LONG).show();
            finish(); // Retourne à l'écran précédent
        });

        authViewModel.compteError.observe(this, errorMsg -> {
            authViewModel.loading.setValue(false);
            Snackbar.make(findViewById(android.R.id.content), errorMsg, Snackbar.LENGTH_LONG).show();
        });

        // Bouton Créer
        btnCreerCompte.setOnClickListener(v -> validerEtCreer());
    }

    private void validerEtCreer() {
        String nom = etNom.getText().toString().trim();
        String prenom = etPrenom.getText().toString().trim();
        String login = etLogin.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String role = spinnerRole.getSelectedItem().toString();

        if (nom.isEmpty() || prenom.isEmpty() || login.isEmpty() || password.isEmpty()) {
            Snackbar.make(findViewById(android.R.id.content), "Veuillez remplir tous les champs", Snackbar.LENGTH_SHORT).show();
            return;
        }

        if (password.length() < 6) {
            etPassword.setError("Le mot de passe doit contenir au moins 6 caractères");
            return;
        }

        authViewModel.creerCompte(nom, prenom, login, password, role);
    }
}
