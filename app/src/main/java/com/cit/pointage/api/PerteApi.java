package com.cit.pointage.api;

import com.cit.pointage.model.request.PerteRequest;
import com.cit.pointage.model.response.PerteResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface PerteApi {

    // Signaler une perte
    @POST("api/pertes")
    Call<PerteResponse> signaler(@Body PerteRequest request);

    // Toutes les pertes
    @GET("api/pertes")
    Call<List<PerteResponse>> toutes();

    // Pertes non traitées
    @GET("api/pertes/non-traitees")
    Call<List<PerteResponse>> nonTraitees();
}