package com.tacheyourself.myassistant.model;
/*
 **    *** my Assistant ***
 **   Created by EL KHARROUBI HASSAN
 **   At Saturday July 2021 18H 20MIN
 */


public class Transport {
    private String type;
    private String tPrix;
    private String station;

    public Transport(){

    }
    public Transport(String type, String prix, String station ) {
        this.type = type;
        tPrix = prix;
        this.station = station;
    }
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPrix() {
        return tPrix;
    }

    public void settPrix(String prix) {
        tPrix = prix;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }
}
