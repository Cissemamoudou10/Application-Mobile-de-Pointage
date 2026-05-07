package com.cit.pointage.model.response;

public class DetailPresenceResponse {
    private String id;
    private String nomComplet;
    private String matricule;
    private String statutDuJour;
    private String heurePointage;

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getNomComplet() { return nomComplet; }
    public void setNomComplet(String nomComplet) { this.nomComplet = nomComplet; }
    public String getMatricule() { return matricule; }
    public void setMatricule(String matricule) { this.matricule = matricule; }
    public String getStatutDuJour() { return statutDuJour; }
    public void setStatutDuJour(String statutDuJour) { this.statutDuJour = statutDuJour; }
    public String getHeurePointage() { return heurePointage; }
    public void setHeurePointage(String heurePointage) { this.heurePointage = heurePointage; }
}
