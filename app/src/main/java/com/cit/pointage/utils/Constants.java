package com.cit.pointage.utils;

public class Constants {

    // ════════ URL DE BASE DU BACKEND ════════
    // En développement : adresse IP de votre PC sur le réseau local
    // Remplacez par l'IP de votre machine (pas localhost !)
    // Sur Android, localhost = l'appareil lui-même, pas votre PC
    public static final String BASE_URL =
            "http://10.0.2.2:8080/";
    // ↑ Remplacez X par votre IP locale

    // ════════ CLÉS SHARED PREFERENCES ════════
    public static final String PREF_NAME = "CITPrefs";
    public static final String KEY_TOKEN = "jwt_token";
    public static final String KEY_LOGIN = "login";
    public static final String KEY_ROLE = "role";
    public static final String KEY_NOM_COMPLET = "nom_complet";

    // ════════ RÔLES ════════
    public static final String ROLE_CONTROLEUR = "CONTROLEUR";
    public static final String ROLE_ADMIN = "ADMIN";
    public static final String ROLE_SUPER_ADMIN = "SUPER_ADMIN";
}