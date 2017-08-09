package com.example.xueranma.tidetable;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

/**
 * Created by xueranma on 7/16/17.
 */

public class SecondActivity extends Activity {
    Cursor cursor = null;
    private Dal dal = new Dal(this);
    SimpleCursorAdapter adapter = null;
    String stationSelection = "9434098";
    String dateSelection = "11";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity);

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
        String sSelection = intent.getExtras().getString("stationSelection");
        String dSelection = intent.getExtras().getString("dateSelection");
        String mSelection = intent.getExtras().getString("monthSelection");
        String geoDateSelection = intent.getExtras().getString("geoDateSelection");
        if(geoDateSelection.equals("0")){
            // Get a tide predict the selected station and selected date
            getPrediction(sSelection,mSelection,dSelection);
        }else{
            getPredictionToday(sSelection,geoDateSelection);

        }


        //adapter.changeCursor(cursor);

    }

    /************* ----------- Private Methods ------------- ***************/
    private void getPredictionToday(String station, String date) {
        // If there isn't a forecast in the db for this location (and date?), then get one from the web service
        cursor = dal.getPredictionToday(station, date);
        if(cursor.getCount() == 0) {
            new RestTask().execute("1",station,date);
        }
        else {
            displayPrediction();
        }
    }

    private void getPrediction(String station, String month, String date) {
        // If there isn't a forecast in the db for this location (and date?), then get one from the web service
        cursor = dal.getPredictionFromDb(station, month, date);
        if(cursor.getCount() == 0) {
            new RestTask().execute("0",station,month, date);
        }
        else {
            displayPrediction();
        }
    }

    private void displayPrediction() {
        //predefined one layout not our layout
        int resource = R.layout.listview_items;
        String[] from = {"Date", "Pred","Time", "HighLow"};
        int [] to = {R.id.dateTextView,R.id.predTextView,R.id.timeTextView, R.id.highLowTextView};

        //create and set the adapter
        adapter =
                new SimpleCursorAdapter(this,resource, cursor,from, to,0);

        // Pass the data adapter to the List View
        ListView itemsListView = (ListView)findViewById(R.id.tideListView);
        itemsListView.setAdapter(adapter);
        //adapter.changeCursor(cursor);
    }


    /************* ----------- Nested Class ------------- ***************/

    public class RestTask extends AsyncTask<String, Void, TideItems> {

        private String flag;
        private String stationSelection;
        private String dateSelection;
        private String monthSelection;

        // TODO:Replace deprecated HTTPEntity with a more up-to-date class
        // HTTPEntity Code adapted from: http://www.techrepublic.com/blog/software-engineer/calling-restful-services-from-your-android-app/

        @Override
        protected TideItems doInBackground(String... params) {



            String baseUrl = "https://opendap.co-ops.nos.noaa.gov/axis/webservices/highlowtidepred/response.jsp?stationId=";
            flag = params[0];
            if(flag.equals("0")){
                stationSelection = params[1];
                dateSelection = params[2];
                monthSelection = params[3];
            }else if(flag.equals("1")){
                stationSelection = params[1];
                dateSelection = params[2];
            }


            String query = stationSelection + "&beginDate=20170101&endDate=20171231&datum=MLLW&unit=0&timeZone=0&format=xml&Submit=Submit";
            TideItems items = null;
            try {
                URL url = new URL(baseUrl  + query);
                HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                connection.setRequestProperty("User-Agent", "");
                connection.setRequestMethod("GET");
                connection.setDoInput(true);
                connection.connect();
                InputStream in = connection.getInputStream();

                if (in != null) {
                    items = dal.parseXmlStream(in);
                    items.setStationId(stationSelection);
                    //items.setDate(dateSelection);
                    //items.setMonth(monthSelection);
                }

            } catch (Exception e) {
                Log.e("tide", "doInBackground error: " + e.getLocalizedMessage());
            }

            return items;
        }



        @Override
        protected void onPostExecute(TideItems items) {
            if (items != null && items.size() != 0) {
                dal.putPredictionIntoDb(items);
                if(flag.equals("0")){
                    cursor = dal.getPredictionFromDb(stationSelection, monthSelection,dateSelection);
                }else if(flag.equals("1")){
                    cursor = dal.getPredictionToday(stationSelection, dateSelection);
                }
                displayPrediction();
            }
        }
    }


}
