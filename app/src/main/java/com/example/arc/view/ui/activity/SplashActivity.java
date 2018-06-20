package com.example.arc.view.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.arc.R;
import com.example.arc.util.ConstantsApp;
import com.example.arc.util.PreferUtils;
import com.example.arc.view.ui.HomeActivity;
import com.example.arc.view.ui.LoginActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        String token = PreferUtils.getToken(this);

        if (token.equals("")){
            LoginActivity.start(this);
            finish();
        } else {
            HomeActivity.start(this, false);
            ConstantsApp.BASE64_HEADER = ConstantsApp.BEAR + token;
            finish();
        }
    }
}
