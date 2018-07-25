package com.elcom.myelcom.view.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.elcom.myelcom.R;
import com.elcom.myelcom.util.Consts;
import com.elcom.myelcom.util.PermissionsChecker;
import com.elcom.myelcom.util.WebRtcSessionManager;
import com.elcom.myelcom.view.ui.CallActivity;


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