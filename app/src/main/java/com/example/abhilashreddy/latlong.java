package com.example.abhilashreddy.firebase;

/**
 * Created by ABHILASH REDDY on 11/6/2016.
 */
public class latlong {
    public Double locallat;
    public Double locallongi;
    public int unique;
    public String timestamp;
    public latlong(){}

    public latlong(Double locallat, Double locallongi,int unique,String timestamp) {
        this.locallat = locallat;
        this.locallongi = locallongi;
        this.unique=unique;
        this.timestamp=timestamp;

    }

    public Double getLocallat() {
        return locallat;
    }

    public void setLocallat(Double locallat) {
        this.locallat = locallat;
    }

    public Double getLocallongi() {
        return locallongi;
    }

    public void setLocallongi(Double locallongi) {
        this.locallongi = locallongi;
    }

    public int getUnique() {
        return unique;
    }

    public void setUnique(int unique) {
        this.unique = unique;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

}
