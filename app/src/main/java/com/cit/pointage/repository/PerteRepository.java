package com.cit.pointage.repository;

import androidx.lifecycle.MutableLiveData;

import com.cit.pointage.api.ApiClient;
import com.cit.pointage.api.PerteApi;
import com.cit.pointage.model.request.PerteRequest;
import com.cit.pointage.model.response.PerteResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerteRepository {

    private final PerteApi perteApi;

    public PerteRepository() {
        perteApi = ApiClient.createService(PerteApi.class);
    }

    // Signaler une perte
    public void signaler(PerteRequest request,
                         MutableLiveData<PerteResponse> successLiveData,
                         MutableLiveData<String> errorLiveData) {

        perteApi.signaler(request).enqueue(
                new Callback<PerteResponse>() {

                    @Override
                    public void onResponse(Call<PerteResponse> call,
                                           Response<PerteResponse> response) {
                        if (response.isSuccessful()
                                && response.body() != null) {
                            successLiveData.postValue(response.body());
                        } else {
                            errorLiveData.postValue(
                                    "Erreur signalement perte");
                        }
                    }

                    @Override
                    public void onFailure(Call<PerteResponse> call,
                                          Throwable t) {
                        errorLiveData.postValue(
                                "Erreur réseau : " + t.getMessage());
                    }
                });
    }

    // Toutes les pertes
    public void toutes(
            MutableLiveData<List<PerteResponse>>
                    successLiveData,
            MutableLiveData<String> errorLiveData) {

        perteApi.toutes().enqueue(
                new Callback<List<PerteResponse>>() {

                    @Override
                    public void onResponse(
                            Call<List<PerteResponse>> call,
                            Response<List<PerteResponse>> response) {
                        if (response.isSuccessful()
                                && response.body() != null) {
                            successLiveData.postValue(response.body());
                        } else {
                            errorLiveData.postValue(
                                    "Erreur chargement pertes");
                        }
                    }

                    @Override
                    public void onFailure(
                            Call<List<PerteResponse>> call,
                            Throwable t) {
                        errorLiveData.postValue(
                                "Erreur réseau : " + t.getMessage());
                    }
                });
    }
}