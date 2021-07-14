package com.tacheyourself.myassistant.model;

/**
 * created by Youssef Talambouti on
 */
public class Site {
    private String sNom;
    private String sAdresse;
    private String sHoraire;
    private String sSite;
    private String sPrix;
    private String sContact;

    public Site(){

    }
    public Site(String nom, String adresse, String horaire, String site, String prix,String contact) {
        sNom = nom;
        sAdresse = adresse;
        sHoraire = horaire;
        sSite = site;
        sPrix = prix;
        sContact=contact;
    }
    public String getNom() {
        return sNom;
    }

    public void setNom(String nom) {
        sNom = nom;
    }

    public String getAdresse() {
        return sAdresse;
    }

    public void setAdresse(String adresse) {
        sAdresse = adresse;
    }

    public String getHoraire() {
        return sHoraire;
    }

    public void setHoraire(String horaire) {
        sHoraire = horaire;
    }



    public String getPrix() {
        return sPrix;
    }

    public void setRepas(String prix) {
        sPrix = prix;
    }

    public String getSite() {
        return sSite;
    }

    public void setSite(String site) {
        sSite = site;
    }
    public String getContact() {
        return sContact;
    }

    public void setContact(String contact) {
        sContact = contact;
    }

    public String toString() {
        return
                "Nom=" + sNom + '\n' +
                        "Adresse=" + sAdresse + '\n';

    }
}
