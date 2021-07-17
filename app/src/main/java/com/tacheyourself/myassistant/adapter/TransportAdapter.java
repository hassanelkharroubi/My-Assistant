package com.tacheyourself.myassistant.adapter;
/*
 **    *** my Assistant ***
 **   Created by EL KHARROUBI HASSAN
 **   At Saturday July 2021 18H 21MIN
 */


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.tacheyourself.myassistant.R;
import com.tacheyourself.myassistant.model.Transport;

import java.util.List;

public class TransportAdapter extends ArrayAdapter<Transport> {
    private static final String TAG = "TransportAdapter";
    private Context tContext;
    private List<Transport> tTransprt;
    private int tResource;
    public TransportAdapter(Context context, int resource, List<Transport> objects) {
        super(context, resource, objects);
        tContext=context;
        tResource=resource;
        tTransprt =objects;
        Log.d(TAG,"inside TransportAdapter");
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView==null){
            convertView= LayoutInflater.from(tContext).inflate(tResource,null);
        }

        TextView type= convertView.findViewById(R.id.type_trans);

        TextView station= convertView.findViewById(R.id.trans_station);

        TextView prix= convertView.findViewById(R.id.trans_prix);

        Log.d(TAG,tTransprt.get(position).getType()+" ");
        type.setText(tTransprt.get(position).getType());

        station.setText(tTransprt.get(position).getStation().equals("")?" ":tTransprt.get(position).getStation());

        prix.setText(tTransprt.get(position).getPrix());

        return convertView;
    }
}

