package com.netiq.restfullapi;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;

public interface PlaceHolderAPI {

//    @GET("/posts")
//    Call<List<ModelClass>> getPost(
//            @Query("userId") int userId,
//            @Query("_sort") String sort);

    @GET("posts/")
    Observable<List<ModelClass>> getPosts(
//            @Query("userId") int userId,
//            @Query("_sort") String sort
    );

    @GET("api/v1/accessibility/-33.9269/18.6048402/800")
    Observable<BusTime> getBussTimes();
}
