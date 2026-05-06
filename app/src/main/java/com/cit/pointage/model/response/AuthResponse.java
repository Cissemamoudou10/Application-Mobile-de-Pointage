package com.cit.pointage.model.response;

import com.google.gson.annotations.SerializedName;

public class AuthResponse {

    @SerializedName("token")
    private String token;

    @SerializedName("login")
    private String login;

    @SerializedName("nomComplet")
    private String nomComplet;

    @SerializedName("role")
    private String role;

    public String getToken() { return token; }
    public String getLogin() { return login; }
    public String getNomComplet() { return nomComplet; }
    public String getRole() { return role; }
}