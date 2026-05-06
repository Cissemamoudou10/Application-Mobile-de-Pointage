package com.cit.pointage.model.request;

import com.google.gson.annotations.SerializedName;

public class PerteRequest {

    @SerializedName("utilisateurId")
    private String utilisateurId;

    @SerializedName("typePerte")
    private String typePerte;

    @SerializedName("typePointage")
    private String typePointage;

    public PerteRequest(String utilisateurId,
                        String typePerte, String typePointage) {
        this.utilisateurId = utilisateurId;
        this.typePerte = typePerte;
        this.typePointage = typePointage;
    }

    public String getUtilisateurId() { return utilisateurId; }
    public String getTypePerte() { return typePerte; }
    public String getTypePointage() { return typePointage; }
}