package com3014.coursework.group6.model;

/**
 * Model for the location based on IP address for nearest cinema
 */
public class GeoIP {
    private String ipAddress;
    private String city;
    private String latitude;
    private String longitude;

    public String getCity() {
        return city;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public GeoIP(String ip, String city, String latitude, String longitude){
        super();
        this.ipAddress = ip;
        this.city = city;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public String toString(){
        return "{\"ipAddress\":\""+this.ipAddress+"\",\"city\":\""+this.city+"\",\"latitude\":\""+this.latitude+"\",\"longitude\":\""+this.longitude+"\"}";
    }
}
