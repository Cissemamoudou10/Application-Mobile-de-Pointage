package com.cit.pointage.model.request;

import com.google.gson.annotations.SerializedName;

public class UtilisateurRequest {

    @SerializedName("matricule")
    private String matricule;

    @SerializedName("nom")
    private String nom;

    @SerializedName("prenom")
    private String prenom;

    @SerializedName("photoUrl")
    private String photoUrl;

    @SerializedName("categorie")
    private String categorie;

    public UtilisateurRequest(String matricule, String nom,
                              String prenom, String photoUrl, String categorie) {
        this.matricule = matricule;
        this.nom = nom;
        this.prenom = prenom;
        this.photoUrl = photoUrl;
        this.categorie = categorie;
    }

    public String getMatricule() { return matricule; }
    public String getNom() { return nom; }
    public String getPrenom() { return prenom; }
    public String getPhotoUrl() { return photoUrl; }
    public String getCategorie() { return categorie; }
}