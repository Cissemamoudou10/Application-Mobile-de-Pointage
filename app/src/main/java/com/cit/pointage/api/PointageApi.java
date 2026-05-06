package com.cit.pointage.api;

import com.cit.pointage.model.request.PointageRequest;
import com.cit.pointage.model.response.PointageResponse;

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
}