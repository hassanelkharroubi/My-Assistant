package com.tacheyourself.myassistant.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.tacheyourself.myassistant.R;
import com.tacheyourself.myassistant.model.Site;


import java.util.List;

/**
 * created by Youssef Talambouti on
 */
public class SiteAdapter extends ArrayAdapter<Site> {
    private static final String TAG = "SiteAdapter";
    private Context sContext;
    private List<Site> sSite;
    private int sResource;

    public SiteAdapter( Context context, int resource,  List<Site> objects) {
        super(context, resource, objects);
        sContext=context;
        sResource=resource;
        sSite =objects;
        Log.d(TAG,"inside SiteAdapter");
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView==null){
            convertView= LayoutInflater.from(sContext).inflate(sResource,null);
        }

        TextView hotelname= convertView.findViewById(R.id.nomHotel);

        TextView adresse= convertView.findViewById(R.id.adresse);

        Log.d(TAG,sSite.get(position).getNom());
        hotelname.setText(sSite.get(position).getNom());

        adresse.setText(sSite.get(position).getAdresse());

        return convertView;
    }
}
