package com.cit.pointage.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cit.pointage.model.request.PointageRequest;
import com.cit.pointage.model.response.PointageResponse;
import com.cit.pointage.repository.PointageRepository;

import java.util.List;

public class PointageViewModel extends ViewModel {

    private final PointageRepository repository;

    public MutableLiveData<PointageResponse> pointageSuccess =
            new MutableLiveData<>();
    public MutableLiveData<List<PointageResponse>>
            retardataires = new MutableLiveData<>();
    public MutableLiveData<List<PointageResponse>>
            presenceRecente = new MutableLiveData<>();
    public MutableLiveData<String> error =
            new MutableLiveData<>();
    public MutableLiveData<Boolean> loading =
            new MutableLiveData<>();

    public PointageViewModel() {
        repository = new PointageRepository();
    }

    // Pointage par scan QR badge
    public void scannerBadge(String qrCode,
                             String typePointage, boolean horsLigne) {
        loading.setValue(true);
        PointageRequest request = new PointageRequest(
                qrCode, typePointage, "QR_SCAN", horsLigne);
        repository.pointer(request, pointageSuccess, error);
    }

    // Pointage par scan QR titre de permission
    public void scannerPermission(String qrCode,
                                  String typePointage, boolean horsLigne) {
        loading.setValue(true);
        PointageRequest request = new PointageRequest(
                qrCode, typePointage, "QR_SCAN", horsLigne);
        repository.pointer(request, pointageSuccess, error);
    }

    // Pointage manuel
    public void validerManuellement(String utilisateurId,
                                    String typePointage, boolean horsLigne) {
        loading.setValue(true);
        PointageRequest request = new PointageRequest(
                utilisateurId, typePointage, horsLigne);
        repository.pointer(request, pointageSuccess, error);
    }

    // Charger les retardataires
    public void chargerRetardataires() {
        loading.setValue(true);
        repository.retardataires(retardataires, error);
    }

    // Charger la présence récente (48h)
    public void chargerPresenceRecente() {
        loading.setValue(true);
        repository.presenceRecente(presenceRecente, error);
    }
}