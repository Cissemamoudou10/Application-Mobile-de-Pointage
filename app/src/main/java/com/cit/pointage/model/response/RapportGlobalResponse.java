package com.cit.pointage.model.response;

import java.util.List;

public class RapportGlobalResponse {
    private long totalUtilisateurs;
    private long presents;
    private long absents;
    private long enPermission;
    private long enRetard;
    private List<DetailPresenceResponse> details;

    // Getters and Setters
    public long getTotalUtilisateurs() { return totalUtilisateurs; }
    public void setTotalUtilisateurs(long totalUtilisateurs) { this.totalUtilisateurs = totalUtilisateurs; }
    public long getPresents() { return presents; }
    public void setPresents(long presents) { this.presents = presents; }
    public long getAbsents() { return absents; }
    public void setAbsents(long absents) { this.absents = absents; }
    public long getEnPermission() { return enPermission; }
    public void setEnPermission(long enPermission) { this.enPermission = enPermission; }
    public long getEnRetard() { return enRetard; }
    public void setEnRetard(long enRetard) { this.enRetard = enRetard; }
    public List<DetailPresenceResponse> getDetails() { return details; }
    public void setDetails(List<DetailPresenceResponse> details) { this.details = details; }
}
