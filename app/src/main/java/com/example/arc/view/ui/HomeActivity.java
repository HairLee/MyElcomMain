package com.example.arc.view.ui;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.arc.R;
import com.example.arc.util.Consts;
import com.example.arc.view.ui.fragment.AllFriendQuickBloxFragment;
import com.example.arc.view.ui.fragment.contact.ContactFragment;
import com.example.arc.view.ui.fragment.HomeFragment;
import com.example.arc.view.ui.fragment.NewsFragment;


public class HomeActivity extends AppCompatActivity {

    private boolean isRunForCall;
    private BottomNavigationView navigation;
    private Fragment fragment;
    private FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        setupView();
    }

    public void setupView(){
        fragmentManager = getSupportFragmentManager();
        navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            switch (id){
                case R.id.action_item1:
                    fragment = new HomeFragment();
                    break;
                case R.id.action_item2:
                    fragment = new NewsFragment();
                    break;
                case R.id.action_item3:
                    fragment = new ContactFragment();
                    break;
            }
            final FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.frame_layout, fragment).commit();
            return true;
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent.getExtras() != null) {
            if(fragment instanceof AllFriendQuickBloxFragment){
                isRunForCall = intent.getExtras().getBoolean(Consts.EXTRA_IS_STARTED_FOR_CALL);
                if (isRunForCall) {
                    CallActivity.start(HomeActivity.this, true);
                }
            }
        }
    }

    public static void start(Context context, boolean isRunForCall) {
        Intent intent = new Intent(context, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        intent.putExtra(Consts.EXTRA_IS_STARTED_FOR_CALL, isRunForCall);
        context.startActivity(intent);
    }
}
