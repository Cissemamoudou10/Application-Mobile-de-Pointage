package com.cit.pointage.api;

import com.cit.pointage.model.request.PointageRequest;
import com.cit.pointage.model.response.PointageResponse;
import com.cit.pointage.model.response.RapportGlobalResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface PointageApi {

    // Effectuer un pointage
    @POST("api/pointages")
    Call<PointageResponse> pointer(
            @Body PointageRequest request);

    // Historique d'un utilisateur
    @GET("api/pointages/utilisateur/{utilisateurId}")
    Call<List<PointageResponse>> historique(
            @Path("utilisateurId") String utilisateurId);

    // Retardataires
    @GET("api/pointages/retardataires")
    Call<List<PointageResponse>> retardataires();

    // Feuille de présence récente (48h)
    @GET("api/pointages/recents")
    Call<List<PointageResponse>> presenceRecente();

    // Rapport global du jour
    @GET("api/pointages/rapport/jour")
    Call<RapportGlobalResponse> getRapportGlobalJour();
}