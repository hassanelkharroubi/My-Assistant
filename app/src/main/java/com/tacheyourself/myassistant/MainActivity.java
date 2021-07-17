package com.tacheyourself.myassistant;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private Button mHotel,mTransport,mSite,mRestaurant;
    private final static String TAG="MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mHotel=findViewById(R.id.Hotel);
        mTransport=findViewById(R.id.transport);
        mRestaurant=findViewById(R.id.Restaurant);
        mSite=findViewById(R.id.Site_touristque);


        mHotel.setOnClickListener(this);
        mTransport.setOnClickListener(this);
        mRestaurant.setOnClickListener(this);
        mSite.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        int id=v.getId();
        // pour le traitement des hotels
        if(id==mHotel.getId()){

            Log.d(TAG," hotel is clicked ");

            startSearchActivity("etablissement");
            return;
        }

        //pour traitemenet des rest...
        if(id==mRestaurant.getId()){

            Log.d(TAG," restaurant is clicked ");

            startSearchActivity("restaurant");
            return;
        }
        if(id==mSite.getId()){
            Log.d(TAG," site is clicked ");

            startSearchActivity("site");
            return;
        }

        if(id==mTransport.getId()){
            Log.d(TAG," transport is clicked ");

            startSearchActivity("transport");
            return;
        }

        
    }


    private void startSearchActivity(String name) {

        Intent intent=new Intent(this,SearchActivity.class);
        intent.putExtra("name",name);

        startActivity(intent);

    }


}