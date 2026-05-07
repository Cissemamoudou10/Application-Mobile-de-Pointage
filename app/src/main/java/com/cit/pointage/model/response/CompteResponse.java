package com.cit.pointage.model.response;

import com.google.gson.annotations.SerializedName;

public class CompteResponse {

    @SerializedName("id")
    private String id;

    @SerializedName("nomComplet")
    private String nomComplet;

    @SerializedName("login")
    private String login;

    @SerializedName("role")
    private String role;

    @SerializedName("statut")
    private String statut;

    public String getId() { return id; }
    public String getNomComplet() { return nomComplet; }
    public String getLogin() { return login; }
    public String getRole() { return role; }
    public String getStatut() { return statut; }
}
