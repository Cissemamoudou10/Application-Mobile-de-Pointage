package com.cit.pointage.repository;

import androidx.lifecycle.MutableLiveData;

import com.cit.pointage.api.ApiClient;
import com.cit.pointage.api.HoraireApi;
import com.cit.pointage.model.response.HoraireResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Repository — Horaires
 * Couche unique d'accès aux données horaires (réseau via Retrofit).
 */
public class HoraireRepository {

    private final HoraireApi horaireApi;

    public HoraireRepository() {
        horaireApi = ApiClient.createService(HoraireApi.class);
    }

    // ════════ Charger tous les horaires ════════
    public void tous(
            MutableLiveData<List<HoraireResponse>> successLiveData,
            MutableLiveData<String> errorLiveData) {

        horaireApi.tous().enqueue(new Callback<List<HoraireResponse>>() {

            @Override
            public void onResponse(Call<List<HoraireResponse>> call,
                                   Response<List<HoraireResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    successLiveData.postValue(response.body());
                } else {
                    errorLiveData.postValue(
                            "Erreur chargement horaires (" + response.code() + ")");
                }
            }

            @Override
            public void onFailure(Call<List<HoraireResponse>> call, Throwable t) {
                errorLiveData.postValue("Erreur réseau : " + t.getMessage());
            }
        });
    }

    // ════════ Configurer un horaire ════════
    public void configurer(
            String categorie,
            String heureLimite,
            String jours,
            MutableLiveData<HoraireResponse> successLiveData,
            MutableLiveData<String> errorLiveData) {

        horaireApi.configurer(categorie, heureLimite, jours)
                .enqueue(new Callback<HoraireResponse>() {

                    @Override
                    public void onResponse(Call<HoraireResponse> call,
                                           Response<HoraireResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            successLiveData.postValue(response.body());
                        } else {
                            errorLiveData.postValue(
                                    "Erreur configuration horaire (" + response.code() + ")");
                        }
                    }

                    @Override
                    public void onFailure(Call<HoraireResponse> call, Throwable t) {
                        errorLiveData.postValue("Erreur réseau : " + t.getMessage());
                    }
                });
    }

    // ════════ Désactiver un horaire ════════
    public void desactiver(
            String id,
            MutableLiveData<Boolean> successLiveData,
            MutableLiveData<String> errorLiveData) {

        horaireApi.desactiver(id).enqueue(new Callback<Void>() {

            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    successLiveData.postValue(true);
                } else {
                    errorLiveData.postValue(
                            "Erreur désactivation horaire (" + response.code() + ")");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                errorLiveData.postValue("Erreur réseau : " + t.getMessage());
            }
        });
    }
}
