package com.elcom.hailpt.util.configs;

import android.content.Context;


import com.elcom.hailpt.AApp;
import com.elcom.hailpt.util.AssetsUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class ConfigParser {

    private Context context;

    public ConfigParser() {
        context = AApp.getInstance().getApplicationContext();
    }

    public String getConfigsAsJsonString(String fileName) throws IOException {
        return AssetsUtils.getJsonAsString(fileName, context);
    }

    public JSONObject getConfigsAsJson(String fileName) throws IOException, JSONException {
        return new JSONObject(getConfigsAsJsonString(fileName));
    }

    public String getConfigByName(JSONObject jsonObject, String fieldName) throws JSONException {
        return jsonObject.getString(fieldName);
    }
}
