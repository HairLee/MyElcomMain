package com.elcom.hailpt.di.module;

import com.elcom.hailpt.BuildConfig;
import com.elcom.hailpt.model.api.Api;
import com.elcom.hailpt.util.ConstantsApp;
import com.elcom.hailpt.util.Consts;
import com.ihsanbal.logging.Level;
import com.ihsanbal.logging.LoggingInterceptor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.internal.platform.Platform;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author ihsan on 2/28/18.
 */
@Module
public class NetworkModule {

    @Provides
    @Singleton
    LoggingInterceptor provideInterceptor() {
        return new LoggingInterceptor.Builder()
                .loggable(BuildConfig.DEBUG)
                .setLevel(Level.BASIC)
                .log(Platform.INFO)
                .request("Request")
                .response("Response")

//                .addQueryParam("apiKey", "75e1fac65ae745f7a7b23f7184664575")
                .build();
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttp(LoggingInterceptor interceptor) {
        return new OkHttpClient.Builder()
                .addNetworkInterceptor(interceptor)
                .build();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(OkHttpClient client) {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl("http://myelcom.elcom.com.vn:8000/api/")
                .client(client)
                .build();
    }

    @Provides
    @Singleton
    Api provideApi(Retrofit retrofit) {
        return retrofit.create(Api.class);
    }
}
