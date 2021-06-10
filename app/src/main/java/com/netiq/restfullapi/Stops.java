package com.netiq.restfullapi;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Stops {

    private String id;
    private String code;
    private String name;
    private float lat;
    private float lon;
    private int dist;
    private String modes;
    private int routeCount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public float getLon() {
        return lon;
    }

    public void setLon(float lon) {
        this.lon = lon;
    }

    public int getDist() {
        return dist;
    }

    public void setDist(int dist) {
        this.dist = dist;
    }

    public String getModes() {
        return modes;
    }

    public void setModes(String modes) {
        this.modes = modes;
    }

    public int getRouteCount() {
        return routeCount;
    }

    public void setRouteCount(int routeCount) {
        this.routeCount = routeCount;
    }
}
