package com.example.eld.network;

import com.example.eld.network.dto.attendance.AddAttendanceRecordRequestModel;
import com.example.eld.network.dto.login.request.ChangePasswordRequestModel;
import com.example.eld.network.dto.login.request.ForgotPasswordModel;
import com.example.eld.network.dto.login.request.LoginRequestModel;
import com.example.eld.network.dto.login.request.UpdateCoDriverModel;
import com.example.eld.network.dto.login.request.UpdateShippingAddressModel;
import com.example.eld.network.dto.login.request.UpdateTripNoModel;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
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

    @POST("reset/sendOtp")
    Call<JsonObject> sendOtp(@Body ForgotPasswordModel requestBody);

    @PUT("reset/changePassword")
    Call<JsonObject> changePassword(@Body ChangePasswordRequestModel requestBody);

    @POST("getuserstatus.php")
    Call<JsonObject> getstatus(
            @Header("Authorization")String token);

    @GET("driver/driverProfile")
    Call<ResponseBody> getDriverProfile(@Query("id") int id);

    @POST("driver/addAttendenceRecord")
    Call<ResponseBody> addAttendanceRecord(@Body AddAttendanceRecordRequestModel requestBody);

//    @GET("driver/getAttendenceRecord")
//    Call<ResponseBody> getAttendanceRecord(@Query("userId") String userId, @Query("fromdate") String fromDate,@Query("todate") String toDate);


    @GET("driver/getAttendenceRecord")
    Call<ResponseBody> getAttendenceRecord(
            @Query("userId") String userId,
            @Query("fromdate") String fromdate,
            @Query("todate") String todate
    );
   @PUT ("driver/updateCoDriver")
   Call<ResponseBody> updateCoDriver(@Body UpdateCoDriverModel requestBody);

    @PUT ("driver/updateShippingAddress")
    Call<ResponseBody> updateShippingAddress(@Body UpdateShippingAddressModel requestBody);
    @PUT ("driver/updateTripNo")
    Call<ResponseBody> updateTripNo(@Body UpdateTripNoModel requestBody);

}
