package com.cit.pointage.api;

import com.cit.pointage.model.response.HoraireResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Interface Retrofit — Endpoints /api/horaires
 * Accès : ADMIN, SUPER_ADMIN (POST/PUT), CONTROLEUR (GET)
 */
public interface HoraireApi {

    // Configurer un horaire pour une catégorie
    @POST("api/horaires")
    Call<HoraireResponse> configurer(
            @Query("categorie") String categorie,
            @Query("heureLimite") String heureLimite,
            @Query("jours") String jours);

    // Récupérer tous les horaires configurés
    @GET("api/horaires")
    Call<List<HoraireResponse>> tous();

    // Désactiver un horaire par ID
    @PUT("api/horaires/desactiver/{id}")
    Call<Void> desactiver(@Path("id") String id);
}
