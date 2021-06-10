package com.netiq.restfullapi;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    ProgressBar progressBar;

    PlaceHolderAPI placeHolderAPI;
    PlaceHolderAPIImpl placeHolderAPIImpl;

    //Finding the View by name
    TextView textView;

    //Disposing on subscribe
    private CompositeDisposable disposable = new CompositeDisposable();

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy: Thread is dispose: " + disposable.isDisposed());
        disposable.clear();
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: Starting the application");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Progress bar
        progressBar = findViewById(R.id.progressbar);


        //UI
        textView = findViewById(R.id.dspAPI);

        //Calling methods
//        getData();
//        clickButtonCount();
        getDataFromObjectApi();

    }

    //Getting and observing data from the Object API
    private void getDataFromObjectApi() {
        Log.d(TAG, "getDataFromObjectApi: Getting data from API");

        OkHttpClient innerClient = new OkHttpClient.Builder()
                .connectTimeout(3, TimeUnit.MINUTES) // connect timeout
                .writeTimeout(3, TimeUnit.MINUTES) // write timeout
                .readTimeout(3, TimeUnit.MINUTES) // read timeout
                .build();

        //Calling retrofit to make online API call
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://proserver.gometro.co.za/")
                .addConverterFactory(GsonConverterFactory.create()) //convert to json file
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) //RxJava call
                .client(innerClient) //calling OKHttpClient
                .build();

        placeHolderAPI = retrofit.create(PlaceHolderAPI.class);


        Observable<BusTime> observableObjectAPI = placeHolderAPI.getBussTimes();


        observableObjectAPI.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable1 -> showLoader())
                .doOnTerminate(this::hideLoader)
                .subscribe(new Observer<BusTime>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe: Object API is Called");
                        disposable.add(d);

                    }

                    @Override
                    public void onNext(BusTime busTime) {
                        Log.d(TAG, "onNext: Bus API is called " + busTime);
                        Log.d(TAG, "onNext: Running on: " + Thread.currentThread());



                        for (Stops busTime1 : busTime.stops) {
                            String content = "";
                            content += "UserId: " + busTime1.getId() + "\n";
                            content += "Code: " + busTime1.getCode() + "\n";
                            content += "Name: " + busTime1.getName() + "\n";
                            content += "Lat: " + busTime1.getLat() + "\n";
                            content += "Lon: " + busTime1.getLon() + "\n";
                            content += "Dist: " + busTime1.getDist() + "\n";
                            content += "Modes: " + busTime1.getModes() + "\n";
                            content += "RouteCount: " + busTime1.getRouteCount() + "\n \n";


                            textView.append(content);


                        }

                        for (longestRoutes longestRoutes : busTime.longestRoutes) {
                            String route = "";

                            route += "ID: " + longestRoutes.getId() + "\n";
                            route += "shortName: " + longestRoutes.getShortName() + "\n";
                            route += "longName: " + longestRoutes.getLongName() + "\n";
                            route += "Mode: " + longestRoutes.getMode() + "\n";
                            route += "AgencyName: " + longestRoutes.getAgencyName() + "\n";
                            route += "RouterID: " + longestRoutes.getRouterId() + "\n";
                            route += "Distance: " + longestRoutes.getDistance() + "\n \n";


                            textView.append(route);


                        }



                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: An error has occurred " + e.getMessage());


                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: Bus object ran Successfully");

                    }
                });


    }

    private void hideLoader() {
        Log.d(TAG, "hideLoader: Called");
        progressBar.setVisibility(View.INVISIBLE);
    }

    private void showLoader() {
        Log.d(TAG, "showLoader: Called");
        progressBar.setVisibility(View.VISIBLE); // To show the ProgressBar
    }

    // Method for counting clicks
//    private void clickButtonCount() {
//
//        RxView.touches(findViewById(R.id.buttonBtn))
//                .map(new Function<MotionEvent, Object>() {
//                    @Override
//                    public Object apply(MotionEvent motionEvent) throws Exception {
//                        return 1;
//                    }
//                })
//                .buffer(4, TimeUnit.SECONDS)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<List<Object>>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//                        Log.d(TAG, "onSubscribe: Button motion Called");
//
//                    }
//
//                    @Override
//                    public void onNext(List<Object> objects) {
//                        Log.d(TAG, "onNext: Button touched " + objects.size() + " times in 4 seconds");
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.e(TAG, "onError: an error has occurred " + e);
//                        ;
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        Log.d(TAG, "onComplete: Successfully completed");
//
//                    }
//                });
//
//
//    }


    //Getting and observing data from the Array API
    private void getData() {

        OkHttpClient innerClient = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS) // connect timeout
                .writeTimeout(30, TimeUnit.SECONDS) // write timeout
                .readTimeout(30, TimeUnit.SECONDS) // read timeout
                .build();

        //Calling retrofit to make online API call
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create()) //convert to json file
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) //RxJava call
                .client(innerClient) //calling OKHttpClient
                .build();

        placeHolderAPI = retrofit.create(PlaceHolderAPI.class);

        Observable<List<ModelClass>> observable = placeHolderAPI.getPosts();


        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<ModelClass>>() { //Creating an observer that subscribing to the observable
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe: Called Successfully " + d);
                        disposable.add(d);
                    }

                    @Override
                    public void onNext(List<ModelClass> modelClasses) {
                        Log.d(TAG, "onNext: Calling thread: " + Thread.currentThread().getName());

                        progressBar.setVisibility(View.VISIBLE); // To show the ProgressBar

                        if (!modelClasses.isEmpty()) {
                            Log.d(TAG, "onNext: Model class is empty: " + modelClasses.isEmpty());
                            Log.d(TAG, "onNext: Calling thread: " + Thread.currentThread().getName());
                        }


                        for (ModelClass post : modelClasses) {
                            String content = "";
                            content += "UserId: " + post.getUserId() + "\n";
                            content += "Name: " + post.getTitle() + "\n";
                            content += "Body: " + post.getBody() + "\n \n";

                            textView.append(content);

                        }

                        progressBar.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: An error has occurred  " + e);
                        e.getMessage();

                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: Completed Successfully");

                    }
                });


    }


}