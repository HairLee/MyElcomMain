package com.example.arc.util;

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


}
