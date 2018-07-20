package com.elcom.hailpt.model.api;

import android.arch.lifecycle.LiveData;

import com.elcom.hailpt.model.api.request.ChangeMobileReq;
import com.elcom.hailpt.model.api.request.ChangePwRq;
import com.elcom.hailpt.model.api.request.ForgetPwReq;
import com.elcom.hailpt.model.api.request.LoginReq;
import com.elcom.hailpt.model.api.request.LunchCancelReq;
import com.elcom.hailpt.model.api.request.LunchFeedBackReq;
import com.elcom.hailpt.model.api.request.LunchLikeReq;
import com.elcom.hailpt.model.api.request.MarkUserReq;
import com.elcom.hailpt.model.api.request.ReasonLate;
import com.elcom.hailpt.model.api.response.Contact;
import com.elcom.hailpt.model.api.response.ContactSuggest;
import com.elcom.hailpt.model.api.response.Lunch;
import com.elcom.hailpt.model.api.response.Notification;
import com.elcom.hailpt.model.api.response.User;
import com.elcom.hailpt.model.data.Articles;
import com.elcom.hailpt.model.data.Sources;
import com.elcom.hailpt.model.data.Statistic;
import com.elcom.hailpt.model.data.TimeKeep;
import com.google.gson.JsonElement;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * @author ihsan on 11/29/17.
 */

public interface Api {
    @GET("sources")
    Observable<Sources> sources();

    @GET("top-headlines")
    Observable<Articles> topHeadlines(@Query("sources") String sources);

    @POST("auth/login")
    Observable<RestData<User>> login(@Body LoginReq loginReq);

    @GET("check-in-history?")
    Observable<RestData<List<TimeKeep>>> getTimeKeeping(@Query("from_date") String from_date, @Query("to_date") String to_date, @Header("Authorization") String s);

    @GET("check-in-history/statistic?")
    Observable<RestData<Statistic>> getMonthInformation(@Query("y") String year, @Query("m") String month, @Header("Authorization") String s);


    @POST("reason-late")
    Observable<RestData<JsonElement>> reasonLate(@Body ReasonLate reasonLate, @Header("Authorization") String s);

    @GET("suggest")
    Observable<RestData<List<ContactSuggest>>> getAllContactSuggest(@Header("Authorization") String s);

    @POST("auth/forgot")
    Observable<RestData<JsonElement>> forgetPassword(@Body ForgetPwReq forgetPwReq);


    @GET("user-group")
    Observable<RestData<List<Contact>>> getAllContact(@Header("Authorization") String s);

    @GET("user")
    Observable<RestData<List<User>>> getOnlineContact(@Header("Authorization") String s);

    @GET("mark-user")
    Observable<RestData<List<User>>> getFavouriteContact(@Header("Authorization") String s);

    @POST("lunch-booking")
    Observable<RestData<JsonElement>> registerLunch(@Body LunchCancelReq lunchCancelReq,@Header("Authorization") String s);

    @POST("lunch-cancel")
    Observable<RestData<JsonElement>> cancelLunch(@Body LunchCancelReq lunchCancelReq,@Header("Authorization") String s);

    @GET("lunch-menu?")
    Observable<RestData<List<Lunch>>> getLunchMenu(@Query("from_date") String from_date, @Query("to_date") String to_date, @Header("Authorization") String s);

    @POST("lunch-like")
    Observable<RestData<JsonElement>> likeLunch(@Body LunchLikeReq lunchCancelReq, @Header("Authorization") String s);

    @POST("lunch-feedback")
    Observable<RestData<JsonElement>> sendLunchFeedBack(@Body LunchFeedBackReq lunchFeedBackReq , @Header("Authorization") String s);

    @POST("lunch-dislike")
    Observable<RestData<JsonElement>> dislikeLunch(@Body LunchLikeReq lunchCancelReq,@Header("Authorization") String s);

    @GET("user/{userId}")
    Observable<RestData<User>> getUserProfile(@Path("userId")  int userId, @Header("Authorization") String s);

    @POST("mark-user")
    Observable<RestData<JsonElement>> markFriend(@Body MarkUserReq markUserReq, @Header("Authorization") String s);

    @PUT("user")
    Observable<RestData<JsonElement>> changeMobile(@Body ChangeMobileReq changeMobileReq, @Header("Authorization") String s);

     /*Setting*/
     @POST("change-pwd")
     Observable<RestData<JsonElement>> changePassword(@Body ChangePwRq changePwRq, @Header("Authorization") String s);

    @GET("notify")
    Observable<RestData<List<Notification>>> getAllNotification(@Header("Authorization") String s);

    @GET("auth/logout/{token}")
    Observable<RestData<JsonElement>> logout(@Path("token")  String token);

    @Multipart
    @POST("upload-avatar")
    Observable<RestData<User>> uploadAvatar(@Part MultipartBody.Part  avatarPart, @Header("Authorization") String s);


}
