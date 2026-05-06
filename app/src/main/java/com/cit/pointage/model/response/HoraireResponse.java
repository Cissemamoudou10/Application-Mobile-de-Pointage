package com.cit.pointage.model.response;

import com.google.gson.annotations.SerializedName;

/**
 * DTO réponse — correspond à l'entité Horaire du backend.
 * Champs : id, categorie, heureLimite, jours, actif, modifieLe
 */
public class HoraireResponse {

    @SerializedName("id")
    private String id;

    @SerializedName("categorie")
    private String categorie;

    // Format "HH:mm:ss" renvoyé par Spring Boot (LocalTime)
    @SerializedName("heureLimite")
    private String heureLimite;

    // Format "MONDAY,TUESDAY,..." séparé par virgules
    @SerializedName("jours")
    private String jours;

    @SerializedName("actif")
    private boolean actif;

    @SerializedName("modifieLe")
    private String modifieLe;

    // ════════ Getters ════════

    public String getId() { return id; }

    public String getCategorie() { return categorie; }

    public String getHeureLimite() { return heureLimite; }

    public String getJours() { return jours; }

    public boolean isActif() { return actif; }

    public String getModifieLe() { return modifieLe; }

    // ════════ Méthodes utilitaires pour l'affichage ════════

    /**
     * Retourne l'heure limite au format HH:mm (sans les secondes).
     */
    public String getHeureLimiteAffichage() {
        if (heureLimite == null) return "--:--";
        // "07:45:00" → "07:45"
        return heureLimite.length() >= 5 ? heureLimite.substring(0, 5) : heureLimite;
    }

    /**
     * Retourne le libellé lisible de la catégorie.
     */
    public String getCategorieLibelle() {
        if (categorie == null) return "";
        switch (categorie) {
            case "ELEVE":               return "Élèves";
            case "OFFICIER_STAGIAIRE":  return "Officiers stagiaires";
            case "PERSONNEL_ADMIN":     return "Personnel administratif";
            case "PERSONNEL_ENCADRANT": return "Personnel encadrant";
            default:                    return categorie;
        }
    }

    /**
     * Retourne les jours sous forme lisible.
     * "MONDAY,TUESDAY" → "Lun, Mar"
     */
    public String getJoursAffichage() {
        if (jours == null || jours.isEmpty()) return "";
        String[] parts = jours.split(",");
        StringBuilder sb = new StringBuilder();
        for (String j : parts) {
            if (sb.length() > 0) sb.append(", ");
            sb.append(getJourCourt(j.trim()));
        }
        return sb.toString();
    }

    private String getJourCourt(String jour) {
        switch (jour) {
            case "MONDAY":    return "Lun";
            case "TUESDAY":   return "Mar";
            case "WEDNESDAY": return "Mer";
            case "THURSDAY":  return "Jeu";
            case "FRIDAY":    return "Ven";
            case "SATURDAY":  return "Sam";
            case "SUNDAY":    return "Dim";
            default:          return jour;
        }
    }
}
