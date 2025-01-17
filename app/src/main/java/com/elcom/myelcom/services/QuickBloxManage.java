package com.elcom.myelcom.services;

import android.app.Activity;
import android.content.Context;

import com.elcom.myelcom.R;
import com.elcom.myelcom.model.api.response.User;
import com.elcom.myelcom.util.Consts;
import com.elcom.myelcom.util.PushNotificationSender;
import com.elcom.myelcom.util.SharedPrefsHelper;
import com.elcom.myelcom.util.Toaster;
import com.elcom.myelcom.util.WebRtcSessionManager;
import com.elcom.myelcom.view.ui.CallActivity;
import com.elcom.myelcom.view.ui.PermissionsActivity;
import com.quickblox.chat.QBChatService;
import com.quickblox.users.model.QBUser;
import com.quickblox.videochat.webrtc.QBRTCClient;
import com.quickblox.videochat.webrtc.QBRTCSession;
import com.quickblox.videochat.webrtc.QBRTCTypes;

import java.util.ArrayList;

/**
 * Created by Hailpt on 6/21/2018.
 */
public class QuickBloxManage {

    public SharedPrefsHelper sharedPrefsHelper;
    public QuickBloxManage(){
        sharedPrefsHelper = SharedPrefsHelper.getInstance();
    }

    private boolean isLoggedInChat(Context context) {
        if (!QBChatService.getInstance().isLoggedIn()) {
            Toaster.shortToast(R.string.dlg_signal_error);
            tryReLoginToChat(context);
            return false;
        }
        return true;
    }

    private void tryReLoginToChat(Context context) {
        if (sharedPrefsHelper.hasQbUser()) {
            QBUser qbUser = sharedPrefsHelper.getQbUser();
            CallService.start(context, qbUser);
        }
    }

    private void startPermissionsActivity(Activity context, boolean checkOnlyAudio) {
        PermissionsActivity.startActivity(context, checkOnlyAudio, Consts.PERMISSIONS);
    }

    private void startCall(User user, boolean isVideoCall, Context context) {
//        if (opponentsAdapter.getSelectedItems().size() > Consts.MAX_OPPONENTS_COUNT) {
//            Toaster.longToast(String.format(getString(R.string.error_max_opponents_count),
//                    Consts.MAX_OPPONENTS_COUNT));
//            return;
//        }

        ArrayList<Integer> opponentsList = new ArrayList<>();
        opponentsList.add(user.getQuickbloxId());
        QBRTCTypes.QBConferenceType conferenceType = isVideoCall
                ? QBRTCTypes.QBConferenceType.QB_CONFERENCE_TYPE_VIDEO
                : QBRTCTypes.QBConferenceType.QB_CONFERENCE_TYPE_AUDIO;

        QBRTCClient qbrtcClient = QBRTCClient.getInstance(context);

        QBRTCSession newQbRtcSession = qbrtcClient.createNewSessionWithOpponents(opponentsList, conferenceType);

        WebRtcSessionManager.getInstance(context).setCurrentSession(newQbRtcSession);

        PushNotificationSender.sendPushMessage(opponentsList, user.getName());

        CallActivity.start(context, false);
    }

}
