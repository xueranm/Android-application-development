package com.example.xueranma.tidetable;

import java.util.ArrayList;

/**
 * Created by xueranma on 7/16/17.
 */

public class TideItems extends ArrayList<TideItem> {
    // Extending ArrayList to facilitate possible future features

    // Default Serial ID
    private static final long serialVersionUID = 1L;

    //Info that applies to the whole tide prediction
    private String stationId = "";
    private String city = "";


    public String getStationId(){return stationId;}

    public void setStationId(String stationId){this.stationId = stationId;}

    public String getCity(){return city;}

    public void setCity(String city){this.city = city;}

    //public String getDate(){return date;}

    //public void setDate(String date){this.date = date;}

   // public String getDateMonth(){return dateMonth;}

    //public void setDateMonth(String dateMonth){this.dateMonth =dateMonth;}
}
