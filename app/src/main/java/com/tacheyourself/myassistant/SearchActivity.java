package com.tacheyourself.myassistant;

import android.content.Intent;
import android.speech.RecognizerIntent;
import android.speech.RecognizerResultsIntent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.tacheyourself.myassistant.adapter.HotelAdapter;
import com.tacheyourself.myassistant.internet.SendRequest;
import com.tacheyourself.myassistant.model.Hotel;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener,DataListener {

    private static final String TAG = "SearchActivity";
    private String name;
    private ImageView imageView;
    private int code=100;

    private HotelAdapter adapter;
    private ListView mListView;
    private List<Hotel> mHotelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        imageView=findViewById(R.id.imageView);

        mHotelList=new ArrayList<>();

        //remplissage de liste
        mListView=findViewById(R.id.liste);
        adapter=new HotelAdapter(this,R.layout.hotel_item,mHotelList);
        mListView.setAdapter(adapter);

        imageView.setOnClickListener(this);

        Intent intent=getIntent();

        if(intent.hasExtra("name")){
            name=intent.getStringExtra("name");
            Log.d(TAG,name);
            //! il faut supprimer
            //new SendRequest(this).getHotels("je cherche un piscine");
        }

    }

    @Override
    public void onClick(View v) {

        if(v.getId()==imageView.getId()){
            //! do our work here
            speak();
        }

    }

    private void speak(){

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent,code);

        } else {
            Toast.makeText(this, "Your Device Don't Support Speech Input", Toast.LENGTH_SHORT).show();
        }



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data) {

        if(requestCode==code && resultCode==RESULT_OK){

            List<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            //get data from our tables
            String spokenText = results.get(0);
            //! il faut supprimer les commenatires
            new SendRequest(this).getHotels(spokenText);
            Log.d(TAG,spokenText);

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void sendData(List<Hotel> liste,String type) {

        if("hotel".equals(type)) {


            mHotelList.addAll(liste);
            Log.d(TAG,"taille est " +mHotelList.size()+" "+mHotelList.get(0).toString());
            adapter.notifyDataSetChanged(); 


        }





    }
}