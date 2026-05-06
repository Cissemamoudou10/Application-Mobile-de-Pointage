package com.cit.pointage.model.request;

import com.google.gson.annotations.SerializedName;

public class LoginRequest {

    @SerializedName("login")
    private String login;

    @SerializedName("motDePasse")
    private String motDePasse;

    public LoginRequest(String login, String motDePasse) {
        this.login = login;
        this.motDePasse = motDePasse;
    }

    public String getLogin() { return login; }
    public String getMotDePasse() { return motDePasse; }
}