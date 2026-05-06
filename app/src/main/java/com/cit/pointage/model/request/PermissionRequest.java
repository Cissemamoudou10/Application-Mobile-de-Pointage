package com.cit.pointage.model.request;

import com.google.gson.annotations.SerializedName;

public class PermissionRequest {

    @SerializedName("utilisateurId")
    private String utilisateurId;

    @SerializedName("dateDebut")
    private String dateDebut;

    @SerializedName("dateFin")
    private String dateFin;

    @SerializedName("saisieManuelle")
    private boolean saisieManuelle;

    public PermissionRequest(String utilisateurId,
                             String dateDebut, String dateFin,
                             boolean saisieManuelle) {
        this.utilisateurId = utilisateurId;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.saisieManuelle = saisieManuelle;
    }

    public String getUtilisateurId() { return utilisateurId; }
    public String getDateDebut() { return dateDebut; }
    public String getDateFin() { return dateFin; }
    public boolean isSaisieManuelle() { return saisieManuelle; }
}