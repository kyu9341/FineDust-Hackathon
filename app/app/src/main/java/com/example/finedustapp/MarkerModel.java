package com.example.finedustapp;

public class MarkerModel {
    double lat;
    double lng;
    String stationName;
    String contents;

    public MarkerModel(double lat, double lng, String stationName, String contents) {
        this.lat = lat;
        this.lng = lng;
        this.stationName = stationName;
        this.contents = contents;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }
}
