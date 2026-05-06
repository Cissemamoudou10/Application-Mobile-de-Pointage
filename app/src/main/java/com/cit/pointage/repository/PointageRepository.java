package com.cit.pointage.repository;

import androidx.lifecycle.MutableLiveData;

import com.cit.pointage.api.ApiClient;
import com.cit.pointage.api.PointageApi;
import com.cit.pointage.model.request.PointageRequest;
import com.cit.pointage.model.response.PointageResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PointageRepository {

    private final PointageApi pointageApi;

    public PointageRepository() {
        pointageApi = ApiClient.createService(
                PointageApi.class);
    }

    // Effectuer un pointage
    public void pointer(PointageRequest request,
                        MutableLiveData<PointageResponse> successLiveData,
                        MutableLiveData<String> errorLiveData) {

        pointageApi.pointer(request).enqueue(
                new Callback<PointageResponse>() {

                    @Override
                    public void onResponse(
                            Call<PointageResponse> call,
                            Response<PointageResponse> response) {
                        if (response.isSuccessful()
                                && response.body() != null) {
                            successLiveData.postValue(response.body());
                        } else {
                            errorLiveData.postValue(
                                    "Erreur lors du pointage");
                        }
                    }

                    @Override
                    public void onFailure(
                            Call<PointageResponse> call,
                            Throwable t) {
                        errorLiveData.postValue(
                                "Erreur réseau : " + t.getMessage());
                    }
                });
    }

    // Retardataires
    public void retardataires(
            MutableLiveData<List<PointageResponse>>
                    successLiveData,
            MutableLiveData<String> errorLiveData) {

        pointageApi.retardataires().enqueue(
                new Callback<List<PointageResponse>>() {

                    @Override
                    public void onResponse(
                            Call<List<PointageResponse>> call,
                            Response<List<PointageResponse>> response) {
                        if (response.isSuccessful()
                                && response.body() != null) {
                            successLiveData.postValue(response.body());
                        } else {
                            errorLiveData.postValue(
                                    "Erreur chargement retardataires");
                        }
                    }

                    @Override
                    public void onFailure(
                            Call<List<PointageResponse>> call,
                            Throwable t) {
                        errorLiveData.postValue(
                                "Erreur réseau : " + t.getMessage());
                    }
                });
    }
}