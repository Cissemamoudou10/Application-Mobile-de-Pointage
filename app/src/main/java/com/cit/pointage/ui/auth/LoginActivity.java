package com.cit.pointage.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.cit.pointage.R;
import com.cit.pointage.databinding.ActivityLoginBinding;
import com.cit.pointage.ui.controleur.ControleurActivity;
import com.cit.pointage.utils.NetworkUtils;
import com.cit.pointage.utils.SessionManager;
import com.cit.pointage.viewmodel.AuthViewModel;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private AuthViewModel authViewModel;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // ViewBinding
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Init
        sessionManager = new SessionManager(this);
        authViewModel = new ViewModelProvider(this)
                .get(AuthViewModel.class);

        setupUI();
        observerViewModel();
    }

    private void setupUI() {

        // Touche "Done" sur le clavier → lancer connexion
        binding.etPassword.setOnEditorActionListener(
                (v, actionId, event) -> {
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        lancerConnexion();
                        return true;
                    }
                    return false;
                });

        // Bouton connexion
        binding.btnConnexion.setOnClickListener(
                v -> lancerConnexion());
    }

    private void lancerConnexion() {

        // Récupère les valeurs saisies
        String login = binding.etLogin.getText() != null
                ? binding.etLogin.getText().toString().trim()
                : "";
        String motDePasse = binding.etPassword.getText() != null
                ? binding.etPassword.getText().toString().trim()
                : "";

        // Masque l'erreur précédente
        cacherErreur();

        // Validation des champs
        if (login.isEmpty() || motDePasse.isEmpty()) {
            afficherErreur(getString(
                    R.string.login_error_empty));
            return;
        }

        // Vérifie la connexion réseau
        if (!NetworkUtils.isConnecte(this)) {
            afficherErreur(getString(
                    R.string.login_error_network));
            return;
        }

        // Lance le login
        afficherChargement(true);
        authViewModel.login(login, motDePasse);
    }

    private void observerViewModel() {

        // ✅ Succès
        authViewModel.authSuccess.observe(this, authResponse -> {
            afficherChargement(false);

            if (authResponse != null) {
                // Sauvegarde la session
                sessionManager.sauvegarderSession(
                        authResponse.getToken(),
                        authResponse.getLogin(),
                        authResponse.getRole(),
                        authResponse.getNomComplet()
                );

                // Redirige selon le rôle
                redigerSelonRole(authResponse.getRole());
            }
        });

        // ❌ Erreur
        authViewModel.authError.observe(this, erreur -> {
            afficherChargement(false);
            if (erreur != null) {
                afficherErreur(erreur);
            }
        });
    }

    private void redigerSelonRole(String role) {
        Intent intent;

        switch (role) {
            case "CONTROLEUR":
                intent = new Intent(this,
                        ControleurActivity.class);
                break;
            case "ADMIN":
                intent = new Intent(this,
                        com.cit.pointage.ui.admin
                                .AdminActivity.class);
                break;
            case "SUPER_ADMIN":
                intent = new Intent(this,
                        com.cit.pointage.ui.superadmin
                                .SuperAdminActivity.class);
                break;
            default:
                afficherErreur("Rôle non reconnu : " + role);
                return;
        }

        // ✅ Décommenté — lance l'Activity
        startActivity(intent);
        // ✅ Décommenté — ferme LoginActivity
        finish();
    }
    // ════════ HELPERS UI ════════

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
        binding.btnConnexion.setEnabled(!afficher);
        binding.etLogin.setEnabled(!afficher);
        binding.etPassword.setEnabled(!afficher);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}