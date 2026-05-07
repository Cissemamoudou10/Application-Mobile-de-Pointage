package com.cit.pointage.api;

import com.cit.pointage.model.request.CompteRequest;
import com.cit.pointage.model.request.LoginRequest;
import com.cit.pointage.model.response.AuthResponse;
import com.cit.pointage.model.response.CompteResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthApi {

    @POST("api/auth/login")
    Call<AuthResponse> login(@Body LoginRequest request);

    @POST("api/auth/comptes")
    Call<CompteResponse> creerCompte(@Body CompteRequest request);
}