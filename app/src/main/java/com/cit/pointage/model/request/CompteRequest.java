package com.cit.pointage.model.request;

import com.google.gson.annotations.SerializedName;

public class CompteRequest {

    @SerializedName("nom")
    private String nom;

    @SerializedName("prenom")
    private String prenom;

    @SerializedName("login")
    private String login;

    @SerializedName("motDePasse")
    private String motDePasse;

    @SerializedName("role")
    private String role;

    public CompteRequest(String nom, String prenom, String login, String motDePasse, String role) {
        this.nom = nom;
        this.prenom = prenom;
        this.login = login;
        this.motDePasse = motDePasse;
        this.role = role;
    }

    public String getNom() { return nom; }
    public String getPrenom() { return prenom; }
    public String getLogin() { return login; }
    public String getMotDePasse() { return motDePasse; }
    public String getRole() { return role; }
}
