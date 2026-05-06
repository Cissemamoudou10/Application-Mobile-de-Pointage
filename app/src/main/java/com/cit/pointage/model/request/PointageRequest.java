package com.cit.pointage.model.request;

import com.google.gson.annotations.SerializedName;
import java.util.UUID;

public class PointageRequest {

    @SerializedName("qrCode")
    private String qrCode;

    @SerializedName("type")
    private String type;

    @SerializedName("methode")
    private String methode;

    @SerializedName("utilisateurId")
    private String utilisateurId;

    @SerializedName("horsLigne")
    private boolean horsLigne;

    // Constructeur pour scan QR
    public PointageRequest(String qrCode, String type,
                           String methode, boolean horsLigne) {
        this.qrCode = qrCode;
        this.type = type;
        this.methode = methode;
        this.horsLigne = horsLigne;
    }

    // Constructeur pour pointage manuel
    public PointageRequest(String utilisateurId, String type,
                           boolean horsLigne) {
        this.utilisateurId = utilisateurId;
        this.type = type;
        this.methode = "MANUEL";
        this.horsLigne = horsLigne;
    }

    // Getters
    public String getQrCode() { return qrCode; }
    public String getType() { return type; }
    public String getMethode() { return methode; }
    public String getUtilisateurId() { return utilisateurId; }
    public boolean isHorsLigne() { return horsLigne; }
}