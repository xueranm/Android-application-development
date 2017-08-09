package com.example.xueranma.tidetable;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.ParseException;
/**
 * Created by xueranma on 7/16/17.
 */

@SuppressLint("SimpleDateFormat")
public class TideItem {
    // the string is the combination of date and time
    private String tideDate = null;
    private String tideTime = null;
    private String tidePred = null;
    private String tideHighLow = null;



    public void setTideDate(String pubDate){this.tideDate = pubDate;}

    public String getTideDate(){return tideDate;}

    public void setTidePred(String pred){this.tidePred = pred;}

    public String getTidePred(){return this.tidePred;}

    public void setTideHighLow(String highLow){this.tideHighLow =highLow;}

    public String getTideHighLow(){return tideHighLow;}

    public String getTideTime(){return tideTime;}

    public void setTideTime(String time){this.tideTime=time;}




}
