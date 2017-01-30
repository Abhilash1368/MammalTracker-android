package com.example.abhilashreddy.firebase;

/**
 * Created by ABHILASH REDDY on 9/14/2016.
 */

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;

//@IgnoreExtraProperties //annotation on your class

public class objects implements Serializable{
    public double lati;
    //@Exclude
    public double longi;
    public String url;
    public String valuetext;
    public String dateitem;
    public String selectedstring;

    public objects() {
    }


    public objects(double lat, double longi, String url,String valuetext,String dateitem,String selectedstring){
        this.lati=lat;
        this.longi=longi;
        this.url=url;
        this.valuetext=valuetext;
        this.valuetext=dateitem;
        this.selectedstring=selectedstring;
    }

    public double getLat() {
        return lati;
    }

    public void setLat(double lat) {
        this.lati = lat;
    }

    public double getLongi() {
        return longi;
    }

    public void setLon(double Long) {
        this.longi = Long;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDateitem() {
        return dateitem;
    }

    public void setDateitem(String dateitem) {
        this.dateitem = dateitem;
    }

    public String getValuetext() {
        return valuetext;
    }

    public void setValuetext(String valuetext) {
        this.valuetext = valuetext;
    }

    public String getSelectedstring() {
        return selectedstring;
    }

    public void setSelectedstring(String selectedstring) {
        this.selectedstring = selectedstring;
    }
}
