package com.example.xueranma.tidetable;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.io.InputStream;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Created by xueranma on 7/16/17.
 */
//Data access layer
public class Dal {
    private Context context = null;

    public Dal(Context context){this.context = context;}

    /*
    // This is a temporary method for loading fixed data into the db
    public void loadTestData(String stationId)
    {
        // Initialize database
        TideSQLiteHelper helper = new TideSQLiteHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        // load the database with test data if it isn't already loaded
        if (db.rawQuery("SELECT * FROM Tide WHERE StationId = " + stationId, null).getCount() == 0)
        {
            loadDbFromXML("9432780");	//Charleston
            loadDbFromXML("9437540"); // Garibaldi
            loadDbFromXML("9434098"); // Florence


        }
        db.close();
    }*/
    /*
    // Parse the XML files and put the data in the db
    public void loadDbFromXML(String stationId) {
        // Get the data from the XML file
        String fileName = "tide_prediction" + stationId +".xml";
        TideItems items = parseXmlFile(fileName);
        items.setStationId(stationId);	// This field isn't in the xml file, so we add it here

        // Initialize database
        TideSQLiteHelper helper = new TideSQLiteHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();

        // Put tide prediction in the database
        ContentValues cv = new ContentValues();

        for(TideItem item : items)
        {
            cv.put("Date", item.getTideDate());
            cv.put("StationId", items.getStationId());	// stored in items, not item
            cv.put("City", items.getCity());			// stored in items, not item
            cv.put("Day",item.getTideDay());
            cv.put("Time", item.getTideTime());
            cv.put("Pred", item.getTidePred());
            cv.put("HighLow", item.getTideHighLow());
            db.insert("Tide", null, cv);
        }
        db.close();
    }*/
    protected void putPredictionIntoDb(TideItems items) {

        // Initialize database
        TideSQLiteHelper helper = new TideSQLiteHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();

        // Put tide prediction in the database
        ContentValues cv = new ContentValues();

        for(TideItem item : items)
        {
            cv.put("Date", item.getTideDate());
            cv.put("StationId", items.getStationId());	// stored in items, not item
            //cv.put("Month", items.getMonth());			// stored in items, not item
            cv.put("City",items.getCity());
            cv.put("Time", item.getTideTime());
            cv.put("Pred", item.getTidePred());
            cv.put("HighLow", item.getTideHighLow());
            db.insert("Tide", null, cv);
        }
        db.close();
    }

    //for geoActivity
    public Cursor getPredictionToday(String location1, String startDate)
    {

        // Initialize the database
        TideSQLiteHelper helper = new TideSQLiteHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();

        // Get a tide prediction for one location
        String query = "SELECT * FROM Tide WHERE StationId = ?  AND Date=? ORDER BY Date ASC";
        String[] variables = new String[]{location1,startDate};    // rawQuery must not include a trailing ';'
        return db.rawQuery(query, variables);
    }

    public Cursor getPredictionFromDb(String location, String startMonth, String startDate)
    {
        // Ensure there is data in the database for this location
        //loadTestData(location);
        String date1String;
        String date2String;
        String startDate2;
        String startMonth2;
        int date = Integer.parseInt(startDate);
        int month = Integer.parseInt(startMonth);
        if(date==28 && month==2){
            date1String = "02/28/2017";
            date2String = "03/01/2017";
        }else if(date==30&&(month==4||month==6||month==9||month==11)){
            if(month!=11&&month!=9){
                date1String ="0"+startMonth+"/"+startDate +"/2017";
                month=month+1;
                startMonth2 = Integer.toString(month);
                date2String = "0"+startMonth2+"/01"+"/2017";
            }else if(month==11){
                date1String = startMonth+"/"+startDate+"/2017";
                date2String = "12/01/2017";
            }else{
                date1String = "09/30/2017";
                date2String = "10/01/2017";
            }

        }else if(date==31&&(month==1||month==3||month==5||month==7||month==8||month==10)){
            if (month!=10){
                date1String = "0"+startMonth+"/"+startDate+"/2017";
                month=month+1;
                startMonth2 = Integer.toString(month);
                date2String = "0"+startMonth2+"/01/2017";
            }else{
                date1String ="10/31/2017";
                date2String = "11/01/2017";
            }

        }else if (date==31&&month==12) {
            date1String = "12/31/2017";
            date2String = "";
        }else {
            startDate2 = Integer.toString(date+1);
            if(date<10){
                startDate = "0"+startDate;
            }
            if((date+1)<10){
                startDate2 = "0"+startDate2;
            }
            if(month<10){
                startMonth = "0"+startMonth;
            }
            date1String = startMonth +"/"+ startDate+"/2017";
            date2String = startMonth + "/"+startDate2+"/2017";
        }

        // Initialize the database
        TideSQLiteHelper helper = new TideSQLiteHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();

        // Get a tide prediction for one location
        String query = "SELECT * FROM Tide WHERE StationId = ? AND (Date=? OR Date =?) ORDER BY Date ASC";
        String[] variables = new String[]{location,date1String,date2String};    // rawQuery must not include a trailing ';'
        return db.rawQuery(query, variables);
    }

    /*public TideItems parseXmlFile(String fileName) {
        try {
            // get the XML reader
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            XMLReader xmlreader = parser.getXMLReader();

            // set content handler
            ParseHandler handler = new ParseHandler();
            xmlreader.setContentHandler(handler);

            // read the file from internal storage
            InputStream in = context.getAssets().open(fileName);

            // parse the data
            InputSource is = new InputSource(in);
            xmlreader.parse(is);

            // set the feed in the activity
            TideItems items = handler.getItems();
            return items;
        }
        catch (Exception e) {
            Log.e("News reader", e.toString());
            return null;
        }
    }*/
    protected TideItems parseXmlStream(InputStream in) {
        TideItems items = null;
        if (in != null) {
            try {
                // get the XML reader
                SAXParserFactory factory = SAXParserFactory.newInstance();
                SAXParser parser = factory.newSAXParser();
                XMLReader xmlreader = parser.getXMLReader();

                // set content handler
                ParseHandler handler = new ParseHandler();
                xmlreader.setContentHandler(handler);

                // parse the data
                InputSource is = new InputSource(in);
                xmlreader.parse(is);
                items = handler.getItems();
            } catch (Exception e) {
                Log.e("Tide", "parseXMLStream error: " + e.toString());
            }
        }
        return items;
    }
}
