package com.cit.pointage.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cit.pointage.model.response.AuthResponse;
import com.cit.pointage.repository.AuthRepository;

public class AuthViewModel extends ViewModel {

    private final AuthRepository authRepository;

    // LiveData observées par LoginActivity
    public MutableLiveData<AuthResponse> authSuccess =
            new MutableLiveData<>();
    public MutableLiveData<String> authError =
            new MutableLiveData<>();
    public MutableLiveData<Boolean> loading =
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
}