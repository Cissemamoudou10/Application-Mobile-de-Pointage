package com.cit.pointage.api;

import com.cit.pointage.model.request.PermissionRequest;
import com.cit.pointage.model.response.PermissionResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface PermissionApi {

    // Créer une permission
    @POST("api/permissions")
    Call<PermissionResponse> creer(
            @Body PermissionRequest request);

    // Annuler une permission
    @PUT("api/permissions/annuler/{id}")
    Call<PermissionResponse> annuler(@Path("id") String id);

    // Permissions en cours
    @GET("api/permissions/en-cours")
    Call<List<PermissionResponse>> enCours();

    // Permissions d'un utilisateur
    @GET("api/permissions/utilisateur/{utilisateurId}")
    Call<List<PermissionResponse>> parUtilisateur(
            @Path("utilisateurId") String utilisateurId);
}