package network.social.com.androidfinal;


import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.Hashtable;

import network.social.com.androidfinal.database.DBHelper;

public class MissionComplete extends AppCompatActivity {
    EditText ad, soyad, tckno, kargoUcret, alimTarih;
    private RequestQueue requestQueue;
    private String gorevler;
    private String reportUrl = "http://----/kargo/gorevliWS/setMissionCompleteReport/";
    private Hashtable<String, String> parcalar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mission_complete);

        ad = (EditText) findViewById(R.id.editAd);
        soyad = (EditText) findViewById(R.id.editSoyad);
        tckno = (EditText) findViewById(R.id.editTckno);
        kargoUcret = (EditText) findViewById(R.id.editKargoUcret);
        alimTarih = (EditText) findViewById(R.id.editAlimTarihi);






        requestQueue = Volley.newRequestQueue(getApplication());
        addInformationText();



    }

    public void addInformationText(){
        Bundle extras = getIntent().getExtras();
        gorevler = extras.getString("gorev");
        ad.setText(bilgiGetir(gorevler, "customerName"));
        soyad.setText(bilgiGetir(gorevler, "customerLastName"));
        tckno.setText(bilgiGetir(gorevler, "customerTcNo"));
        kargoUcret.setText(bilgiGetir(gorevler, "cargoPay"));
        alimTarih.setText(bilgiGetir(gorevler, "getTimeCoordinate"));
    }

    public void teslimEt(View v){
        SharedPreferences sharedPref= PreferenceManager.getDefaultSharedPreferences(this);
        Double korX=Double.parseDouble(sharedPref.getString("enlemm","hata"));
        Double korY=Double.parseDouble(sharedPref.getString("boylamm", "hata büyük"));

        if(Double.parseDouble(bilgiGetir(gorevler,"coordinateX"))-1<=korX && korX<=Double.parseDouble(bilgiGetir(gorevler,"coordinateX"))+1 &&
                Double.parseDouble(bilgiGetir(gorevler,"coordinateY"))-1<=korY && korY<=Double.parseDouble(bilgiGetir(gorevler,"coordinateY"))+1){
            sendDataWebService();
            DBHelper db=new DBHelper(getApplicationContext());
            db.deleteTask(Integer.parseInt(bilgiGetir(gorevler, "coordinateId")));
            Intent in = new Intent(this,MenuActivity.class);
            startActivity(in);
        }else{
            Toast.makeText(MissionComplete.this, "Hedefteki konumda değilsiniz.", Toast.LENGTH_SHORT).show();
            Intent back2Map=new Intent(this,MapActivity.class);
            startActivity(back2Map);
        }


    }

    public String bilgiGetir(String list, String alinacakBilgi) {
        String text = list;
        parcalar = new Hashtable<String, String>();


        String[] yeni = text.replaceAll("[{}]", "_").replace("'", "").replaceAll("\\s+", "").trim().split("_")[1].split(",");

        for (String s : yeni) {
            parcalar.put(s.split("=")[0], s.split("=")[1]);
        }

        return parcalar.get(alinacakBilgi);

    }


    public void sendDataWebService() {
        reportUrl += bilgiGetir(gorevler,"userId")+"/"+bilgiGetir(gorevler,"coordinateId")+"/"+ad.getText().toString()+"/"+soyad.getText().toString()+"/"+tckno.getText().toString()+"/"+bilgiGetir(gorevler,"cargoPay")+"/"+"Teslim_Edildi" ;
        JsonObjectRequest jsonReq = new JsonObjectRequest(Request.Method.GET, reportUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {


                } catch (Exception e) {
                    Log.e("Web Servis", "bağlantı sağlanamadı" + e.getLocalizedMessage());
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "", Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(jsonReq);
    }





}
