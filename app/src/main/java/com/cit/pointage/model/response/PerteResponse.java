package com.cit.pointage.model.response;

import com.google.gson.annotations.SerializedName;

public class PerteResponse {

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

    @SerializedName("typePerte")
    private String typePerte;

    @SerializedName("dateSignalement")
    private String dateSignalement;

    @SerializedName("traitee")
    private boolean traitee;

    public String getId() { return id; }
    public String getUtilisateurId() { return utilisateurId; }
    public String getNomCompletUtilisateur() {
        return nomCompletUtilisateur; }
    public String getPhotoUrl() { return photoUrl; }
    public String getMatricule() { return matricule; }
    public String getTypePerte() { return typePerte; }
    public String getDateSignalement() { return dateSignalement; }
    public boolean isTraitee() { return traitee; }
}