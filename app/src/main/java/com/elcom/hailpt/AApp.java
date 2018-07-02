package com.elcom.hailpt;

import android.text.TextUtils;
import android.util.Log;

import com.elcom.hailpt.di.DaggerAppComponent;
import com.elcom.hailpt.model.data.QbConfigs;
import com.elcom.hailpt.util.FontsOverride;
import com.elcom.hailpt.util.QBResRequestExecutor;
import com.elcom.hailpt.util.configs.CoreConfigUtils;
import com.quickblox.auth.session.QBSession;
import com.quickblox.auth.session.QBSessionManager;
import com.quickblox.auth.session.QBSessionParameters;
import com.quickblox.auth.session.QBSettings;
import com.quickblox.core.ServiceZone;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;

/**
 * @author ihsan on 11/29/17.
 */

public class AApp extends DaggerApplication {

    public static final String TAG = AApp.class.getSimpleName();
    private QBResRequestExecutor qbResRequestExecutor;
    private static AApp instance;
    private static final String QB_CONFIG_DEFAULT_FILE_NAME = "qb_config.json";
    protected QbConfigs qbConfigs;


    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().create(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        setupFont();
        initApplication();
        initQBSessionManager();
        initQbConfigs();
        initCredentials();
    }

    private void setupFont(){
        Log.e("hailpt", " Go to setupFont");
        FontsOverride.setDefaultFont(this, "DEFAULT", "fonts/RobotoLight.ttf");
        FontsOverride.setDefaultFont(this, "MONOSPACE", "fonts/RobotoLight.ttf");
        FontsOverride.setDefaultFont(this, "SERIF", "fonts/RobotoLight.ttf");
        FontsOverride.setDefaultFont(this, "SANS_SERIF", "fonts/RobotoLight.ttf");
    }

    private void initApplication(){
        instance = this;
    }

    public synchronized QBResRequestExecutor getQbResRequestExecutor() {
        return qbResRequestExecutor == null
                ? qbResRequestExecutor = new QBResRequestExecutor()
                : qbResRequestExecutor;
    }

    private void initQbConfigs() {
        Log.e(TAG, "QB CONFIG FILE NAME: " + getQbConfigFileName());
        qbConfigs = CoreConfigUtils.getCoreConfigsOrNull(getQbConfigFileName());
    }

    public static synchronized AApp getInstance() {
        return instance;
    }

    public void initCredentials(){
        if (qbConfigs != null) {
            QBSettings.getInstance().init(getApplicationContext(), qbConfigs.getAppId(), qbConfigs.getAuthKey(), qbConfigs.getAuthSecret());
            QBSettings.getInstance().setAccountKey(qbConfigs.getAccountKey());

            if (!TextUtils.isEmpty(qbConfigs.getApiDomain()) && !TextUtils.isEmpty(qbConfigs.getChatDomain())) {
                QBSettings.getInstance().setEndpoints(qbConfigs.getApiDomain(), qbConfigs.getChatDomain(), ServiceZone.PRODUCTION);
                QBSettings.getInstance().setZone(ServiceZone.PRODUCTION);

            }
        }
    }

    public QbConfigs getQbConfigs(){
        return qbConfigs;
    }

    protected String getQbConfigFileName(){
        return QB_CONFIG_DEFAULT_FILE_NAME;
    }

    private void initQBSessionManager() {
        QBSessionManager.getInstance().addListener(new QBSessionManager.QBSessionListener() {
            @Override
            public void onSessionCreated(QBSession qbSession) {
                Log.d(TAG, "Session Created");
            }

            @Override
            public void onSessionUpdated(QBSessionParameters qbSessionParameters) {
                Log.d(TAG, "Session Updated");
            }

            @Override
            public void onSessionDeleted() {
                Log.d(TAG, "Session Deleted");
            }

            @Override
            public void onSessionRestored(QBSession qbSession) {
                Log.d(TAG, "Session Restored");
            }

            @Override
            public void onSessionExpired() {
                Log.d(TAG, "Session Expired");
            }

            @Override
            public void onProviderSessionExpired(String provider) {
                Log.d(TAG, "Session Expired for provider:" + provider);
            }
        });
    }

}
