package com.cit.pointage.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cit.pointage.model.request.PermissionRequest;
import com.cit.pointage.model.response.PermissionResponse;
import com.cit.pointage.repository.PermissionRepository;

import java.util.List;

/**
 * ViewModel — Permissions
 * Gère : créer, annuler, lister les permissions en cours et par utilisateur.
 */
public class PermissionViewModel extends ViewModel {

    private final PermissionRepository repository;

    // ════════ LiveData exposées à l'UI ════════

    /** Liste des permissions en cours */
    public final MutableLiveData<List<PermissionResponse>> permissionsEnCours =
            new MutableLiveData<>();

    /** Permission créée ou annulée avec succès */
    public final MutableLiveData<PermissionResponse> permissionSuccess =
            new MutableLiveData<>();

    /** Message d'erreur */
    public final MutableLiveData<String> error =
            new MutableLiveData<>();

    /** Indicateur de chargement */
    public final MutableLiveData<Boolean> loading =
            new MutableLiveData<>(false);

    public PermissionViewModel() {
        repository = new PermissionRepository();
    }

    // ════════ Actions ════════

    /** Charge toutes les permissions actuellement en cours */
    public void chargerPermissionsEnCours() {
        loading.setValue(true);
        repository.enCours(permissionsEnCours, error);
        loading.setValue(false);
    }

    /** Crée une nouvelle permission de sortie */
    public void creerPermission(PermissionRequest request) {
        loading.setValue(true);
        repository.creer(request, permissionSuccess, error);
        loading.setValue(false);
    }

    /** Annule une permission par son ID */
    public void annulerPermission(String id) {
        loading.setValue(true);
        repository.annuler(id, permissionSuccess, error);
        loading.setValue(false);
    }
}
