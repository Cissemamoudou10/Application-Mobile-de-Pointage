package com.cit.pointage.api;

import com.cit.pointage.model.response.AlerteResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface AlerteApi {

    // Alertes non lues
    @GET("api/alertes/non-lues")
    Call<List<AlerteResponse>> nonLues();

    // Marquer comme lue
    @PUT("api/alertes/{id}/lue")
    Call<AlerteResponse> marquerLue(@Path("id") String id);
}