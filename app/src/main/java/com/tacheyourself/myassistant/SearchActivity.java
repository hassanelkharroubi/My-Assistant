package com.tacheyourself.myassistant;

import android.app.Dialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.Toast;

import com.tacheyourself.myassistant.adapter.HotelAdapter;
import com.tacheyourself.myassistant.adapter.RestaurantAdapter;
import com.tacheyourself.myassistant.adapter.SiteAdapter;
import com.tacheyourself.myassistant.adapter.TransportAdapter;
import com.tacheyourself.myassistant.internet.SendRequest;
import com.tacheyourself.myassistant.model.Hotel;
import com.tacheyourself.myassistant.model.Restaurant;
import com.tacheyourself.myassistant.model.Site;
import com.tacheyourself.myassistant.model.Transport;
import com.tacheyourself.myassistant.utils.Util;

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
    private ProgressBar mProgressBar;


    //for restaurant

    private RestaurantAdapter mRestaurantAdapter;
    private List<Restaurant> mRestaurantList;
    private List<Restaurant> mFilteredRestaurants;
    private List<Restaurant> mRestaurantListCopy;

    //pour  site

    private SiteAdapter mSiteAdapter;
    private List<Site> mSitelist;
    private List<Site> mFilteredSitelist;
    private List<Site> mSiteListCopy;

    //pour transport
    private TransportAdapter mTransportAdapter;
    private List<Transport> mTransportList;
    private List<Transport> mFilteredTransportList;
    private List<Transport> mTransportListCopy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Intent intent=getIntent();


        if(intent.hasExtra("name")){
            name=intent.getStringExtra("name");
            Log.d(TAG,name);
        }



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
        imageView.setOnClickListener(this);
        mListView=findViewById(R.id.liste);
        mProgressBar=findViewById(R.id.progressBar);
        mProgressBar.setVisibility(View.GONE);



        Log.d(TAG,name);
        if(name.equals("etablissement")) {


            mHotelList = new ArrayList<>();
            mHotelListCopy = new ArrayList<>();
            filtredList = new ArrayList<>();
            adapter = new HotelAdapter(this, R.layout.hotel_item, mHotelList);
            mListView.setAdapter(adapter);
        }

        if(name.equals("site")) {

            mSitelist = new ArrayList<>();
            mSiteListCopy = new ArrayList<>();
            mFilteredSitelist = new ArrayList<>();
            mSiteAdapter = new SiteAdapter(this, R.layout.site_item, mSitelist);
            mListView.setAdapter(mSiteAdapter);
        }


        if(name.equals("transport")) {

            mTransportList = new ArrayList<>();
            mTransportListCopy = new ArrayList<>();
            mFilteredTransportList = new ArrayList<>();
            mTransportAdapter = new TransportAdapter(this, R.layout.transport_item, mTransportList);
            mListView.setAdapter(mTransportAdapter);
            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    showTransportMap(position);
                }
            });
        }


        if(name.equals("restaurant")) {

            mRestaurantList = new ArrayList<>();
            mRestaurantListCopy = new ArrayList<>();
            mFilteredRestaurants = new ArrayList<>();
            mRestaurantAdapter = new RestaurantAdapter(this, R.layout.restaurant_item, mRestaurantList);
            mListView.setAdapter(mRestaurantAdapter);
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

            if(name.equals(Util.ACTION[1])){
                enableFilteringOnRestaurant();
            }
            if(name.equals(Util.ACTION[0]))
                enableFiltering();


            return;
        } if(v.getId()==supprimerFiltreBtn.getId()){

            //show dialogbox for filtering data
            if(name.equals(Util.ACTION[0])){
                mHotelList.clear();
                mHotelList.addAll(mHotelListCopy);
                adapter.notifyDataSetChanged();

            }

            if(name.equals(Util.ACTION[1])){
                mRestaurantList.clear();
                mRestaurantList.addAll(mRestaurantListCopy);
                mRestaurantAdapter.notifyDataSetChanged();

            }

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
        RatingBar ratingBar   =  dialog.findViewById(R.id.ratingBar_restaurant);
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



    private void enableFilteringOnRestaurant(){

        Button annulerBtn;
        Button appliquerBtn;

        final Dialog dialog = new Dialog(this);

        dialog.setCancelable(false);
        dialog.setContentView(R.layout.hotel_filtre_restaurant);


        RatingBar ratingBar   =  dialog.findViewById(R.id.ratingBar_restaurant);
        ratingBar.setStepSize(0.5f);

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



                numStars= ratingBar.getRating();

                Log.d(TAG,"min prive "+minPrice+" max price "+maxPrice+" num stars "+numStars);
                //filtering data
                appliquerFiltreRestaurant(numStars);

                supprimerFiltreBtn.setVisibility(View.VISIBLE);

                dialog.dismiss();

            }
        });


        dialog.show();

    }

    private void appliquerFiltreRestaurant(float numStars) {





        if(numStars==0){
            Toast.makeText(this, "pas de filtre choisi", Toast.LENGTH_SHORT).show();
            return;

        }
        mFilteredRestaurants.clear();
        for (int i=0;i<mRestaurantList.size();i++){

            if(mRestaurantList.get(i).getRevaluation()==numStars){
                mFilteredRestaurants.add(mRestaurantList.get(i));

            }
        }

        mRestaurantListCopy.addAll(mRestaurantList);
        mRestaurantList.clear();
        mRestaurantList.addAll(mFilteredRestaurants);
        mRestaurantAdapter.notifyDataSetChanged();

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
        Log.d(TAG,"try to het data");


        new SendRequest(this).getInfo("Hôtel Radisson Blu Marrakech",name);
        Log.d(TAG,"number of stars and evaluation ");

        if(requestCode==code && resultCode==RESULT_OK){
            mProgressBar.setVisibility(View.VISIBLE);

            List<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            //get data from our tables
            String spokenText = results.get(0);
            //! il faut supprimer les commenatires
            new SendRequest(this).getInfo(spokenText,name);
            Log.d(TAG,spokenText);

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void sendHotel(List<Hotel> liste, String type) {


        mProgressBar.setVisibility(View.GONE);
            if(liste.size()>0){

                Log.d(TAG,"number of stars and evaluation "+liste.get(0).getEvaluation()+" stars "+liste.get(0).getStars());
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

    @Override
    public void sendSite(List<Site> liste, String type) {

        mProgressBar.setVisibility(View.GONE);
        if(liste.size()>0){
            mSitelist.addAll(liste);
            // Log.d(TAG,"taille est " +mHotelList.size()+" "+mHotelList.get(0).toString());
            mSiteAdapter.notifyDataSetChanged();

        }
        else{
            Toast.makeText(this, "Pas de donnees", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void sendRestaurant(List<Restaurant> liste, String type) {
        mProgressBar.setVisibility(View.GONE);
        if(liste.size()>0){
            mRestaurantList.addAll(liste);
            // Log.d(TAG,"taille est " +mHotelList.size()+" "+mHotelList.get(0).toString());
            mRestaurantAdapter.notifyDataSetChanged();
            //ajouter mp3 son
            MediaPlayer.create(this,R.raw.sound).start();
            //afficher les btns de filtres
            filter.setVisibility(View.VISIBLE);

        }
        else{
            Toast.makeText(this, "Pas de donnees", Toast.LENGTH_SHORT).show();
        }



    }

    @Override
    public void sendTransport(List<Transport> liste, String type) {
        mProgressBar.setVisibility(View.GONE);

        if(liste.size()>0){
            mTransportList.addAll(liste);
            // Log.d(TAG,"taille est " +mHotelList.size()+" "+mHotelList.get(0).toString());
            mTransportAdapter.notifyDataSetChanged();

        }
        else{
            Toast.makeText(this, "Pas de donnees", Toast.LENGTH_SHORT).show();
        }

    }
    private void showTransportMap(int position){
        // Create a Uri from an intent string. Use the result to create an Intent.

        Log.d(TAG,mTransportList.get(position).getType()+"  type     hhhhhhh");
        Uri gmmIntentUri = Uri.parse("geo:31.6347485,-8.0778934?q="+mTransportList.get(position).getType());

// Create an Intent from gmmIntentUri. Set the action to ACTION_VIEW
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);

// Make the Intent explicit by setting the Google Maps package
        mapIntent.setPackage("com.google.android.apps.maps");
        if (mapIntent.resolveActivity(getPackageManager()) != null) {
            // Attempt to start an activity that can handle the Intent
            startActivity(mapIntent);

        }
        else{
            Toast.makeText(this, "Veuillez installer google maps", Toast.LENGTH_SHORT).show();
        }


    }
}