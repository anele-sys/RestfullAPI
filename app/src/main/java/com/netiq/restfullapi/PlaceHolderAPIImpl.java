package com.netiq.restfullapi;

import android.util.Log;

import java.util.List;

import io.reactivex.Observable;

public class PlaceHolderAPIImpl implements PlaceHolderAPI {

    @Override
    public Observable<List<ModelClass>> getPosts() {
        return null;
    }

    @Override
    public Observable<BusTime> getBussTimes() {
        return null;
    }


}
