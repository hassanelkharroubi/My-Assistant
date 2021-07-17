package com.tacheyourself.myassistant.internet;



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

                        String name=jsonObject.getString("nom");
                        String adresse=jsonObject.getString("adresse");
                        String pointsForts=jsonObject.getString("points_forts");
                        String lieuxApp=jsonObject.getString("lieux_proximite");
                        //String aeroports=jsonObject.getString("aéroports les plus proches");
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

                       

                        hotelList.add(new Hotel(name,adresse,pointsForts,lieuxApp,null,evaluation,prix,stars));
                        //! send to to searchActivity

                    }

                    if(mContext instanceof DataListener)
                    {
                        mDataListener= (DataListener) mContext;
                        mDataListener.sendData(hotelList,"hotel");
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






}
