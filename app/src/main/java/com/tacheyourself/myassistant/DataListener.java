package com.tacheyourself.myassistant;


import com.tacheyourself.myassistant.model.Hotel;
import com.tacheyourself.myassistant.model.Restaurant;
import com.tacheyourself.myassistant.model.Site;
import com.tacheyourself.myassistant.model.Transport;

import java.util.List;

public interface DataListener {


    /**
     *
     * @param liste liste des object site,transport..
     * @param type of objet(transport ,tourstique....)
     */
    public void sendHotel(List<Hotel> liste, String type);

    public void sendSite(List<Site> liste, String type);

    public void sendRestaurant(List<Restaurant> liste, String type);
    public void sendTransport(List<Transport> liste, String type);
}
