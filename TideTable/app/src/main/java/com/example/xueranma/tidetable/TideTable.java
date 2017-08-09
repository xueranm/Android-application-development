package com.example.xueranma.tidetable;

import android.app.Activity;

import android.content.Intent;

import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.view.View.OnClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class TideTable extends Activity implements OnClickListener,OnItemSelectedListener, ConnectionCallbacks,OnConnectionFailedListener, LocationListener {

    //location related variables
    GoogleApiClient googleApiClient;
    Location myLocation;
    LocationRequest locationRequest;


    //private Dal dal = new Dal(this);
    //Cursor cursor = null;
    String state ="";
    String stationSelection = "9434098";
    String stationName = "Florence";
    String dateSelection = "11";
    String monthSelection = "7";
    double latitude = 44.0f;
    double longitude = -124.1f;

    TextView currentLocationTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tide_table);

        currentLocationTextView = (TextView)findViewById(R.id.currentLocationTextView);

        // Set up station selection spinner
        Spinner stationSpinner = (Spinner)findViewById(R.id.stationSpinner);
        stationSpinner.setOnItemSelectedListener(this);

        Spinner dateSpinner = (Spinner)findViewById(R.id.dateSpinner);
        dateSpinner.setOnItemSelectedListener(this);

        Spinner monthSpinner = (Spinner)findViewById(R.id.monthSpinner);
        monthSpinner.setOnItemSelectedListener(this);

        Button showButton = (Button)findViewById(R.id.showButton);
        showButton.setOnClickListener(this);

        Button geoButton = (Button)findViewById(R.id.geoButton);
        geoButton.setOnClickListener(this);

        // Get the Location API and register the callbacks that it uses
        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        // This object will be used when we request location updates in onConnected
        locationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setNumUpdates(1)
                .setExpirationDuration(60000);

    }

    @Override
    public void onStart() {
        super.onStart();
        googleApiClient.connect();
    }


    @Override
    public void onStop() {
        googleApiClient.disconnect();
        super.onStop();
    }

    // ** Event Handler **

    /*@Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        cursor.moveToPosition(position);
        float pred = cursor.getFloat(cursor.getColumnIndex("Pred"));

        //TideItem item = tideItems.get(position);
        Toast.makeText(this,
                Float.toString(pred)+" ft., "+ftToCm(Float.toString(pred))+" cm",
                Toast.LENGTH_LONG).show();
    }

    private String ftToCm(String ft){
        double num = Double.parseDouble(ft);
        int cen = (int) (num *  30.5);
        String cm = String.valueOf(cen);
        return cm;
    }*/
    @Override
    public void onClick(View v){
        if(v.getId()==R.id.showButton){
            // Start second activity and send it the user's date and station selection
            if ((monthSelection.equals("2")&&(dateSelection.equals("29")||dateSelection.equals("30")||dateSelection.equals("31")))
                    ||((monthSelection.equals("4")||monthSelection.equals("6")||monthSelection.equals("9")||monthSelection.equals("11"))&&dateSelection.equals("31"))){
                Toast.makeText(this, "There is no the date!", Toast.LENGTH_LONG).show();
            }else{
                Intent intent = new Intent(this, SecondActivity.class);
                intent.putExtra("dateSelection", dateSelection);  // send data to 2nd activity
                intent.putExtra("stationSelection", stationSelection);  // send data to 2nd activity
                intent.putExtra("monthSelection",monthSelection);
                intent.putExtra("geoDateSelection","0");
                startActivity(intent);
            }
        }else if(v.getId()==R.id.geoButton){
            if (state.equals("Oregon")){
                //get Current date
                Calendar c = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
                String todayDate = df.format(c.getTime());

                //distance between curren location to Florence
                String toFlorence = calcDistance(44.0021,-124.123);
                //distance between curren location to Charleston
                String toCharleston = calcDistance(43.345,-124.322);
                //distance between curren location to Garibaldi
                String toGaribaldi = calcDistance(45.5545,-123.9189);

                //pass to GeoActivity
                Intent intent = new Intent(this, GeoActivity.class);
                intent.putExtra("toFlorence",toFlorence);
                intent.putExtra("toCharleston",toCharleston);
                intent.putExtra("toGaribaldi",toGaribaldi);
                intent.putExtra("todayDate",todayDate);
                startActivity(intent);

            }else{
                Toast.makeText(this, "No station in this state!", Toast.LENGTH_LONG).show();

            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View v, int position,
                               long id){
        if (parent.getId() == R.id.stationSpinner) {
            switch (position) {
                case 0:
                    stationSelection = "9434098";// Florence

                    break;
                case 1:
                    stationSelection = "9432780";// Charleston

                    break;
                case 2:
                    stationSelection = "9437540";// Garibaldi
                    break;
            }
        }else if(parent.getId()==R.id.monthSpinner){
            switch (position) {
                case 0:
                    monthSelection = "1";
                    break;
                case 1:
                    monthSelection= "2";
                    break;
                case 2:
                    monthSelection = "3";
                    break;
                case 3:
                    monthSelection = "4";
                    break;
                case 4:
                    monthSelection= "5";
                    break;
                case 5:
                    monthSelection = "6";
                    break;
                case 6:
                    monthSelection = "7";
                    break;
                case 7:
                    monthSelection= "8";
                    break;
                case 8:
                    monthSelection = "9";
                    break;
                case 9:
                    monthSelection = "10";
                    break;
                case 10:
                    monthSelection= "11";
                    break;
                case 11:
                    monthSelection = "12";
                    break;
            }

        }else if(parent.getId()== R.id.dateSpinner){
            switch (position){
                case 0:
                    dateSelection = "1";
                    break;
                case 1:
                    dateSelection = "2";
                    break;
                case 2:
                    dateSelection = "3";
                    break;
                case 3:
                    dateSelection = "4";
                    break;
                case 4:
                    dateSelection = "5";
                    break;
                case 5:
                    dateSelection = "6";
                    break;
                case 6:
                    dateSelection = "7";
                    break;
                case 7:
                    dateSelection = "8";
                    break;
                case 8:
                    dateSelection = "9";
                    break;
                case 9:
                    dateSelection = "10";
                    break;
                case 10:
                    dateSelection = "11";
                    break;
                case 11:
                    dateSelection = "12";
                    break;
                case 12:
                    dateSelection = "13";
                    break;
                case 13:
                    dateSelection = "14";
                    break;
                case 14:
                    dateSelection = "15";

                    break;
                case 15:
                    dateSelection = "16";
                    break;
                case 16:
                    dateSelection = "17";
                    break;
                case 17:
                    dateSelection = "18";
                    break;
                case 18:
                    dateSelection = "19";
                    break;
                case 19:
                    dateSelection = "20";
                    break;
                case 20:
                    dateSelection = "21";
                    break;
                case 21:
                    dateSelection = "22";
                    break;
                case 22:
                    dateSelection = "23";
                    break;
                case 23:
                    dateSelection = "24";
                    break;
                case 24:
                    dateSelection = "25";
                    break;
                case 25:
                    dateSelection = "26";
                    break;
                case 26:
                    dateSelection = "27";
                    break;
                case 27:
                    dateSelection = "28";
                    break;
                case 28:
                    dateSelection = "29";
                    break;
                case 29:
                    dateSelection = "30";
                    break;
                case 30:
                    dateSelection = "31";
                    break;
            }

        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        final int MY_PERMISSION_ACCESS_COURSE_LOCATION = 42;  // This is an arbitrary value
        // This check is required by Google Play Services APIs beginning with version 9.0.0 (I think)
        if( ContextCompat.checkSelfPermission( this,
                android.Manifest.permission.ACCESS_FINE_LOCATION )
                != PackageManager.PERMISSION_GRANTED )
        {
            // In API 23 and later users must give permission after the activity runs
            ActivityCompat.requestPermissions( this,
                    new String[] {  android.Manifest.permission.ACCESS_FINE_LOCATION  },
                    MY_PERMISSION_ACCESS_COURSE_LOCATION );
        }
        else
        {
            // This is where we request the locaiton
            LocationServices.FusedLocationApi.requestLocationUpdates(
                    googleApiClient, locationRequest, this);
        }
    }


    @Override
    public void onConnectionSuspended(int i) {
        currentLocationTextView.setText("Connection failed. Code: " + i);
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        currentLocationTextView.setText("Connection failed. Error code: " +
                connectionResult.getErrorCode());
    }


    @Override
    public void onLocationChanged(Location location) {
        // Here it is! This is where we get the current location
        myLocation = location;
        Geocoder geo = new Geocoder(this,
                Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = geo.getFromLocation(
                    myLocation.getLatitude(),
                    myLocation.getLongitude(), 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        state = addresses.get(0).getAdminArea();
        currentLocationTextView.setText("You are at State: "+state);
    }

    public String calcDistance(double lat, double lon) {
        //if(state.equals("Oregon")){

        //}
        if( ContextCompat.checkSelfPermission( this,
                android.Manifest.permission.ACCESS_FINE_LOCATION )
                != PackageManager.PERMISSION_GRANTED ) {
            myLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        }
        if (myLocation != null) {
            Location destinationLocation = new Location("Destination");
            destinationLocation.setLatitude(lat);
            destinationLocation.setLongitude(lon);
            float distance = myLocation.distanceTo(destinationLocation);
            // distance is in meters, will convert to km
            return Float.toString(distance / 1000.0F);
        }
        else {
            return "Can't determine your location";
        }

    }

}
