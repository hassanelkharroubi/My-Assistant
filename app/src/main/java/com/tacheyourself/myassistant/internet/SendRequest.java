package com.tacheyourself.myassistant.internet;

/**
 * created by Youssef Talambouti on
 */

import android.content.Context;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tacheyourself.myassistant.DataListener;
import com.tacheyourself.myassistant.model.Hotel;
import com.tacheyourself.myassistant.model.Restaurant;
import com.tacheyourself.myassistant.model.Site;
import com.tacheyourself.myassistant.model.Transport;
import com.tacheyourself.myassistant.utils.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SendRequest {


    public static final String TAG="SendRequest";

private Context mContext;

private DataListener mDataListener;



public SendRequest(Context context){
mContext = context;

    }


    public void getHotels(String query) {
        // url to post our data
        String url = Util.URL+"q="+query+"&action="+Util.ACTION[0];
        Log.d(TAG,url);

        RequestQueue queue = Volley.newRequestQueue(mContext);

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d(TAG,response);
                List<Hotel> hotelList=new ArrayList<>();

                try {

                    JSONArray jsonArray=  new JSONArray(response);

                    Log.d(TAG,"taille de jsonArray is "+jsonArray.length());

                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject=jsonArray.getJSONObject(i);

                        //remove all line break character to avoid much spaces in our text view
                       // name = name.replace("\n", "").replace("\r", "");

                        String name=jsonObject.getString("nom").replace("\n", " ").replace("\r", " ");
                        String adresse=jsonObject.getString("adresse").replace("\n", "").replace("\r", "");
                        String pointsForts=jsonObject.getString("points_forts").replace("\n", " ").replace("\r", " ");
                        String lieuxApp=jsonObject.getString("lieux_proximite").replace("\n", " ").replace("\r", " ");
                        int prix=jsonObject.getInt("prix");



                        //by default give 2
                        int stars;
                        float evaluation;

                        try{


                            String etoile=jsonObject.getString("etoile");
                            String eval=jsonObject.getString("evaluation");


                            try{
                                stars=Integer.parseInt(etoile);
                                evaluation=Float.parseFloat(eval);

                            }catch(NumberFormatException e){

                                stars=2;
                                evaluation=2.5f;

                            }

                        }catch (JSONException e){

                            //by default give 2
                            stars=2;
                            evaluation=2.5f;

                        }

                        String etoile=jsonObject.getString("etoile");
                        String eval=jsonObject.getString("evaluation");
                        //by default give 2
                        Log.d(TAG,etoile+" eval "+eval);

                        hotelList.add(new Hotel(name,adresse,pointsForts,lieuxApp,evaluation,prix,stars));
                        //! send to to searchActivity

                    }

                    if(mContext instanceof DataListener)
                    {
                        mDataListener= (DataListener) mContext;
                        mDataListener.sendHotel(hotelList,"hotel");
                    }

                } catch (JSONException e) {

                    Log.e(TAG,"error de conversion de json array "+e.getMessage());

                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) ;

        queue.add(request);
    }

    public  void getSites(String query) {
        // url to post our data
        String url = Util.URL+"q="+query+"&action="+Util.ACTION[2];
        Log.d(TAG,query);

        RequestQueue queue = Volley.newRequestQueue(mContext);

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d(TAG,response);
                List<Site> siteList=new ArrayList<>();


                try {

                    JSONArray jsonArray=  new JSONArray(response);

                    Log.d(TAG,"taille de jsonArray is "+jsonArray.length());

                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject=jsonArray.getJSONObject(i);

                        String name=jsonObject.getString("nom");
                        String adresse=jsonObject.getString("adresse");
                        String horaire=jsonObject.getString("horaire");
                        String site=jsonObject.getString("sites");
                        String prix=jsonObject.getString("prix");

                        String contact=jsonObject.getString("contact");


                        siteList.add(new Site(name,adresse,horaire,site,prix,contact));
                        //! send to to searchActivity

                    }

                    if(mContext instanceof DataListener)
                    {
                        mDataListener= (DataListener) mContext;
                        mDataListener.sendSite(siteList,"site");
                    }



                } catch (JSONException e) {

                    Log.e(TAG,"error de conversion de json array "+e.getMessage());

                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) ;

        queue.add(request);
    }



    public void getRestaurants(String query) {
        // url to post our data
        String url = Util.URL+"q="+query+"&action="+Util.ACTION[1];
        Log.d(TAG,query);

        RequestQueue queue = Volley.newRequestQueue(mContext);

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d(TAG,response);
                List<Restaurant> restaurantList=new ArrayList<>();


                try {

                    JSONArray jsonArray=  new JSONArray(response);

                    Log.d(TAG,"taille de jsonArray is "+jsonArray.length());

                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject=jsonArray.getJSONObject(i);

                        String name=jsonObject.getString("nom");
                        String adresse=jsonObject.getString("adresse");
                        String horaire=jsonObject.getString("horaire");
                        String repas=jsonObject.getString("repas");
                        String fonctionalite=jsonObject.getString("fonctionnalites");
                        String evaluation=jsonObject.getString("evaluation");

                        //if there no evaluation for this restaurant

                        float evaluationf=-1;


                        try {
                            evaluationf=Float.parseFloat(evaluation);

                        }catch (NumberFormatException ignored){

                        }




                        restaurantList.add(new Restaurant(name,adresse,horaire,repas,fonctionalite,evaluationf ));
                        //! send to to searchActivity

                    }

                    if(mContext instanceof DataListener)
                    {
                        mDataListener= (DataListener) mContext;
                        mDataListener.sendRestaurant(restaurantList,"restaurant");
                    }



                } catch (JSONException e) {

                    Log.e(TAG,"error de conversion de json array "+e.getMessage());

                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) ;

        queue.add(request);
    }

    public void getTransport(String query) {
        // url to post our data
        String url  = Util.URL+"q="+query+"&action="+Util.ACTION[3];
        Log.d(TAG,query);

        RequestQueue queue = Volley.newRequestQueue(mContext);

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d(TAG,response);
                List<Transport> transportList=new ArrayList<>();


                try {

                    JSONArray jsonArray=  new JSONArray(response);

                    Log.d(TAG,"taille de jsonArray is "+jsonArray.length());

                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject=jsonArray.getJSONObject(i);

                        String type=jsonObject.getString("type");
                        Log.d(TAG,type);
                        String tprix=jsonObject.getString("prix");
                        String station;

                        try{
                             station=jsonObject.getString("station");

                        }catch (JSONException e){
                            station="";

                        }


                        transportList.add(new Transport(type,tprix,station));
                        //! send to to searchActivity

                    }

                    if(mContext instanceof DataListener)
                    {
                        mDataListener= (DataListener) mContext;
                        mDataListener.sendTransport(transportList,"transport");
                    }



                } catch (JSONException e) {

                    Log.e(TAG,"error de conversion de json array "+e.getMessage());

                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) ;

        queue.add(request);
    }


    public void getInfo(String query,String type){

    Log.d(TAG,"inside getInfo() method ,type ="+type+" ,query = "+query);

    if(Util.ACTION[0].equals(type)){
        getHotels(query);
        return;
    }
        if(Util.ACTION[1].equals(type)){
            Log.d(TAG,"call getRestauarnt() ");
            getRestaurants(query);
            return;
        }    if(Util.ACTION[2].equals(type)){
            getSites(query);
            return;
        }
        if(Util.ACTION[3].equals(type)){
            Log.d(TAG,"call getTransport() ");
           getTransport(query);
            return;
        }


    }





}
