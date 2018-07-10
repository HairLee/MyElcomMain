package com.elcom.hailpt.view.ui.activity;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;

import com.elcom.hailpt.R;
import com.elcom.hailpt.services.CallService;
import com.elcom.hailpt.util.ConstantsApp;
import com.elcom.hailpt.util.Consts;
import com.elcom.hailpt.util.PreferUtils;
import com.elcom.hailpt.util.SharedPrefsHelper;
import com.elcom.hailpt.view.ui.HomeActivity;
import com.elcom.hailpt.view.ui.LoginActivity;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.core.helper.StringifyArrayList;
import com.quickblox.users.QBUsers;
import com.quickblox.users.model.QBUser;

import java.util.Date;

public class SplashActivity extends AppCompatActivity {
    SharedPrefsHelper sharedPrefsHelper;
    private QBUser userForSave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        sharedPrefsHelper = SharedPrefsHelper.getInstance();
        String token = PreferUtils.getToken(this);


        new Handler().postDelayed(() -> {
            if (token.equals("")){
                LoginActivity.start(SplashActivity.this);
                finish();
            } else {
                tryToLoginQuickServer(PreferUtils.getEmail(SplashActivity.this),"1234567890");
                ConstantsApp.BASE64_HEADER = ConstantsApp.BEAR + token;
                HomeActivity.start(SplashActivity.this, false);
                finish();
            }
        }, 1000);

    }

    public void tryToLoginQuickServer(String username, String pv){
        QBUser user = new QBUser(username, pv);
        QBUsers.signIn(user).performAsync(new QBEntityCallback<QBUser>() {
            @Override
            public void onSuccess(QBUser qbUser, Bundle bundle) {
                Log.e("hailpt"," login onSuccess "+qbUser.getEmail());
                startLoginService(qbUser);
            }

            @Override
            public void onError(QBResponseException e) {
                Log.e("hailpt"," login onError "+e.getErrors());
            }
        });
    }


    private void startLoginService(QBUser qbUser) {
        userForSave = qbUser;
        qbUser.setPassword("1234567890");
        StringifyArrayList<String> userTags = new StringifyArrayList<>();
        userTags.add(sharedPrefsHelper.getQbUser().getFullName());
        qbUser.setTags(userTags);

        Intent tempIntent = new Intent(this, CallService.class);
        PendingIntent pendingIntent = createPendingResult(Consts.EXTRA_LOGIN_RESULT_CODE, tempIntent, 0);
        CallService.start(this, qbUser, pendingIntent);
    }

    private void saveUserData(QBUser qbUser) {
        SharedPrefsHelper sharedPrefsHelper = SharedPrefsHelper.getInstance();
        sharedPrefsHelper.save(Consts.PREF_CURREN_ROOM_NAME, qbUser.getTags().get(0));
        sharedPrefsHelper.saveQbUser(qbUser);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Consts.EXTRA_LOGIN_RESULT_CODE) {
            boolean isLoginSuccess = data.getBooleanExtra(Consts.EXTRA_LOGIN_RESULT, false);
            String errorMessage = data.getStringExtra(Consts.EXTRA_LOGIN_ERROR_MESSAGE);
            if (isLoginSuccess) {
                saveUserData(userForSave);
            }
        }
    }


}
