package com.netiq.restfullapi;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

public class BusTime {

    List<Stops> stops;
    ArrayList<longestRoutes> longestRoutes;

    public BusTime(List<Stops> stops, ArrayList<com.netiq.restfullapi.longestRoutes> longestRoutes) {
        this.stops = stops;
        this.longestRoutes = longestRoutes;
    }

    public static BusTime parseJSON(String response){
        Gson gson = new GsonBuilder().create();
        BusTime busTime = gson.fromJson(response, BusTime.class);
        return busTime;
    }
}
