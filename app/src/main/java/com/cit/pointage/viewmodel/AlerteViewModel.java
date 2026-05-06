package com.cit.pointage.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cit.pointage.model.response.AlerteResponse;
import com.cit.pointage.repository.AlerteRepository;

import java.util.List;

public class AlerteViewModel extends ViewModel {

    private final AlerteRepository repository;

    public MutableLiveData<List<AlerteResponse>> alertes =
            new MutableLiveData<>();
    public MutableLiveData<AlerteResponse> alerteLue =
            new MutableLiveData<>();
    public MutableLiveData<String> error =
            new MutableLiveData<>();
    public MutableLiveData<Boolean> loading =
            new MutableLiveData<>();

    public AlerteViewModel() {
        repository = new AlerteRepository();
    }

    public void chargerNonLues() {
        loading.setValue(true);
        repository.nonLues(alertes, error);
    }

    public void marquerLue(String alerteId) {
        repository.marquerLue(alerteId, alerteLue, error);
    }
}