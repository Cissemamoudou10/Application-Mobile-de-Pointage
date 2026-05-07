package com.cit.pointage.repository;

import androidx.lifecycle.MutableLiveData;

import com.cit.pointage.api.ApiClient;
import com.cit.pointage.api.AuthApi;
import com.cit.pointage.model.request.CompteRequest;
import com.cit.pointage.model.request.LoginRequest;
import com.cit.pointage.model.response.AuthResponse;
import com.cit.pointage.model.response.CompteResponse;

import java.util.List;

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

    public void creerCompte(CompteRequest request,
                            MutableLiveData<CompteResponse> successLiveData,
                            MutableLiveData<String> errorLiveData) {

        authApi.creerCompte(request).enqueue(new Callback<CompteResponse>() {
            @Override
            public void onResponse(Call<CompteResponse> call, Response<CompteResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    successLiveData.postValue(response.body());
                } else {
                    errorLiveData.postValue("Erreur lors de la création du compte (ex: identifiant déjà utilisé)");
                }
            }

            @Override
            public void onFailure(Call<CompteResponse> call, Throwable t) {
                errorLiveData.postValue("Erreur réseau : " + t.getMessage());
            }
        });
    }

    public void getComptes(MutableLiveData<List<CompteResponse>> successLiveData,
                           MutableLiveData<String> errorLiveData) {

        authApi.getComptes().enqueue(new Callback<List<CompteResponse>>() {
            @Override
            public void onResponse(Call<List<CompteResponse>> call, Response<List<CompteResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    successLiveData.postValue(response.body());
                } else {
                    errorLiveData.postValue("Erreur lors du chargement des comptes");
                }
            }

            @Override
            public void onFailure(Call<List<CompteResponse>> call, Throwable t) {
                errorLiveData.postValue("Erreur réseau : " + t.getMessage());
            }
        });
    }

    public void changerStatut(String id, String statut,
                              MutableLiveData<CompteResponse> successLiveData,
                              MutableLiveData<String> errorLiveData) {

        authApi.changerStatut(id, statut).enqueue(new Callback<CompteResponse>() {
            @Override
            public void onResponse(Call<CompteResponse> call, Response<CompteResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    successLiveData.postValue(response.body());
                } else {
                    errorLiveData.postValue("Impossible de modifier le statut");
                }
            }

            @Override
            public void onFailure(Call<CompteResponse> call, Throwable t) {
                errorLiveData.postValue("Erreur réseau : " + t.getMessage());
            }
        });
    }
}