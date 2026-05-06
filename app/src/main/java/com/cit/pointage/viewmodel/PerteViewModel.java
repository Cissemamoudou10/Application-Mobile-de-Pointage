package com.cit.pointage.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cit.pointage.model.request.PerteRequest;
import com.cit.pointage.model.response.PerteResponse;
import com.cit.pointage.repository.PerteRepository;

import java.util.List;

public class PerteViewModel extends ViewModel {

    private final PerteRepository repository;

    public MutableLiveData<PerteResponse> signalerSuccess =
            new MutableLiveData<>();
    public MutableLiveData<List<PerteResponse>> pertes =
            new MutableLiveData<>();
    public MutableLiveData<String> error =
            new MutableLiveData<>();
    public MutableLiveData<Boolean> loading =
            new MutableLiveData<>();

    public PerteViewModel() {
        repository = new PerteRepository();
    }

    public void signaler(String utilisateurId,
                         String typePerte, String typePointage) {
        loading.setValue(true);
        PerteRequest request = new PerteRequest(
                utilisateurId, typePerte, typePointage);
        repository.signaler(
                request, signalerSuccess, error);
    }

    public void chargerTout() {
        loading.setValue(true);
        repository.toutes(pertes, error);
    }
}