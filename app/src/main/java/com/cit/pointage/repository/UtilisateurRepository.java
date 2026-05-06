package com.cit.pointage.repository;

import androidx.lifecycle.MutableLiveData;

import com.cit.pointage.api.ApiClient;
import com.cit.pointage.api.UtilisateurApi;
import com.cit.pointage.model.request.UtilisateurRequest;
import com.cit.pointage.model.response.UtilisateurResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UtilisateurRepository {

    private final UtilisateurApi utilisateurApi;

    public UtilisateurRepository() {
        utilisateurApi = ApiClient.createService(
                UtilisateurApi.class);
    }

    // Créer un utilisateur
    public void creer(UtilisateurRequest request,
                      MutableLiveData<UtilisateurResponse> successLiveData,
                      MutableLiveData<String> errorLiveData) {

        utilisateurApi.creer(request).enqueue(
                new Callback<UtilisateurResponse>() {

                    @Override
                    public void onResponse(
                            Call<UtilisateurResponse> call,
                            Response<UtilisateurResponse> response) {
                        if (response.isSuccessful()
                                && response.body() != null) {
                            successLiveData.postValue(response.body());
                        } else {
                            errorLiveData.postValue(
                                    "Erreur création utilisateur");
                        }
                    }

                    @Override
                    public void onFailure(
                            Call<UtilisateurResponse> call,
                            Throwable t) {
                        errorLiveData.postValue(
                                "Erreur réseau : " + t.getMessage());
                    }
                });
    }

    // Liste tous les utilisateurs
    public void tousLesUtilisateurs(
            MutableLiveData<List<UtilisateurResponse>>
                    successLiveData,
            MutableLiveData<String> errorLiveData) {

        utilisateurApi.tousLesUtilisateurs().enqueue(
                new Callback<List<UtilisateurResponse>>() {

                    @Override
                    public void onResponse(
                            Call<List<UtilisateurResponse>> call,
                            Response<List<UtilisateurResponse>>
                                    response) {
                        if (response.isSuccessful()
                                && response.body() != null) {
                            successLiveData.postValue(response.body());
                        } else {
                            errorLiveData.postValue(
                                    "Erreur chargement utilisateurs");
                        }
                    }

                    @Override
                    public void onFailure(
                            Call<List<UtilisateurResponse>> call,
                            Throwable t) {
                        errorLiveData.postValue(
                                "Erreur réseau : " + t.getMessage());
                    }
                });
    }

    // Rechercher un utilisateur
    public void rechercher(String critere,
                           MutableLiveData<List<UtilisateurResponse>>
                                   successLiveData,
                           MutableLiveData<String> errorLiveData) {

        utilisateurApi.rechercher(critere).enqueue(
                new Callback<List<UtilisateurResponse>>() {

                    @Override
                    public void onResponse(
                            Call<List<UtilisateurResponse>> call,
                            Response<List<UtilisateurResponse>>
                                    response) {
                        if (response.isSuccessful()
                                && response.body() != null) {
                            successLiveData.postValue(response.body());
                        } else {
                            errorLiveData.postValue(
                                    "Aucun résultat trouvé");
                        }
                    }

                    @Override
                    public void onFailure(
                            Call<List<UtilisateurResponse>> call,
                            Throwable t) {
                        errorLiveData.postValue(
                                "Erreur réseau : " + t.getMessage());
                    }
                });
    }
}