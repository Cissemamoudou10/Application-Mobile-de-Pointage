package com.cit.pointage.repository;

import androidx.lifecycle.MutableLiveData;

import com.cit.pointage.api.ApiClient;
import com.cit.pointage.api.PermissionApi;
import com.cit.pointage.model.request.PermissionRequest;
import com.cit.pointage.model.response.PermissionResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PermissionRepository {

    private final PermissionApi permissionApi;

    public PermissionRepository() {
        permissionApi = ApiClient.createService(
                PermissionApi.class);
    }

    // ════════ CRÉER ════════
    public void creer(PermissionRequest request,
                      MutableLiveData<PermissionResponse>
                              successLiveData,
                      MutableLiveData<String> errorLiveData) {

        permissionApi.creer(request).enqueue(
                new Callback<PermissionResponse>() {

                    @Override
                    public void onResponse(
                            Call<PermissionResponse> call,
                            Response<PermissionResponse> response) {
                        if (response.isSuccessful()
                                && response.body() != null) {
                            successLiveData.postValue(
                                    response.body());
                        } else {
                            errorLiveData.postValue(
                                    "Erreur création permission");
                        }
                    }

                    @Override
                    public void onFailure(
                            Call<PermissionResponse> call,
                            Throwable t) {
                        errorLiveData.postValue(
                                "Erreur réseau : "
                                        + t.getMessage());
                    }
                });
    }

    // ════════ ANNULER ════════
    public void annuler(String id,
                        MutableLiveData<PermissionResponse>
                                successLiveData,
                        MutableLiveData<String> errorLiveData) {

        permissionApi.annuler(id).enqueue(
                new Callback<PermissionResponse>() {

                    @Override
                    public void onResponse(
                            Call<PermissionResponse> call,
                            Response<PermissionResponse> response) {
                        if (response.isSuccessful()
                                && response.body() != null) {
                            successLiveData.postValue(
                                    response.body());
                        } else {
                            errorLiveData.postValue(
                                    "Erreur annulation permission");
                        }
                    }

                    @Override
                    public void onFailure(
                            Call<PermissionResponse> call,
                            Throwable t) {
                        errorLiveData.postValue(
                                "Erreur réseau : "
                                        + t.getMessage());
                    }
                });
    }

    // ════════ PERMISSIONS EN COURS ════════
    public void enCours(
            MutableLiveData<List<PermissionResponse>>
                    successLiveData,
            MutableLiveData<String> errorLiveData) {

        permissionApi.enCours().enqueue(
                new Callback<List<PermissionResponse>>() {

                    @Override
                    public void onResponse(
                            Call<List<PermissionResponse>> call,
                            Response<List<PermissionResponse>>
                                    response) {
                        if (response.isSuccessful()
                                && response.body() != null) {
                            successLiveData.postValue(
                                    response.body());
                        } else {
                            errorLiveData.postValue(
                                    "Erreur chargement permissions");
                        }
                    }

                    @Override
                    public void onFailure(
                            Call<List<PermissionResponse>> call,
                            Throwable t) {
                        errorLiveData.postValue(
                                "Erreur réseau : "
                                        + t.getMessage());
                    }
                });
    }
}