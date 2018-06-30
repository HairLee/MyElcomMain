package com.example.arc.view.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.example.arc.R;
import com.example.arc.model.db.QbUsersDbManager;
import com.example.arc.services.CallService;
import com.example.arc.util.CollectionsUtils;
import com.example.arc.util.Consts;
import com.example.arc.util.PermissionsChecker;
import com.example.arc.util.PushNotificationSender;
import com.example.arc.util.SharedPrefsHelper;
import com.example.arc.util.Toaster;
import com.example.arc.util.UsersUtils;
import com.example.arc.util.WebRtcSessionManager;
import com.example.arc.view.adapter.OpponentsAdapter;
import com.example.arc.view.ui.CallActivity;
import com.example.arc.view.ui.LoginActivity;
import com.example.arc.view.ui.PermissionsActivity;
import com.quickblox.chat.QBChatService;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.core.request.QBPagedRequestBuilder;
import com.quickblox.messages.services.SubscribeService;

import com.quickblox.users.QBUsers;
import com.quickblox.users.model.QBUser;
import com.quickblox.videochat.webrtc.QBRTCClient;
import com.quickblox.videochat.webrtc.QBRTCSession;
import com.quickblox.videochat.webrtc.QBRTCTypes;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;



/**
 * QuickBlox team
 */
public class OpponentsActivity extends AppCompatActivity {
    private static final String TAG = OpponentsActivity.class.getSimpleName();
    private boolean isRunForCall;
    private WebRtcSessionManager webRtcSessionManager;

    private PermissionsChecker checker;

    public static void start(Context context, boolean isRunForCall) {
        Intent intent = new Intent(context, OpponentsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        intent.putExtra(Consts.EXTRA_IS_STARTED_FOR_CALL, isRunForCall);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opponents);

        initFields();

        if (isRunForCall && webRtcSessionManager.getCurrentSession() != null) {
            CallActivity.start(OpponentsActivity.this, true);
//            overridePendingTransition( R.anim.slide_in_up, R.anim.slide_out_up );
            finish();
        }

        checker = new PermissionsChecker(getApplicationContext());
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent.getExtras() != null) {
            isRunForCall = intent.getExtras().getBoolean(Consts.EXTRA_IS_STARTED_FOR_CALL);
            if (isRunForCall && webRtcSessionManager.getCurrentSession() != null) {
                CallActivity.start(OpponentsActivity.this, true);
//                overridePendingTransition( R.anim.slide_in_up, R.anim.slide_out_up );
                finish();
            }
        }
    }

    private void initFields() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            isRunForCall = extras.getBoolean(Consts.EXTRA_IS_STARTED_FOR_CALL);
        }

        webRtcSessionManager = WebRtcSessionManager.getInstance(getApplicationContext());
    }


}