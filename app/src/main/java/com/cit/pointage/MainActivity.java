package com.cit.pointage;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.cit.pointage.api.ApiClient;
import com.cit.pointage.ui.admin.AdminActivity;
import com.cit.pointage.ui.auth.LoginActivity;
import com.cit.pointage.ui.controleur.ControleurActivity;
import com.cit.pointage.ui.superadmin.SuperAdminActivity;
import com.cit.pointage.utils.SessionManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialise Retrofit
        ApiClient.init(this);

        // Vérifie si l'utilisateur est déjà connecté
        SessionManager session = new SessionManager(this);

        if (session.isConnecte()) {
            // Déjà connecté → redirige selon le rôle
            redigerSelonRole(session.getRole());
        } else {
            // Pas connecté → écran de login
            startActivity(new Intent(this, LoginActivity.class));
        }

        // Ferme MainActivity — on n'en a plus besoin
        finish();
    }

    private void redigerSelonRole(String role) {
        switch (role) {
            case "CONTROLEUR":
                startActivity(new Intent(this,
                        ControleurActivity.class));
                finish();
                return;
            case "ADMIN":
                startActivity(new Intent(
                        this, AdminActivity.class));
                finish();
                return;
            case "SUPER_ADMIN":
                startActivity(new Intent(
                        this, SuperAdminActivity.class));
                finish();
                return;
            default:
                startActivity(new Intent(
                        this, LoginActivity.class));
                break;
        }
        finish();
    }
}