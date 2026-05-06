package com.cit.pointage.repository;

import androidx.lifecycle.MutableLiveData;

import com.cit.pointage.api.ApiClient;
import com.cit.pointage.api.BadgeApi;
import com.cit.pointage.model.response.BadgeResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BadgeRepository {

    private final BadgeApi badgeApi;

    public BadgeRepository() {
        badgeApi = ApiClient.createService(BadgeApi.class);
    }

    // ════════ GÉNÉRER ════════
    public void generer(String utilisateurId,
                        MutableLiveData<BadgeResponse> successLiveData,
                        MutableLiveData<String> errorLiveData) {

        badgeApi.generer(utilisateurId).enqueue(
                new Callback<BadgeResponse>() {

                    @Override
                    public void onResponse(
                            Call<BadgeResponse> call,
                            Response<BadgeResponse> response) {
                        if (response.isSuccessful()
                                && response.body() != null) {
                            successLiveData.postValue(
                                    response.body());
                        } else {
                            errorLiveData.postValue(
                                    "Erreur génération badge");
                        }
                    }

                    @Override
                    public void onFailure(
                            Call<BadgeResponse> call,
                            Throwable t) {
                        errorLiveData.postValue(
                                "Erreur réseau : "
                                        + t.getMessage());
                    }
                });
    }

    // ════════ RENOUVELER ════════
    public void renouveler(String utilisateurId,
                           MutableLiveData<BadgeResponse> successLiveData,
                           MutableLiveData<String> errorLiveData) {

        badgeApi.renouveler(utilisateurId).enqueue(
                new Callback<BadgeResponse>() {

                    @Override
                    public void onResponse(
                            Call<BadgeResponse> call,
                            Response<BadgeResponse> response) {
                        if (response.isSuccessful()
                                && response.body() != null) {
                            successLiveData.postValue(
                                    response.body());
                        } else {
                            errorLiveData.postValue(
                                    "Erreur renouvellement badge");
                        }
                    }

                    @Override
                    public void onFailure(
                            Call<BadgeResponse> call,
                            Throwable t) {
                        errorLiveData.postValue(
                                "Erreur réseau : "
                                        + t.getMessage());
                    }
                });
    }

    // ════════ DÉSACTIVER ════════
    public void desactiver(String badgeId,
                           MutableLiveData<Void> successLiveData,
                           MutableLiveData<String> errorLiveData) {

        badgeApi.desactiver(badgeId).enqueue(
                new Callback<Void>() {

                    @Override
                    public void onResponse(
                            Call<Void> call,
                            Response<Void> response) {
                        if (response.isSuccessful()) {
                            successLiveData.postValue(null);
                        } else {
                            errorLiveData.postValue(
                                    "Erreur désactivation badge");
                        }
                    }

                    @Override
                    public void onFailure(
                            Call<Void> call,
                            Throwable t) {
                        errorLiveData.postValue(
                                "Erreur réseau : "
                                        + t.getMessage());
                    }
                });
    }
}