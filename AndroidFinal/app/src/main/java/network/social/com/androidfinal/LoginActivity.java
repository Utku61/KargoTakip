package network.social.com.androidfinal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import network.social.com.androidfinal.database.DBHelper;
import network.social.com.androidfinal.model.Gorev;


public class LoginActivity extends AppCompatActivity {
    private EditText userEmail,userPassword;
    Button login;
    private  RequestQueue requestQueue;
    private String loginUrl="http://192.168.43.203:8081/kargo/gorevliWS/login/";
    private String getTaskUrl="http://192.168.43.203:8081/kargo/gorevliWS/getCoordinats/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userEmail=(EditText)findViewById(R.id.email);
        userPassword=(EditText)findViewById(R.id.password);
        login=(Button)findViewById(R.id.login_button);
        requestQueue= Volley.newRequestQueue(getApplication());


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (emailControl() && passwordControl()) {

                    requestWebServiceToLogin();


                } else {
                    Toast.makeText(getApplicationContext(), "Lütfen bilgileriniz tam giriniz", Toast.LENGTH_SHORT).show();
                }


            }
        });


    }


    public boolean emailControl(){
         String email = userEmail.getText().toString();
        boolean onay=false;
        if (android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            userEmail.setError(null);
            onay=true;
        } else {
            userEmail.setError("Eposta kurallara uygun degildir");
        }
        return onay;
    }


    public boolean passwordControl(){
        String password =userPassword.getText().toString();
        Pattern rgx= Pattern.compile("^[a-zA-Z0-9*_+-]{8,12}$");
        Matcher matcher=rgx.matcher(password);
        boolean onay=false;
        if(!matcher.matches()){
            userPassword.setError("parala 6 ie 12 karekter arası olmalıdır.");
        }else{
            userPassword.setError(null);
            onay=true;

        }
        return onay;
    }

    public void requestWebServiceToLogin(){

        loginUrl+= userEmail.getText().toString()+"/"+userPassword.getText().toString();
        JsonObjectRequest jsonReq= new JsonObjectRequest(Request.Method.GET,loginUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    int userId=response.getInt("gorevliId");
                    String userMail=response.getString("kullaniciAd");
                    String userPassword=response.getString("kullaniciSifre");
                    getMenuActivity(userId, userMail, userPassword);

                }catch(Exception e){
                    Log.e("Web Servis","bağlantı sağlanamadı"+e.getLocalizedMessage());
                    e.printStackTrace();
                }
            }
        },new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Web servis ile bağlantı kurulamadı.",Toast.LENGTH_LONG).show();
                loginUrl="http://192.168.43.203:8081/kargo/gorevliWS/login/";
            }
        });
        requestQueue.add(jsonReq);
    }

    public void getTaskWithWebService(final int userId) {

        getTaskUrl+=userId;

    JsonArrayRequest jsonReq = new JsonArrayRequest(getTaskUrl, new Response.Listener<JSONArray>() {


        @Override
        public void onResponse(JSONArray response) {
            try {
                if(response.length() > 0 ){
                    DBHelper taskInformation = new DBHelper(getApplicationContext());
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonResponse = response.getJSONObject(i);

                        Gorev task = new Gorev();
                        task.setCustomerName(jsonResponse.getString("aliciAd"));
                        task.setCustomerLastName(jsonResponse.getString("aliciSoyad"));
                        task.setCustomerTcNo(jsonResponse.getString("aliciTcNo"));
                        task.setCargoPay(Float.parseFloat(jsonResponse.getString("kargoBedeli")));
                        task.setCoordinateId(jsonResponse.getInt("koordinatId"));
                        task.setCoordinateX(jsonResponse.getString("koordinatX"));
                        task.setCoordinateY(jsonResponse.getString("koordinatY"));
                        task.setGetTimeCoordinate(parseDate());
                        task.setReachingTimeCoordinate(parseDate());
                        task.setDelivery("edilmedi");
                        task.setUserId(userId);

                        taskInformation.insertTask(task);
                    }
                    }
            } catch (Exception e) {
                Log.e("json", "veri cekilemedi" + e.getLocalizedMessage());
                e.printStackTrace();
            }
        }
    }, new Response.ErrorListener() {

        @Override
        public void onErrorResponse(VolleyError error) {
            Toast.makeText(getApplicationContext(), "Veri çekilemedi", Toast.LENGTH_LONG).show();
        }
    }) {
        @Override
        public String getBodyContentType() {
            return "application/json;charset=utf-8";
        }
    };
    requestQueue.add(jsonReq);


}

    public void getMenuActivity(int userId,String userMail,String userPassword){
        if(userMail==null && userPassword==null){
            Toast.makeText(getApplication(),"Hata",Toast.LENGTH_LONG).show();
        }else{
            finish();
            Toast.makeText(getApplication(),"Hoşgeldiniz",Toast.LENGTH_LONG).show();
            getTaskWithWebService(userId);
            Intent menu=new Intent(this,MenuActivity.class);
            startActivity(menu);
        }
    }


    public static String parseDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(Calendar.getInstance().getTime());
    }




}
