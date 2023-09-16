package com.example.eld.network;

import com.example.eld.network.dto.login.request.LoginRequestModel;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

//: https://eld-k7sg.onrender.com/api/login/checkLogin

public interface ApiService {

    @POST("login/checkLogin")
    Call<JsonElement> loginUser(@Body LoginRequestModel requestModel);



    @FormUrlEncoded
    @POST("adduserstatus.php")
    Call<JsonObject> addstatus(@Header("Authorization")String token,
                                @Field("id") String id,
                                @Field("date") String date,
                                @Field("xvalues") String xvalues,
                                @Field("yvalues") String yvalues

    );

    @Headers("Content-Type: application/json")
    @POST("api/reset/sendOtp")
    Call<JsonObject> sendOtp(@Body JsonObject requestBody);

    @POST("getuserstatus.php")
    Call<JsonObject> getstatus(
            @Header("Authorization")String token);

    @FormUrlEncoded
    @GET("driver/listUser")
    Call<JsonObject> getUserDetails(@Header("Authorization")String token
    );

    //https://eld-k7sg.onrender.com/api/driver/driverProfile?id=1'
    @GET("api/driver/driverProfile")
    Call<ResponseBody> getDriverProfile(@Query("id") String id);
}
