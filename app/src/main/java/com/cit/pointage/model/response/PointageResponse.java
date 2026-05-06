package com.cit.pointage.model.response;

import com.google.gson.annotations.SerializedName;

public class PointageResponse {

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

    @SerializedName("type")
    private String type;

    @SerializedName("methode")
    private String methode;

    @SerializedName("dateHeure")
    private String dateHeure;

    @SerializedName("estRetard")
    private boolean estRetard;

    @SerializedName("horsLigne")
    private boolean horsLigne;

    public String getId() { return id; }
    public String getUtilisateurId() { return utilisateurId; }
    public String getNomCompletUtilisateur() {
        return nomCompletUtilisateur; }
    public String getPhotoUrl() { return photoUrl; }
    public String getMatricule() { return matricule; }
    public String getType() { return type; }
    public String getMethode() { return methode; }
    public String getDateHeure() { return dateHeure; }
    public boolean isEstRetard() { return estRetard; }
    public boolean isHorsLigne() { return horsLigne; }
}