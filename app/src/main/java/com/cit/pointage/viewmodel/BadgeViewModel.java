package com.cit.pointage.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cit.pointage.model.response.BadgeResponse;
import com.cit.pointage.repository.BadgeRepository;

/**
 * ViewModel — Badges
 * Centralise toutes les opérations sur les badges (génération,
 * renouvellement, désactivation) avec un état LiveData par opération.
 */
public class BadgeViewModel extends ViewModel {

    private final BadgeRepository repository;

    // ════════ LiveData exposées à l'UI ════════

    /** Badge généré ou renouvelé avec succès */
    public final MutableLiveData<BadgeResponse> badgeSuccess =
            new MutableLiveData<>();

    /** Désactivation réussie */
    public final MutableLiveData<Boolean> desactiverSuccess =
            new MutableLiveData<>();

    /** Message d'erreur */
    public final MutableLiveData<String> error =
            new MutableLiveData<>();

    /** Indicateur de chargement */
    public final MutableLiveData<Boolean> loading =
            new MutableLiveData<>(false);

    public BadgeViewModel() {
        repository = new BadgeRepository();
    }

    // ════════ Actions ════════

    /** Génère un nouveau badge pour un utilisateur */
    public void generer(String utilisateurId) {
        loading.setValue(true);
        repository.generer(utilisateurId, badgeSuccess, error);
        loading.setValue(false);
    }

    /** Renouvelle le badge actif d'un utilisateur (désactive l'ancien + crée un nouveau) */
    public void renouveler(String utilisateurId) {
        loading.setValue(true);
        repository.renouveler(utilisateurId, badgeSuccess, error);
        loading.setValue(false);
    }

    /** Désactive un badge par son ID */
    public void desactiver(String badgeId) {
        loading.setValue(true);
        // BadgeRepository.desactiver retourne Void,
        // on map sur un Boolean pour l'UI
        MutableLiveData<Void> voidLiveData = new MutableLiveData<>();
        voidLiveData.observeForever(v -> desactiverSuccess.setValue(true));
        repository.desactiver(badgeId, voidLiveData, error);
        loading.setValue(false);
    }
}
