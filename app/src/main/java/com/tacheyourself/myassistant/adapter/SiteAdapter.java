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

        TextView hotelname= convertView.findViewById(R.id.nomsite);

        TextView adresse= convertView.findViewById(R.id.adresse_site);

        TextView horaire= convertView.findViewById(R.id.site_horaire);
        TextView contact= convertView.findViewById(R.id.site_contact);
        TextView prix= convertView.findViewById(R.id.site_prix);
        TextView site= convertView.findViewById(R.id.sSite);

        String vide="";
        if(!(sSite.get(position).getAdresse()==null || "".equals(sSite.get(position).getAdresse())))
        {
            vide= sSite.get(position).getAdresse();

        }
        adresse.setText(vide);

        if(!(sSite.get(position).getHoraire()==null || sSite.get(position).getHoraire().equals("null") || "".equals(sSite.get(position).getHoraire()) ))
        {
            vide= sSite.get(position).getHoraire();

        }
        horaire.setText(vide);
        if(!(sSite.get(position).getContact()==null || "".equals(sSite.get(position).getContact())))
        {
            vide= sSite.get(position).getContact();

        }
        contact.setText(vide);

        if(!(sSite.get(position).getPrix()==null || "".equals(sSite.get(position).getPrix())))
        {
            vide= sSite.get(position).getPrix();

        }
        prix.setText(vide);

        if( !(sSite.get(position).getSite()==null || sSite.get(position).getSite().equals("null")|| "".equals(sSite.get(position).getSite()) ))
        {
            vide= sSite.get(position).getSite();

        }
        site.setText(vide);



        Log.d(TAG,sSite.get(position).getNom());
        hotelname.setText(sSite.get(position).getNom());


        return convertView;
    }
}
