package com.cit.pointage.api;

import com.cit.pointage.utils.Constants;
import com.cit.pointage.utils.SessionManager;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.concurrent.TimeUnit;

public class ApiClient {

    private static Retrofit retrofit = null;
    private static SessionManager sessionManager;

    // Initialise le client avec le contexte
    public static void init(android.content.Context context) {
        sessionManager = new SessionManager(context);
    }

    // Retourne l'instance Retrofit (Singleton)
    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .client(buildOkHttpClient())
                    .addConverterFactory(
                            GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    // Configure le client HTTP
    private static OkHttpClient buildOkHttpClient() {

        // ════════ LOG DES REQUÊTES HTTP ════════
        // Affiche les requêtes/réponses dans Logcat
        HttpLoggingInterceptor logging =
                new HttpLoggingInterceptor();
        logging.setLevel(
                HttpLoggingInterceptor.Level.BODY);

        return new OkHttpClient.Builder()

                // Ajoute automatiquement le token JWT
                // dans le header de chaque requête
                .addInterceptor(chain -> {
                    Request original = chain.request();

                    // Si le token existe, on l'ajoute
                    String token = sessionManager != null
                            ? sessionManager.getToken() : null;

                    Request request;
                    if (token != null) {
                        request = original.newBuilder()
                                .header("Authorization",
                                        "Bearer " + token)
                                .header("Content-Type",
                                        "application/json")
                                .build();
                    } else {
                        request = original.newBuilder()
                                .header("Content-Type",
                                        "application/json")
                                .build();
                    }

                    return chain.proceed(request);
                })

                // Logs HTTP dans Logcat
                .addInterceptor(logging)

                // Timeouts
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();
    }

    // Raccourci pour créer les interfaces API
    public static <T> T createService(Class<T> serviceClass) {
        return getClient().create(serviceClass);
    }
}