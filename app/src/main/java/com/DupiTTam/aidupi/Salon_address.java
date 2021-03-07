package com.DupiTTam.aidupi;

public class Salon_address {
    String name;
    String address;
    double lat;
    double lon;
    String locate_divide;
    double distance;

    public Salon_address(){}
    public Salon_address(String name, String address, double lat, double lon, String locate_divide, double distance) {
        this.name=name;
        this.address = address;
        this.lat = lat;
        this.lon = lon;
        this.locate_divide=locate_divide;
        this.distance=distance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getLocate_divide() {
        return locate_divide;
    }

    public void setLocate_divide(String locate_divide) {
        this.locate_divide = locate_divide;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
}
