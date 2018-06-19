package com.example.arc.view.ui.fragment;


import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.arc.R;
import com.example.arc.services.CallService;
import com.example.arc.util.CollectionsUtils;
import com.example.arc.util.Consts;
import com.example.arc.util.PermissionsChecker;
import com.example.arc.util.PushNotificationSender;
import com.example.arc.util.Toaster;
import com.example.arc.util.WebRtcSessionManager;
import com.example.arc.view.adapter.AllFriendQuickBloxAdapter;
import com.example.arc.view.adapter.NewsAdapter;
import com.example.arc.view.ui.CallActivity;
import com.example.arc.view.ui.PermissionsActivity;
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

import static org.webrtc.ContextUtils.getApplicationContext;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllFriendQuickBloxFragment extends Fragment implements AllFriendQuickBloxAdapter.ItemSelectedListener {

    public String TAG = "AllFriendQuickBloxFragment";
    private AllFriendQuickBloxAdapter allFriendQuickBloxAdapter;
    private PermissionsChecker checker;
    public AllFriendQuickBloxFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return DataBindingUtil.inflate(inflater, R.layout.fragment_all_friend_qick_blox, container, false).getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        checker = new PermissionsChecker(getContext());
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        allFriendQuickBloxAdapter = new AllFriendQuickBloxAdapter();
        allFriendQuickBloxAdapter.setOnItemClickListener(this);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, OrientationHelper.VERTICAL));
        recyclerView.setAdapter(allFriendQuickBloxAdapter);
        getAllUsers();
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
        PermissionsActivity.startActivity(getActivity(), checkOnlyAudio, Consts.PERMISSIONS);
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
        CallService.start(getContext(), item);
    }

    private void startCall(boolean isVideoCall, QBUser qbUser) {
//        if (allFriendQuickBloxAdapter.getSelectedItems().size() > Consts.MAX_OPPONENTS_COUNT) {
//            Toaster.longToast(String.format(getString(R.string.error_max_opponents_count),
//                    Consts.MAX_OPPONENTS_COUNT));
//            return;
//        }

        Log.d(TAG, "startCall()");
        ArrayList<Integer> opponentsList = new ArrayList<>();
        opponentsList.add(qbUser.getId());
        QBRTCTypes.QBConferenceType conferenceType = isVideoCall
                ? QBRTCTypes.QBConferenceType.QB_CONFERENCE_TYPE_VIDEO
                : QBRTCTypes.QBConferenceType.QB_CONFERENCE_TYPE_AUDIO;

        QBRTCClient qbrtcClient = QBRTCClient.getInstance(getApplicationContext());

        QBRTCSession newQbRtcSession = qbrtcClient.createNewSessionWithOpponents(opponentsList, conferenceType);

        WebRtcSessionManager.getInstance(getContext()).setCurrentSession(newQbRtcSession);

        PushNotificationSender.sendPushMessage(opponentsList, qbUser.getFullName());

        CallActivity.start(getContext(), false);
        Log.d(TAG, "conferenceType = " + conferenceType);
    }
}
