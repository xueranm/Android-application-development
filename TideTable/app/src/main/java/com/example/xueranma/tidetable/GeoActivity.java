package com.example.xueranma.tidetable;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by xueranma on 7/20/17.
 */

public class GeoActivity extends Activity implements OnClickListener {

    TextView distance;
    TextView distance1;
    TextView distance2;
    Button fButton;
    Button cButton;
    Button gButton;

    String todayDate = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.geo_activity);

        distance = (TextView)findViewById(R.id.distanceLable);
        distance1 = (TextView)findViewById(R.id.distance1Lable);
        distance2 = (TextView)findViewById(R.id.distance2Lable);

        fButton = (Button)findViewById(R.id.florenceButton);
        cButton = (Button)findViewById(R.id.charlestonButton);
        gButton = (Button)findViewById(R.id.garibaldiButton);
        fButton.setOnClickListener(this);
        cButton.setOnClickListener(this);
        gButton.setOnClickListener(this);
        // Initialize the database
        //dal.loadTestData("9434098");
        // Get Prediction for the default station
        //cursor = dal.getPredictionByLocation(stationSelection,dateSelection);




    }

    @Override
    protected void onResume() {
        super.onResume();

        // Get the game state sent from the FirstActivity
        Intent intent = getIntent();
        String toFlorence = intent.getExtras().getString("toFlorence");
        String toCharleston = intent.getExtras().getString("toCharleston");
        String toGaribaldi = intent.getExtras().getString("toGaribaldi");
        todayDate = intent.getExtras().getString("todayDate");

        distance.setText("Distance to Florence station: "+toFlorence+" km");
        distance1.setText("Distance to Charleston station: "+toCharleston+" km");
        distance2.setText("Distance to Garibaldi station: "+toGaribaldi+" km");



        //adapter.changeCursor(cursor);

    }

    @Override
    public void onClick(View v){
        if(v.getId()==R.id.florenceButton){
            // Start second activity and send it the user's date and station selection
            Intent intent = new Intent(this, SecondActivity.class);
            intent.putExtra("stationSelection", "9434098");  // send data to 2nd activity
            intent.putExtra("geoDateSelection",todayDate);
            startActivity(intent);
        }else if(v.getId()==R.id.charlestonButton){
            // Start second activity and send it the user's date and station selection
            Intent intent = new Intent(this, SecondActivity.class);
            intent.putExtra("stationSelection", "9432780");  // send data to 2nd activity
            intent.putExtra("geoDateSelection",todayDate);
            startActivity(intent);
        }else if(v.getId()==R.id.garibaldiButton){
            // Start second activity and send it the user's date and station selection
            Intent intent = new Intent(this, SecondActivity.class);
            intent.putExtra("stationSelection", "9437540");  // send data to 2nd activity
            intent.putExtra("geoDateSelection",todayDate);
            startActivity(intent);
        }
    }


}
