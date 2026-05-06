package com.cit.pointage.model.response;

import com.google.gson.annotations.SerializedName;

public class UtilisateurResponse {

    @SerializedName("id")
    private String id;

    @SerializedName("matricule")
    private String matricule;

    @SerializedName("nom")
    private String nom;

    @SerializedName("prenom")
    private String prenom;

    @SerializedName("nomComplet")
    private String nomComplet;

    @SerializedName("photoUrl")
    private String photoUrl;

    @SerializedName("categorie")
    private String categorie;

    @SerializedName("statut")
    private String statut;

    @SerializedName("peutSortirAvecBadge")
    private boolean peutSortirAvecBadge;

    @SerializedName("necessitePermission")
    private boolean necessitePermission;

    public String getId() { return id; }
    public String getMatricule() { return matricule; }
    public String getNom() { return nom; }
    public String getPrenom() { return prenom; }
    public String getNomComplet() { return nomComplet; }
    public String getPhotoUrl() { return photoUrl; }
    public String getCategorie() { return categorie; }
    public String getStatut() { return statut; }
    public boolean isPeutSortirAvecBadge() {
        return peutSortirAvecBadge; }
    public boolean isNecessitePermission() {
        return necessitePermission; }
}