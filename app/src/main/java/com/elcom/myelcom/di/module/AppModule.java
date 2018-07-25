package com.elcom.myelcom.di.module;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.SharedPreferences;

import com.elcom.myelcom.AApp;
import com.elcom.myelcom.core.AppSchedulerProvider;
import com.elcom.myelcom.core.Constants;
import com.elcom.myelcom.model.db.AppDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @author ihsan on 12/2/17.
 */

@Module(includes = {ViewModelModule.class, NetworkModule.class})
public class AppModule {

    @Provides
    @Singleton
    Context provideContext(AApp application) {
        return application.getApplicationContext();
    }

    @Provides
    @Singleton
    AppSchedulerProvider provideSchedulerProvider() {
        return new AppSchedulerProvider();
    }

    @Provides
    @Singleton
    SharedPreferences provideSplashViewModel(Context context) {
        return context.getSharedPreferences(Constants.PREFERENCES, Context.MODE_PRIVATE);
    }

    @Provides
    @Singleton
    AppDatabase provideAppDatabase(Context context) {
        return Room.databaseBuilder(context,
                AppDatabase.class, Constants.DB)
                .allowMainThreadQueries()
                .build();
    }
}
