package com.cit.pointage.api;

import com.cit.pointage.model.response.BadgeResponse;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface BadgeApi {

    // Générer un badge
    @POST("api/badges/generer/{utilisateurId}")
    Call<BadgeResponse> generer(
            @Path("utilisateurId") String utilisateurId);

    // Renouveler un badge
    @POST("api/badges/renouveler/{utilisateurId}")
    Call<BadgeResponse> renouveler(
            @Path("utilisateurId") String utilisateurId);

    // Désactiver un badge
    @PUT("api/badges/desactiver/{badgeId}")
    Call<Void> desactiver(@Path("badgeId") String badgeId);
}