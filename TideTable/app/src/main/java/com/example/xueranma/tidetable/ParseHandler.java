package com.example.xueranma.tidetable;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Created by xueranma on 7/16/17.
 */

public class ParseHandler extends DefaultHandler {
    private TideItems tideItems;
    private TideItem item;

    private boolean isCity = false;



    private boolean isTime = false;
    private boolean isHighLow = false;
    private boolean isPred = false;
    private String dateMonth ="";

    public TideItems getItems(){return tideItems;}

    @Override
    public void startDocument() throws SAXException{
        tideItems = new TideItems();
        item = new TideItem();
    }

    @Override
    public void startElement(String namespaceURI, String localName,
                             String qName, Attributes atts ) throws SAXException{
        if(qName.equals("item")){
            dateMonth = atts.getValue(0);
            return;
        }else if(qName.equals("stationName")){
            isCity = true;
            return;
        }
        else if(qName.equals("data")){
            item = new TideItem();
            item.setTideDate(dateMonth);
            return;
        /*
        }else if(qName.equals("date")){
            isDate = true;
            return;
        }else if(qName.equals("day")){
            isDay = true;
            return;
        */
        }else if(qName.equals("time")){
            isTime = true;
            return;

        }else if(qName.equals("pred")){
            isPred =true;
            return;
        }else if(qName.equals("type")){
            isHighLow = true;
            return;
        }
    }

    @Override
    public void endElement(String namespaceURI, String localName,
                           String qName) throws SAXException{
        if(qName.equals("data")){
            tideItems.add(item);
        }
        return;
    }

    @Override
    public void characters(char ch[], int start, int length){
        String s = new String(ch, start, length);
        if(isCity){
            tideItems.setCity(s);
            isCity = false;
            //isCity=false;
        /*
        }else if(isDate){
            item.setTideDate(s);
            isDate = false;
        }else if (isDay){
            item.setTideDay(s);
            isDay = false;
        */
        }else if(isTime){

            item.setTideTime(s);
            isTime = false;

        }else if(isPred){

            item.setTidePred(s);
            isPred =false;
        }else if(isHighLow){

            item.setTideHighLow(s);
            isHighLow = false;
        }
    }


}
