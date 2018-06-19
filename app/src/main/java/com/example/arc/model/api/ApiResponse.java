/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.arc.model.api;

import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;

import retrofit2.Response;

/**
 * Common class used by API responses.
 *
 * @param <D>
 */
public class ApiResponse<D> {
    public final int code;
    @Nullable
    public final D body;
    @Nullable
    public final String errorMessage;
    @Nullable
    public final D errorBody;

    public ApiResponse(Throwable error) {
        code = 500;
        body = null;
        errorMessage = error.getMessage();
        errorBody = parseErrorBodyIfCan(errorMessage);
    }

    public ApiResponse(Response<D> response) {
        code = response.code();
        if (response.isSuccessful()) {
            body = response.body();
            errorMessage = null;
            errorBody = null;
        } else {
            String message = null;
            if (response.errorBody() != null) {
                try {
                    message = response.errorBody().string();
                } catch (IOException ignored) {
                }
            }
            if (message == null || message.trim().length() == 0) {
                message = response.message();
            }
            errorMessage = message;
            body = null;
            errorBody = parseErrorBodyIfCan(errorMessage);
        }
    }

    public boolean isSuccessful() {
        return code >= 200 && code < 300;
    }

    private D parseErrorBodyIfCan(String json) {
        try {
            Type type = new TypeToken<D>() {
            }.getType();
            D data = new Gson().fromJson(json, type);
            if (data instanceof String) {
                return null;
            } else {
                return data;
            }
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            return null;
        } catch (AssertionError ae) {
            ae.printStackTrace();
            return null;
        }
    }
}
