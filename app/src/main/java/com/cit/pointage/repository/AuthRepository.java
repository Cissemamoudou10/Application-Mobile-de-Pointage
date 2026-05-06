package com.cit.pointage.repository;

import androidx.lifecycle.MutableLiveData;

import com.cit.pointage.api.ApiClient;
import com.cit.pointage.api.AuthApi;
import com.cit.pointage.model.request.LoginRequest;
import com.cit.pointage.model.response.AuthResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthRepository {

    private final AuthApi authApi;

    public AuthRepository() {
        authApi = ApiClient.createService(AuthApi.class);
    }

    public void login(String login, String motDePasse,
                      MutableLiveData<AuthResponse> successLiveData,
                      MutableLiveData<String> errorLiveData) {

        LoginRequest request =
                new LoginRequest(login, motDePasse);

        authApi.login(request).enqueue(
                new Callback<AuthResponse>() {

                    @Override
                    public void onResponse(Call<AuthResponse> call,
                                           Response<AuthResponse> response) {

                        if (response.isSuccessful()
                                && response.body() != null) {
                            // ✅ Login réussi
                            successLiveData.postValue(response.body());
                        } else {
                            // ❌ Erreur serveur (400, 401...)
                            errorLiveData.postValue(
                                    "Login ou mot de passe incorrect");
                        }
                    }

                    @Override
                    public void onFailure(Call<AuthResponse> call,
                                          Throwable t) {
                        // ❌ Erreur réseau
                        errorLiveData.postValue(
                                "Erreur réseau : " + t.getMessage());
                    }
                });
    }
}