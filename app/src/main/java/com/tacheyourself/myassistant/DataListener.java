package com.tacheyourself.myassistant;
/*
 **    *** my Assistant ***
 **   Created by EL KHARROUBI HASSAN
 **   At Friday July 2021 12H 34MIN
 */


import com.tacheyourself.myassistant.model.Hotel;

import java.util.List;

public interface DataListener {


    /**
     *
     * @param liste liste des object site,transport..
     * @param type of objet(transport ,tourstique....)
     */
    public void sendData(List<Hotel> liste, String type);
}
