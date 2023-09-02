package com.example.eld.custumclass;

import android.content.Context;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Apiservices {

    public static Gson gson = new GsonBuilder()
            .setLenient()
            .create();
    public static Gson gsonchat = new GsonBuilder()
            .setLenient()
            .create();
    private static Retrofit retrofit = null;
    private static Retrofit retrofitforChat=null;
    private static Retrofit shipmentCost=null;
    private static OkHttpClient.Builder httpShipmentBuilder = null;
    private static OkHttpClient.Builder httpClientBuilder = null;
    private static OkHttpClient.Builder httpClientBuilderforChat = null;

    /*For HTTP base URL*/
    public static Retrofit  apiService(Context context) {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClientBuilder = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS);


        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL_API)
                    .client(httpClientBuilder.build())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }











}
