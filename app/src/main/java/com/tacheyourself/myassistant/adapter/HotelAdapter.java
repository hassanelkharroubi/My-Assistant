package com.tacheyourself.myassistant.adapter;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.tacheyourself.myassistant.R;
import com.tacheyourself.myassistant.model.Hotel;

import java.util.List;


public class HotelAdapter  extends ArrayAdapter<Hotel> {

    private static final String TAG = "HotelAdapter";
    private Context mContext;
    private List<Hotel> mHotels;
    private int mResource;

    public HotelAdapter( Context context, int resource,  List<Hotel> objects) {
        super(context, resource, objects);
        mContext=context;
        mResource=resource;
        mHotels =objects;
        Log.d(TAG,"inside HotelAdapter");
    }

    @Override
    public View getView(int position,  View convertView, ViewGroup parent) {

        if(convertView==null){
            convertView= LayoutInflater.from(mContext).inflate(mResource,null);
        }

        TextView hotelname= convertView.findViewById(R.id.nomHotel);

        TextView adresse= convertView.findViewById(R.id.adresse);
        TextView point_fort=convertView.findViewById(R.id.mPoint_fort);
        TextView evaluationView=convertView.findViewById(R.id.evaluation);

        RatingBar ratingBar   =  convertView.findViewById(R.id.ratingBar2);

        ratingBar.setMax(5);

        Log.d(TAG,mHotels.get(position).getNom());
        hotelname.setText(mHotels.get(position).getNom());

        adresse.setText(mHotels.get(position).getAdresse());

        //prix.setText( mHotels.get(position).getPrice());

       // evaluation.setText((int) mHotels.get(position).getEvaluation());
        //stars.setText(mHotels.get(position).getStars());

        point_fort.setText(mHotels.get(position).getPointFort());
        //lieu.setText(mHotels.get(position).getLieuxApprox());

        evaluationView.setText("Evaluation: "+mHotels.get(position).getEvaluation());

        ratingBar.setRating(mHotels.get(position).getStars());
        ratingBar.setIsIndicator(true);



        return convertView;
    }



}

