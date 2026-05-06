package com.cit.pointage.model.response;

import com.google.gson.annotations.SerializedName;

public class AlerteResponse {

    @SerializedName("id")
    private String id;

    @SerializedName("perteId")
    private String perteId;

    @SerializedName("destinataireId")
    private String destinataireId;

    @SerializedName("lue")
    private boolean lue;

    @SerializedName("envoyeeLe")
    private String envoyeeLe;

    @SerializedName("nomCompletUtilisateur")
    private String nomCompletUtilisateur;

    @SerializedName("typePerte")
    private String typePerte;

    public String getId() { return id; }
    public String getPerteId() { return perteId; }
    public String getDestinataireId() { return destinataireId; }
    public boolean isLue() { return lue; }
    public String getEnvoyeeLe() { return envoyeeLe; }
    public String getNomCompletUtilisateur() {
        return nomCompletUtilisateur; }
    public String getTypePerte() { return typePerte; }
}