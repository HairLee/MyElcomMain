package com.elcom.myelcom.view.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.elcom.myelcom.R;
import com.elcom.myelcom.services.CallService;
import com.elcom.myelcom.util.Consts;
import com.elcom.myelcom.util.PermissionsChecker;
import com.elcom.myelcom.util.PushNotificationSender;
import com.elcom.myelcom.util.Toaster;
import com.elcom.myelcom.util.WebRtcSessionManager;
import com.elcom.myelcom.view.adapter.AllFriendQuickBloxAdapter;
import com.elcom.myelcom.view.ui.CallActivity;
import com.elcom.myelcom.view.ui.PermissionsActivity;
import com.quickblox.chat.QBChatService;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.core.request.QBPagedRequestBuilder;
import com.quickblox.users.QBUsers;
import com.quickblox.users.model.QBUser;
import com.quickblox.videochat.webrtc.QBRTCClient;
import com.quickblox.videochat.webrtc.QBRTCSession;
import com.quickblox.videochat.webrtc.QBRTCTypes;

import java.util.ArrayList;

public class AllFriendQuickBloxActivity extends AppCompatActivity implements AllFriendQuickBloxAdapter.ItemSelectedListener  {



    private AllFriendQuickBloxAdapter allFriendQuickBloxAdapter;
    private PermissionsChecker checker;
    private WebRtcSessionManager webRtcSessionManager;
    private boolean isRunForCall;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_friend_quick_blox);
        checker = new PermissionsChecker(this);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        allFriendQuickBloxAdapter = new AllFriendQuickBloxAdapter();
        allFriendQuickBloxAdapter.setOnItemClickListener(this);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, OrientationHelper.VERTICAL));
        recyclerView.setAdapter(allFriendQuickBloxAdapter);
        initFields();
        getAllUsers();
    }

    private void initFields() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            isRunForCall = extras.getBoolean(Consts.EXTRA_IS_STARTED_FOR_CALL);
        }

        webRtcSessionManager = WebRtcSessionManager.getInstance(getApplicationContext());
    }

    private void getAllUsers(){
        QBPagedRequestBuilder pagedRequestBuilder = new QBPagedRequestBuilder();
        pagedRequestBuilder.setPage(1);
        pagedRequestBuilder.setPerPage(50);

        QBUsers.getUsers(pagedRequestBuilder).performAsync(new QBEntityCallback<ArrayList<QBUser>>() {
            @Override
            public void onSuccess(ArrayList<QBUser> users, Bundle bundle) {
                allFriendQuickBloxAdapter.setData(users);

            }

            @Override
            public void onError(QBResponseException e) {

            }
        });
    }

    @Override
    public void onItemSelected(View view, QBUser item) {

        if (isLoggedInChat(item)) {
            startCall(true, item);
        }

        if (checker.lacksPermissions(Consts.PERMISSIONS)) {
            startPermissionsActivity(false);
        }

    }

    private void startPermissionsActivity(boolean checkOnlyAudio) {
        PermissionsActivity.startActivity(this, checkOnlyAudio, Consts.PERMISSIONS);
    }

    private boolean isLoggedInChat( QBUser item) {
        if (!QBChatService.getInstance().isLoggedIn()) {
            Toaster.shortToast(R.string.dlg_signal_error);
            tryReLoginToChat(item);
            return false;
        }
        return true;
    }

    private void tryReLoginToChat( QBUser item) {
        CallService.start(this, item);
    }

    private void startCall(boolean isVideoCall, QBUser qbUser) {
//        if (allFriendQuickBloxAdapter.getSelectedItems().size() > Consts.MAX_OPPONENTS_COUNT) {
//            Toaster.longToast(String.format(getString(R.string.error_max_opponents_count),
//                    Consts.MAX_OPPONENTS_COUNT));
//            return;
//        }

        ArrayList<Integer> opponentsList = new ArrayList<>();
        opponentsList.add(qbUser.getId());
        QBRTCTypes.QBConferenceType conferenceType = isVideoCall
                ? QBRTCTypes.QBConferenceType.QB_CONFERENCE_TYPE_VIDEO
                : QBRTCTypes.QBConferenceType.QB_CONFERENCE_TYPE_AUDIO;

        QBRTCClient qbrtcClient = QBRTCClient.getInstance(getApplicationContext());

        QBRTCSession newQbRtcSession = qbrtcClient.createNewSessionWithOpponents(opponentsList, conferenceType);

        WebRtcSessionManager.getInstance(this).setCurrentSession(newQbRtcSession);

        PushNotificationSender.sendPushMessage(opponentsList, qbUser.getEmail());

        CallActivity.start(this, false);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent.getExtras() != null) {
            isRunForCall = intent.getExtras().getBoolean(Consts.EXTRA_IS_STARTED_FOR_CALL);
            if (isRunForCall && webRtcSessionManager.getCurrentSession() != null) {
                CallActivity.start(AllFriendQuickBloxActivity.this, true);
            }
        }
    }

    public static void start(Context context, boolean isRunForCall) {
        Intent intent = new Intent(context, AllFriendQuickBloxActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        intent.putExtra(Consts.EXTRA_IS_STARTED_FOR_CALL, isRunForCall);
        context.startActivity(intent);
    }
}
