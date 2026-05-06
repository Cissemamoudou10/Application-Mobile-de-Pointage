package com.cit.pointage.api;

import com.cit.pointage.model.request.UtilisateurRequest;
import com.cit.pointage.model.response.UtilisateurResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UtilisateurApi {

    // Créer un utilisateur
    @POST("api/utilisateurs")
    Call<UtilisateurResponse> creer(
            @Body UtilisateurRequest request);

    // Modifier un utilisateur
    @PUT("api/utilisateurs/{id}")
    Call<UtilisateurResponse> modifier(
            @Path("id") String id,
            @Body UtilisateurRequest request);

    // Changer le statut
    @PUT("api/utilisateurs/{id}/statut")
    Call<UtilisateurResponse> changerStatut(
            @Path("id") String id,
            @Query("statut") String statut);

    // Liste de tous les utilisateurs
    @GET("api/utilisateurs")
    Call<List<UtilisateurResponse>> tousLesUtilisateurs();

    // Récupérer par ID
    @GET("api/utilisateurs/{id}")
    Call<UtilisateurResponse> parId(@Path("id") String id);

    // Rechercher
    @GET("api/utilisateurs/recherche")
    Call<List<UtilisateurResponse>> rechercher(
            @Query("critere") String critere);
}