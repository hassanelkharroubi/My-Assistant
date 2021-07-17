package com.tacheyourself.myassistant.model;

/**
 * created by Youssef Talambouti on
 */
public class Restaurant {
    private String rNom;
    private String rAdresse;
    private String rHoraire;
    private String rRepas;
    private String rFonctionalite;
    private float revaluation;

    public float getRevaluation() {
        return revaluation;
    }

    public void setRevaluation(float revaluation) {
        this.revaluation = revaluation;
    }

    public Restaurant(){

    }
    public Restaurant(String nom, String adresse, String horaire, String repas, String fonctionalité, float evaluation) {
        rNom = nom;
        rAdresse = adresse;
        rHoraire = horaire;
        rRepas = repas;
        rFonctionalite = fonctionalité;
        revaluation=evaluation;
    }
    public String getNom() {
        return rNom;
    }

    public void setNom(String nom) {
        rNom = nom;
    }

    public String getAdresse() {
        return rAdresse;
    }

    public void setAdresse(String adresse) {
        rAdresse = adresse;
    }

    public String getHoraire() {
        return rHoraire;
    }

    public void setHoraire(String horaire) {
        rHoraire = horaire;
    }



    public String getRepas() {
        return rRepas;
    }

    public void setRepas(String repas) {
        rRepas = repas;
    }

    public String getFonctionalite() {
        return rFonctionalite;
    }

    public void setAearportProche(String fonctionalité) {
        rFonctionalite = fonctionalité;
    }

    public String toString() {
        return
                "Nom=" + rNom + '\n' +
                        "Adresse=" + rAdresse + '\n';

    }
}
