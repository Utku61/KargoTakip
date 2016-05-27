package network.social.com.androidfinal;



import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


import java.util.List;

import network.social.com.androidfinal.database.DBHelper;
import network.social.com.androidfinal.model.TaskLocation;


public class MapActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private GoogleMap googleMap;
    private GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    List<TaskLocation> taskLocations;
    TaskLocation userLocation=new TaskLocation();
    private int SURE=15*1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);


        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }


        DBHelper locationInformList = new DBHelper(getApplicationContext());
        taskLocations = locationInformList.getCustomerLocationList();


    }

    @Override
    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            addLocationList();
            startLocationReicever();
            showLocationes();
            addLocationPrafered();
        }
    }

    public void startLocationReicever(){

        AlarmManager locationAlarm=(AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent locReicever=new Intent(this,LocationReceiver.class);
        locReicever.putExtra("enlem",String.valueOf(mLastLocation.getLatitude()));
        locReicever.putExtra("boylam", String.valueOf(mLastLocation.getLongitude()));
        PendingIntent pendingIntent=PendingIntent.getBroadcast(this, 1, locReicever, 0);
        locationAlarm.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+SURE, SURE, pendingIntent);

    }

    public void addLocationPrafered(){
        SharedPreferences sharedPref= PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor=sharedPref.edit();
        editor.putString("enlemm",String.valueOf(mLastLocation.getLatitude()));
        editor.putString("boylamm",String.valueOf(mLastLocation.getLongitude()));
        editor.commit();
    }

     public void addLocationList(){
         userLocation.setCoordinateX(String.valueOf(mLastLocation.getLatitude()));
         userLocation.setCoordinateY(String.valueOf(mLastLocation.getLongitude()));
         userLocation.setDelivery("kullanici");
         taskLocations.add(userLocation);
     }
    public void showLocationes(){
        try {
            if (googleMap == null) {
                googleMap = ((MapFragment) getFragmentManager().
                        findFragmentById(R.id.map)).getMap();
            }
            googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);



            if (taskLocations.size() != 0) {
                for (TaskLocation task : taskLocations) {

                     if(task.getDelivery()=="kullanici"){
                         Marker TP_ONE = googleMap.addMarker(new MarkerOptions().
                                 position(new LatLng(Double.parseDouble(task.getCoordinateX()), Double.parseDouble(task.getCoordinateY()))).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                     }
                    else{
                         Marker TP_TWO = googleMap.addMarker(new MarkerOptions().
                                 position(new LatLng(Double.parseDouble(task.getCoordinateX()), Double.parseDouble(task.getCoordinateY()))).title("gorev"));
                     }


                }
            } else {
                Toast.makeText(getApplicationContext(), "Herhangi bir göreviniz yok", Toast.LENGTH_LONG).show();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}



