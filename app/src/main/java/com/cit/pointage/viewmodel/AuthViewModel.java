package com.cit.pointage.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cit.pointage.model.request.CompteRequest;
import com.cit.pointage.model.response.AuthResponse;
import com.cit.pointage.model.response.CompteResponse;
import com.cit.pointage.repository.AuthRepository;

import java.util.List;

public class AuthViewModel extends ViewModel {

    private final AuthRepository authRepository;

    // LiveData observées par LoginActivity
    public MutableLiveData<AuthResponse> authSuccess =
            new MutableLiveData<>();
    public MutableLiveData<String> authError =
            new MutableLiveData<>();
    public MutableLiveData<Boolean> loading =
            new MutableLiveData<>();

    // LiveData pour la création de compte
    public MutableLiveData<CompteResponse> compteSuccess =
            new MutableLiveData<>();
    public MutableLiveData<String> compteError =
            new MutableLiveData<>();

    // LiveData pour la liste des comptes
    public MutableLiveData<List<CompteResponse>> comptesListe =
            new MutableLiveData<>();

    // LiveData pour le changement de statut
    public MutableLiveData<CompteResponse> statutSuccess =
            new MutableLiveData<>();
    public MutableLiveData<String> listeError =
            new MutableLiveData<>();
    public MutableLiveData<String> statutError =
            new MutableLiveData<>();

    public AuthViewModel() {
        authRepository = new AuthRepository();
    }

    public void login(String login, String motDePasse) {
        // Affiche le chargement
        loading.setValue(true);

        authRepository.login(
                login,
                motDePasse,
                authSuccess,
                authError
        );
    }

    public void creerCompte(String nom, String prenom, String login, String motDePasse, String role) {
        loading.setValue(true);
        CompteRequest request = new CompteRequest(nom, prenom, login, motDePasse, role);
        authRepository.creerCompte(request, compteSuccess, compteError);
    }

    public void getComptes() {
        loading.setValue(true);
        authRepository.getComptes(comptesListe, listeError);
    }

    public void changerStatut(String id, String statut) {
        authRepository.changerStatut(id, statut, statutSuccess, statutError);
    }
}