package com.cit.pointage.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cit.pointage.model.response.HoraireResponse;
import com.cit.pointage.repository.HoraireRepository;

import java.util.List;

/**
 * ViewModel — Horaires
 * Gère l'état de l'écran HorairesActivity via LiveData.
 * Survit aux rotations d'écran (cycle de vie Activity).
 */
public class HoraireViewModel extends ViewModel {

    private final HoraireRepository repository;

    // ════════ LiveData exposées à l'UI ════════

    /** Liste de tous les horaires chargés depuis le serveur */
    public final MutableLiveData<List<HoraireResponse>> horaires =
            new MutableLiveData<>();

    /** Résultat d'une configuration (POST) réussie */
    public final MutableLiveData<HoraireResponse> configurerSuccess =
            new MutableLiveData<>();

    /** Résultat d'une désactivation (PUT) réussie */
    public final MutableLiveData<Boolean> desactiverSuccess =
            new MutableLiveData<>();

    /** Message d'erreur réseau ou serveur */
    public final MutableLiveData<String> error =
            new MutableLiveData<>();

    /** État de chargement (affiche/masque le ProgressBar) */
    public final MutableLiveData<Boolean> loading =
            new MutableLiveData<>(false);

    public HoraireViewModel() {
        repository = new HoraireRepository();
    }

    // ════════ Actions ════════

    /** Charge tous les horaires depuis le backend */
    public void chargerHoraires() {
        loading.setValue(true);
        repository.tous(horaires, error);
        loading.setValue(false);
    }

    /**
     * Configure un horaire pour une catégorie donnée.
     *
     * @param categorie  Enum string (ex: "PERSONNEL_ADMIN")
     * @param heureLimite Format "HH:mm" (ex: "07:45")
     * @param jours       Jours séparés par virgules (ex: "MONDAY,TUESDAY,WEDNESDAY,THURSDAY,FRIDAY")
     */
    public void configurerHoraire(String categorie,
                                  String heureLimite,
                                  String jours) {
        loading.setValue(true);
        repository.configurer(categorie, heureLimite, jours,
                configurerSuccess, error);
        loading.setValue(false);
    }

    /** Désactive un horaire par son ID */
    public void desactiverHoraire(String id) {
        loading.setValue(true);
        repository.desactiver(id, desactiverSuccess, error);
        loading.setValue(false);
    }
}
