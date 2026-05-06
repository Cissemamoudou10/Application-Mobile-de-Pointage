package com.cit.pointage.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;

public class NetworkUtils {

    // Vérifie si l'appareil est connecté à Internet
    public static boolean isConnecte(Context context) {
        ConnectivityManager cm = (ConnectivityManager)
                context.getSystemService(
                        Context.CONNECTIVITY_SERVICE);

        if (cm == null) return false;

        android.net.Network network =
                cm.getActiveNetwork();
        if (network == null) return false;

        NetworkCapabilities capabilities =
                cm.getNetworkCapabilities(network);
        if (capabilities == null) return false;

        // Vérifie WiFi, données mobiles ou Ethernet
        return capabilities.hasTransport(
                NetworkCapabilities.TRANSPORT_WIFI)
                || capabilities.hasTransport(
                NetworkCapabilities.TRANSPORT_CELLULAR)
                || capabilities.hasTransport(
                NetworkCapabilities.TRANSPORT_ETHERNET);
    }
}