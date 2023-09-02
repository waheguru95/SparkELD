package com.example.eld.network;

import com.example.eld.network.dto.login.request.LoginRequestModel;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

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
    @POST("getuserstatus.php")
    Call<JsonObject> getstatus(
            @Header("Authorization")String token);

    @FormUrlEncoded
    @GET("driver/listUser")
    Call<JsonObject> getUserDetails(@Header("Authorization")String token
    );
}
