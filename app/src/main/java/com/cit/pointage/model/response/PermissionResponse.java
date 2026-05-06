package com.cit.pointage.model.response;

import com.google.gson.annotations.SerializedName;

public class PermissionResponse {

    @SerializedName("id")
    private String id;

    @SerializedName("utilisateurId")
    private String utilisateurId;

    @SerializedName("nomCompletUtilisateur")
    private String nomCompletUtilisateur;

    @SerializedName("photoUrl")
    private String photoUrl;

    @SerializedName("matricule")
    private String matricule;

    @SerializedName("dateDebut")
    private String dateDebut;

    @SerializedName("dateFin")
    private String dateFin;

    @SerializedName("qrCode")
    private String qrCode;

    @SerializedName("statut")
    private String statut;

    @SerializedName("valide")
    private boolean valide;

    @SerializedName("expiree")
    private boolean expiree;

    public String getId() { return id; }
    public String getUtilisateurId() { return utilisateurId; }
    public String getNomCompletUtilisateur() {
        return nomCompletUtilisateur; }
    public String getPhotoUrl() { return photoUrl; }
    public String getMatricule() { return matricule; }
    public String getDateDebut() { return dateDebut; }
    public String getDateFin() { return dateFin; }
    public String getQrCode() { return qrCode; }
    public String getStatut() { return statut; }
    public boolean isValide() { return valide; }
    public boolean isExpiree() { return expiree; }
}