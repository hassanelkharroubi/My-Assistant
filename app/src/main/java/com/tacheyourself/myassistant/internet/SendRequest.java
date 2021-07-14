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
        String url = "https://readbeforespeak.000webhostapp.com/fst.php?q="+query;
        Log.d(TAG,query);

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

                        String name=jsonObject.getString("nom de l'hôtel");
                        String adresse=jsonObject.getString("adresse");
                        String pointsForts=jsonObject.getString("pointsForts");
                        String lieuxApp=jsonObject.getString("lieux à proximité");
                        String aeroports=jsonObject.getString("aéroports les plus proches");

                        String eval=jsonObject.getString("évaluation");
                        eval=eval.replace(',','.');
                       

                        hotelList.add(new Hotel(name,adresse,pointsForts,lieuxApp,aeroports,9));
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
