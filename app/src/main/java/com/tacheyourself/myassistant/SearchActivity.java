package com.tacheyourself.myassistant;

import android.app.Dialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.speech.RecognizerIntent;
import android.speech.RecognizerResultsIntent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
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
    private List<Hotel> mHotelListCopy;
    //va nous servir pour le filtrage de donnees
    private List<Hotel> filtredList;


    private Button nonBtn,ouiBtn,supprimerFiltreBtn;


    private LinearLayout filter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        imageView=findViewById(R.id.imageView);


        nonBtn=findViewById(R.id.non);
        ouiBtn=findViewById(R.id.oui);
        supprimerFiltreBtn=findViewById(R.id.supprimerFiltre);
        supprimerFiltreBtn.setVisibility(View.GONE);
        nonBtn.setOnClickListener(this);
        ouiBtn.setOnClickListener(this);
        supprimerFiltreBtn.setOnClickListener(this);


        filter=findViewById(R.id.filtre);
        filter.setVisibility(View.GONE);

        mHotelList=new ArrayList<>();
        mHotelListCopy=new ArrayList<>();
        filtredList=new ArrayList<Hotel>();

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
            return;
        }

        if(v.getId()==nonBtn.getId()){
            //! handle event of non btn
            return;
        }
        if(v.getId()==ouiBtn.getId()){

            //show dialogbox for filtering data
            enableFiltering();


            return;
        } if(v.getId()==supprimerFiltreBtn.getId()){

            //show dialogbox for filtering data
            mHotelList.clear();
            mHotelList.addAll(mHotelListCopy);
            adapter.notifyDataSetChanged();
            supprimerFiltreBtn.setVisibility(View.GONE);


            return;
        }




    }

    //for choosing filter

    private void enableFiltering(){

         Button annulerBtn;
         Button appliquerBtn;

        final Dialog dialog = new Dialog(this);

        dialog.setCancelable(false);
        dialog.setContentView(R.layout.hotel_filtre);

        EditText minPriceText =  dialog.findViewById(R.id.minPrice);
        EditText maxPriceText =  dialog.findViewById(R.id.maxPrice);
        RatingBar ratingBar   =  dialog.findViewById(R.id.ratingBar);
        ratingBar.setStepSize(1f);

        appliquerBtn=dialog.findViewById(R.id.appliquer);
         annulerBtn=dialog.findViewById(R.id.annuler);




        annulerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

                Log.d(TAG,"vous avez annuler le filtre");
                //hide filter option
                //filter.setVisibility(View.GONE);
            }
        });

        appliquerBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                int minPrice=-1;
                int maxPrice=-1;
                float numStars;

                String minPriceString = minPriceText.getText().toString();
                String maxPriceString = maxPriceText.getText().toString();



                try {
                    minPrice=Integer.parseInt(minPriceString);
                    maxPrice=Integer.parseInt(maxPriceString);

                }catch (NumberFormatException ignored){

                }


                 numStars= ratingBar.getRating();

                Log.d(TAG,"min prive "+minPrice+" max price "+maxPrice+" num stars "+numStars);
                //filtering data
                appliquerFiltre(minPrice,maxPrice,numStars);

                supprimerFiltreBtn.setVisibility(View.VISIBLE);

                dialog.dismiss();






            }
        });


        dialog.show();

    }

    private void appliquerFiltre(int minPrice, int maxPrice, float numStars) {



        if(filtredList.size()>0)
            filtredList.clear();


        Log.d(TAG,"le nombre de HotelListe est "+mHotelList.size());


        for (int i=0;i<mHotelList.size();i++){


            if(minPrice!=-1){
                if(mHotelList.get(i).getPrice()>=minPrice)
                {
                    if(maxPrice!=-1){
                        if(mHotelList.get(i).getPrice()<=maxPrice){
                            if(numStars!=0){

                                if(mHotelList.get(i).getStars()==numStars){
                                    filtredList.add(mHotelList.get(i));
                                }

                            }else
                                filtredList.add(mHotelList.get(i));

                        }
                    }else{
                        if(numStars!=0){

                            if(mHotelList.get(i).getStars()==numStars){
                                filtredList.add(mHotelList.get(i));
                            }

                        }else
                            filtredList.add(mHotelList.get(i));
                    }

                }

            }
            else{
                if(maxPrice!=-1){
                    if(mHotelList.get(i).getPrice()<=maxPrice){
                        if(numStars!=0){

                            if(mHotelList.get(i).getStars()==numStars){
                                filtredList.add(mHotelList.get(i));
                            }

                        }else
                            filtredList.add(mHotelList.get(i));

                    }
                }
                else{
                    if(numStars!=0){

                        if(mHotelList.get(i).getStars()==numStars){
                            filtredList.add(mHotelList.get(i));
                        }

                    }else
                        filtredList.add(mHotelList.get(i));
                }

            }

        }
        mHotelListCopy.addAll(mHotelList);
        //remove all hotels from our liste to add new filter hotels
        mHotelList.clear();

        Log.d(TAG,"le nb de hotels de filtred liste est "+filtredList.size());
        mHotelList.addAll(filtredList);
        //show filtered data
        adapter.notifyDataSetChanged();

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




        //!remove this statement
        new SendRequest(this).getHotels("je cherche la piscine dans Marrakech");

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


            if(liste.size()>0){
                mHotelList.addAll(liste);
                // Log.d(TAG,"taille est " +mHotelList.size()+" "+mHotelList.get(0).toString());
                adapter.notifyDataSetChanged();
                //ajouter mp3 son
                MediaPlayer.create(this,R.raw.sound).start();
                //afficher les btns de filtres
                filter.setVisibility(View.VISIBLE);

            }
            else{
                Toast.makeText(this, "Pas de donnees", Toast.LENGTH_SHORT).show();
            }



        }





    }
}