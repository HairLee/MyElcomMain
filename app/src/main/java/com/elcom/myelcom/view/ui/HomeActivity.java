package com.elcom.myelcom.view.ui;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.elcom.myelcom.R;
import com.elcom.myelcom.model.api.response.User;
import com.elcom.myelcom.util.Consts;
import com.elcom.myelcom.util.SharedPrefsHelper;
import com.elcom.myelcom.util.WebRtcSessionManager;
import com.elcom.myelcom.view.ui.fragment.HomeFragment;
import com.elcom.myelcom.view.ui.fragment.contact.ContactFragment;
import com.elcom.myelcom.view.ui.fragment.news.NewsFragment;


public class HomeActivity extends AppCompatActivity {

    private boolean isRunForCall;
    private BottomNavigationView navigation;
    private Fragment fragment;
    private FragmentManager fragmentManager;
    private HomeFragment homeFragment = new HomeFragment();
    private NewsFragment newsFragment = new NewsFragment();
    private ContactFragment contactFragment = new ContactFragment();
    private WebRtcSessionManager webRtcSessionManager;
    private SharedPrefsHelper sharedPrefsHelper;
    private int fragmentPos = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        sharedPrefsHelper = SharedPrefsHelper.getInstance();
        initFields();

        if (isRunForCall && webRtcSessionManager.getCurrentSession() != null) {
            CallActivity.start(HomeActivity.this, true);
        }

        setupView();
    }

    private void initFields() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            isRunForCall = extras.getBoolean(Consts.EXTRA_IS_STARTED_FOR_CALL);
        }

        webRtcSessionManager = WebRtcSessionManager.getInstance(getApplicationContext());

        Log.e("hailpt"," HomeActivity initFields ");
    }

    public void setupView(){
        fragmentManager = getSupportFragmentManager();
        navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            switch (id){
                case R.id.action_item1:
                    fragment = homeFragment;
                    fragmentPos = 1;
                    break;
                case R.id.action_item2:
                    fragmentPos = 2;
                    fragment = newsFragment;
                    break;
                case R.id.action_item3:
                    fragmentPos = 3;
                    fragment = contactFragment;
                    break;
            }
            final FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.frame_layout, fragment, fragment.getClass().getName()).commit();
            return true;
        });

        final FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame_layout, homeFragment).commit();


        User user =  sharedPrefsHelper.getUser();
//       Log.e("hailpt"," sharedPrefsHelper~~> "+ user.getAvatar() + sharedPrefsHelper.getQbUser().getEmail());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        Log.e("hailpt"," HomeActivity onNewIntent");

        if (intent.getExtras() != null) {
//            if(fragment instanceof AllFriendQuickBloxFragment){
            isRunForCall = intent.getExtras().getBoolean(Consts.EXTRA_IS_STARTED_FOR_CALL);
            if (isRunForCall) {
                CallActivity.start(HomeActivity.this, true);
            }
//            }
        }
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 0){
            finish();
        } else {
            getSupportFragmentManager().popBackStackImmediate();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(fragmentPos == 1) {
            homeFragment.updateAvatar();
        }
    }

    public static void start(Context context, boolean isRunForCall) {
        Intent intent = new Intent(context, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        intent.putExtra(Consts.EXTRA_IS_STARTED_FOR_CALL, isRunForCall);
        context.startActivity(intent);
    }
}
