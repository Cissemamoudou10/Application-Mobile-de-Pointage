package com.cit.pointage.repository;

import androidx.lifecycle.MutableLiveData;

import com.cit.pointage.api.AlerteApi;
import com.cit.pointage.api.ApiClient;
import com.cit.pointage.model.response.AlerteResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlerteRepository {

    private final AlerteApi alerteApi;

    public AlerteRepository() {
        alerteApi = ApiClient.createService(AlerteApi.class);
    }

    // Alertes non lues
    public void nonLues(
            MutableLiveData<List<AlerteResponse>>
                    successLiveData,
            MutableLiveData<String> errorLiveData) {

        alerteApi.nonLues().enqueue(
                new Callback<List<AlerteResponse>>() {

                    @Override
                    public void onResponse(
                            Call<List<AlerteResponse>> call,
                            Response<List<AlerteResponse>> response) {
                        if (response.isSuccessful()
                                && response.body() != null) {
                            successLiveData.postValue(response.body());
                        } else {
                            errorLiveData.postValue(
                                    "Erreur chargement alertes");
                        }
                    }

                    @Override
                    public void onFailure(
                            Call<List<AlerteResponse>> call,
                            Throwable t) {
                        errorLiveData.postValue(
                                "Erreur réseau : " + t.getMessage());
                    }
                });
    }

    // Marquer une alerte comme lue
    public void marquerLue(String alerteId,
                           MutableLiveData<AlerteResponse> successLiveData,
                           MutableLiveData<String> errorLiveData) {

        alerteApi.marquerLue(alerteId).enqueue(
                new Callback<AlerteResponse>() {

                    @Override
                    public void onResponse(
                            Call<AlerteResponse> call,
                            Response<AlerteResponse> response) {
                        if (response.isSuccessful()
                                && response.body() != null) {
                            successLiveData.postValue(response.body());
                        } else {
                            errorLiveData.postValue(
                                    "Erreur marquage alerte");
                        }
                    }

                    @Override
                    public void onFailure(
                            Call<AlerteResponse> call,
                            Throwable t) {
                        errorLiveData.postValue(
                                "Erreur réseau : " + t.getMessage());
                    }
                });
    }
}