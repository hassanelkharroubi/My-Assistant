package com.tacheyourself.myassistant.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.tacheyourself.myassistant.R;
import com.tacheyourself.myassistant.model.Restaurant;


import java.util.List;

/**
 * created by Youssef Talambouti on
 */
public class RestaurantAdapter extends ArrayAdapter<Restaurant> {
    private static final String TAG = "RestaurantAdapter";
    private Context rContext;
    private List<Restaurant> rRestaurant;
    private int rResource;

    public RestaurantAdapter( Context context, int resource,  List<Restaurant> objects) {
        super(context, resource, objects);
        rContext=context;
        rResource=resource;
        rRestaurant =objects;
        Log.d(TAG,"inside RestaurantAdapter");
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView==null){
            convertView= LayoutInflater.from(rContext).inflate(rResource,null);
        }

        TextView hotelname= convertView.findViewById(R.id.nomHotel);

        TextView adresse= convertView.findViewById(R.id.adresse);

        Log.d(TAG,rRestaurant.get(position).getNom());
        hotelname.setText(rRestaurant.get(position).getNom());

        adresse.setText(rRestaurant.get(position).getAdresse());

        return convertView;
    }
}
