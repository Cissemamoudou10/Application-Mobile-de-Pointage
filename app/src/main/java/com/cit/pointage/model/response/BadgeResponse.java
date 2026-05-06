package com.cit.pointage.model.response;

import com.google.gson.annotations.SerializedName;

public class BadgeResponse {

    @SerializedName("id")
    private String id;

    @SerializedName("utilisateurId")
    private String utilisateurId;

    @SerializedName("nomCompletUtilisateur")
    private String nomCompletUtilisateur;

    @SerializedName("photoUrl")
    private String photoUrl;

    @SerializedName("qrCode")
    private String qrCode;

    @SerializedName("actif")
    private boolean actif;

    @SerializedName("dateCreation")
    private String dateCreation;

    public String getId() { return id; }
    public String getUtilisateurId() { return utilisateurId; }
    public String getNomCompletUtilisateur() {
        return nomCompletUtilisateur; }
    public String getPhotoUrl() { return photoUrl; }
    public String getQrCode() { return qrCode; }
    public boolean isActif() { return actif; }
    public String getDateCreation() { return dateCreation; }
}