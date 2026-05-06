package com.cit.pointage.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {

    private final SharedPreferences prefs;
    private final SharedPreferences.Editor editor;

    public SessionManager(Context context) {
        prefs = context.getSharedPreferences(
                Constants.PREF_NAME, Context.MODE_PRIVATE);
        editor = prefs.edit();
    }

    // ════════ SAUVEGARDER LA SESSION ════════
    public void sauvegarderSession(String token, String login,
                                   String role, String nomComplet) {
        editor.putString(Constants.KEY_TOKEN, token);
        editor.putString(Constants.KEY_LOGIN, login);
        editor.putString(Constants.KEY_ROLE, role);
        editor.putString(Constants.KEY_NOM_COMPLET, nomComplet);
        editor.apply();
    }

    // ════════ GETTERS ════════
    public String getToken() {
        return prefs.getString(Constants.KEY_TOKEN, null);
    }

    public String getLogin() {
        return prefs.getString(Constants.KEY_LOGIN, null);
    }

    public String getRole() {
        return prefs.getString(Constants.KEY_ROLE, null);
    }

    public String getNomComplet() {
        return prefs.getString(Constants.KEY_NOM_COMPLET, null);
    }

    // Retourne le token avec le préfixe Bearer
    // pour l'envoyer dans les headers HTTP
    public String getBearerToken() {
        return "Bearer " + getToken();
    }

    // ════════ VÉRIFICATIONS ════════
    public boolean isConnecte() {
        return getToken() != null;
    }

    public boolean isControleur() {
        return Constants.ROLE_CONTROLEUR.equals(getRole());
    }

    public boolean isAdmin() {
        return Constants.ROLE_ADMIN.equals(getRole());
    }

    public boolean isSuperAdmin() {
        return Constants.ROLE_SUPER_ADMIN.equals(getRole());
    }

    // ════════ DÉCONNEXION ════════
    public void deconnecter() {
        editor.clear();
        editor.apply();
    }
}