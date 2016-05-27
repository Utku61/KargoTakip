package network.social.com.androidfinal;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class LocationReceiver extends BroadcastReceiver {
    private RequestQueue requestQueue;
    private String reportUrl="http://---/kargo/gorevliWS/setHourlyLocationReport/";


    public LocationReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle location=intent.getExtras();
        String enlem=location.getString("enlem");
        String boylam=location.getString("boylam");
        Toast.makeText(context,enlem+" "+boylam, Toast.LENGTH_SHORT).show();
        reportUrl+=1+"/"+enlem+"/"+boylam;
        requestQueue= Volley.newRequestQueue(context.getApplicationContext());
        JsonObjectRequest jsonReq= new JsonObjectRequest(Request.Method.GET,reportUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{


                }catch(Exception e){
                    Log.e("Web Servis", "bağlantı sağlanamadı" + e.getLocalizedMessage());
                    e.printStackTrace();
                }
            }
        },new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonReq);
        Toast.makeText(context, "Istek yollandı", Toast.LENGTH_SHORT).show();
    }




}
