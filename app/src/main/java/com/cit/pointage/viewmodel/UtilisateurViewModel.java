package com.cit.pointage.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cit.pointage.model.request.UtilisateurRequest;
import com.cit.pointage.model.response.UtilisateurResponse;
import com.cit.pointage.repository.UtilisateurRepository;

import java.util.List;

public class UtilisateurViewModel extends ViewModel {

    private final UtilisateurRepository repository;

    public MutableLiveData<UtilisateurResponse> createSuccess =
            new MutableLiveData<>();
    public MutableLiveData<List<UtilisateurResponse>>
            utilisateurs = new MutableLiveData<>();
    public MutableLiveData<List<UtilisateurResponse>>
            resultatsRecherche = new MutableLiveData<>();
    public MutableLiveData<String> error =
            new MutableLiveData<>();
    public MutableLiveData<Boolean> loading =
            new MutableLiveData<>();

    public UtilisateurViewModel() {
        repository = new UtilisateurRepository();
    }

    public void creer(UtilisateurRequest request) {
        loading.setValue(true);
        repository.creer(request, createSuccess, error);
    }

    public void chargerTous() {
        loading.setValue(true);
        repository.tousLesUtilisateurs(utilisateurs, error);
    }

    public void rechercher(String critere) {
        loading.setValue(true);
        repository.rechercher(
                critere, resultatsRecherche, error);
    }
}