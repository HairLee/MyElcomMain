package com.example.arc.model.api;

import android.arch.lifecycle.LiveData;

import com.example.arc.model.api.request.ChangePwRq;
import com.example.arc.model.api.request.LoginReq;
import com.example.arc.model.api.request.LunchCancelReq;
import com.example.arc.model.api.request.LunchLikeReq;
import com.example.arc.model.api.request.MarkUserReq;
import com.example.arc.model.api.response.Contact;
import com.example.arc.model.api.response.ContactSuggest;
import com.example.arc.model.api.response.Lunch;
import com.example.arc.model.api.response.Notification;
import com.example.arc.model.api.response.User;
import com.example.arc.model.data.Articles;
import com.example.arc.model.data.Sources;
import com.example.arc.model.data.TimeKeep;
import com.google.gson.JsonElement;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
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

    @GET("suggest")
    Observable<RestData<List<ContactSuggest>>> getAllContactSuggest(@Header("Authorization") String s);

    @GET("user-group")
    Observable<RestData<List<Contact>>> getAllContact(@Header("Authorization") String s);

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

    @POST("lunch-dislike")
    Observable<RestData<JsonElement>> dislikeLunch(@Body LunchLikeReq lunchCancelReq,@Header("Authorization") String s);

    @GET("user/{userId}")
    Observable<RestData<User>> getUserProfile(@Path("userId")  int userId, @Header("Authorization") String s);

    @POST("mark-user")
    Observable<RestData<JsonElement>> markFriend(@Body MarkUserReq markUserReq, @Header("Authorization") String s);


     /*Setting*/
     @POST("change-pwd")
     Observable<RestData<JsonElement>> changePassword(@Body ChangePwRq changePwRq, @Header("Authorization") String s);

    @GET("notify")
    Observable<RestData<List<Notification>>> getAllNotification(@Header("Authorization") String s);

    @GET("auth/logout/{token}")
    Observable<RestData<JsonElement>> logout(@Path("token")  String token);

    @Multipart
    @POST("upload-avatar")
    Observable<RestData<JsonElement>> uploadAvatar(@Part MultipartBody.Part  avatarPart, @Header("Authorization") String s);


}
