package com.elcom.myelcom.util;

import android.content.Context;
import android.content.SharedPreferences;


/**
 * convenient class to work with SharedPreference
 */

/**
 * Created by Anonymous on 18/10/2017.
 */



public class PreferUtils {

    private  static final String PREFER_NAME = "quizup";
    private  static final String PRE_USER_ID = "PRE_USER_ID";
    private  static final String PRE_OPPONENT_AVATAR = "PRE_OPPONENT_AVATAR";
    private  static final String PRE_USER_AVATAR = "PRE_USER_AVATAR";
    private  static final String PRE_USER_EMAIL = "PRE_USER_EMAIL";
    private  static final String PRE_USER_PW = "PRE_USER_PW";
    private  static final String PRE_USER_NAME = "PRE_USER_NAME";

    private  static final String PRE_OPPONENT_EMAIL = "PRE_OPPONENT_EMAIL";
    private  static final String PREFER_GCM_TOKEN = "gcm_token";
    private  static final String USER_ID = "user_id";
    private  static final String CHANLLENGE_TIME_TO_INTIVE = "CHANLLENGE_TIME_TO_INTIVE";
    private  static final String KEY_ENCRYPTION = "kenc";

    public static void setToken(Context context,  String token) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREFER_NAME, Context.MODE_PRIVATE).edit();
        editor.putString(PREFER_GCM_TOKEN, token);
        editor.commit();
    }

    public static String getToken(Context context){
        SharedPreferences preferences = context.getSharedPreferences(PREFER_NAME, Context.MODE_PRIVATE);
        return preferences.getString(PREFER_GCM_TOKEN, "");
    }


    public static void setPassword(Context context,  String token) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREFER_NAME, Context.MODE_PRIVATE).edit();
        editor.putString(PRE_USER_PW, token);
        editor.commit();
    }

    public static String getPassword(Context context){
        SharedPreferences preferences = context.getSharedPreferences(PREFER_NAME, Context.MODE_PRIVATE);
        return preferences.getString(PRE_USER_PW, "");
    }





    public static void setUserId(Context context,  int token) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PRE_USER_ID, Context.MODE_PRIVATE).edit();
        editor.putInt(PREFER_GCM_TOKEN, token);
        editor.commit();
    }

    public static int getUserId(Context context){
        SharedPreferences preferences = context.getSharedPreferences(PRE_USER_ID, Context.MODE_PRIVATE);
        return preferences.getInt(PREFER_GCM_TOKEN, 0);
    }

    public static void setAvatar(Context context,  String token) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PRE_USER_AVATAR, Context.MODE_PRIVATE).edit();
        editor.putString(PREFER_GCM_TOKEN, token);
        editor.commit();
    }

    public static String getAvatar(Context context){
        SharedPreferences preferences = context.getSharedPreferences(PRE_USER_AVATAR, Context.MODE_PRIVATE);
        return preferences.getString(PREFER_GCM_TOKEN, "");
    }


    public static void setEmail(Context context,  String token) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PRE_USER_EMAIL, Context.MODE_PRIVATE).edit();
        editor.putString(PREFER_GCM_TOKEN, token);
        editor.commit();
    }

    public static String getEmail(Context context){
        SharedPreferences preferences = context.getSharedPreferences(PRE_USER_EMAIL, Context.MODE_PRIVATE);
        return preferences.getString(PREFER_GCM_TOKEN, "");
    }

    public static void setName(Context context,  String token) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PRE_USER_EMAIL, Context.MODE_PRIVATE).edit();
        editor.putString(PRE_USER_NAME, token);
        editor.commit();
    }

    public static String getName(Context context){
        SharedPreferences preferences = context.getSharedPreferences(PRE_USER_EMAIL, Context.MODE_PRIVATE);
        return preferences.getString(PRE_USER_NAME, "");
    }


    public static void setEmailOpponent(Context context,  String token) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PRE_OPPONENT_EMAIL, Context.MODE_PRIVATE).edit();
        editor.putString(PREFER_GCM_TOKEN, token);
        editor.commit();
    }

    public static String getEmailOpponent(Context context){
        SharedPreferences preferences = context.getSharedPreferences(PRE_OPPONENT_EMAIL, Context.MODE_PRIVATE);
        return preferences.getString(PREFER_GCM_TOKEN, "");
    }

    public static void setAvatarOpponent(Context context,  String token) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PRE_OPPONENT_AVATAR, Context.MODE_PRIVATE).edit();
        editor.putString(PREFER_GCM_TOKEN, token);
        editor.commit();
    }

    public static String getAvatarOpponent(Context context){
        SharedPreferences preferences = context.getSharedPreferences(PRE_OPPONENT_AVATAR, Context.MODE_PRIVATE);
        return preferences.getString(PREFER_GCM_TOKEN, "");
    }


}
