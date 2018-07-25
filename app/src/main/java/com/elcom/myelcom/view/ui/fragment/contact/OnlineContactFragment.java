package com.elcom.myelcom.view.ui.fragment.contact;


import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.elcom.myelcom.R;
import com.elcom.myelcom.core.base.BaseFragment;
import com.elcom.myelcom.core.listener.ChatAndCallListener;
import com.elcom.myelcom.model.api.RestData;
import com.elcom.myelcom.model.api.response.User;
import com.elcom.myelcom.services.CallService;
import com.elcom.myelcom.util.Consts;
import com.elcom.myelcom.util.PermissionsChecker;
import com.elcom.myelcom.util.PreferUtils;
import com.elcom.myelcom.util.PushNotificationSender;
import com.elcom.myelcom.util.Toaster;
import com.elcom.myelcom.util.WebRtcSessionManager;
import com.elcom.myelcom.view.adapter.ContactFavouriteAdapter;
import com.elcom.myelcom.view.ui.CallActivity;
import com.elcom.myelcom.view.ui.PermissionsActivity;
import com.elcom.myelcom.viewmodel.ContactOnlineViewModel;
import com.quickblox.chat.QBChatService;
import com.quickblox.users.model.QBUser;
import com.quickblox.videochat.webrtc.QBRTCClient;
import com.quickblox.videochat.webrtc.QBRTCSession;
import com.quickblox.videochat.webrtc.QBRTCTypes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class OnlineContactFragment extends BaseFragment<ContactOnlineViewModel>  implements ChatAndCallListener {


    ContactOnlineViewModel contactOnlineViewModel;
    ContactFavouriteAdapter contactFavouriteAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private PermissionsChecker checker;
    private User user;
    public OnlineContactFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_online_contact, container, false);
        contactFavouriteAdapter = new ContactFavouriteAdapter(getContext());
        contactFavouriteAdapter.setChatAndCallListener(this);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, OrientationHelper.VERTICAL));
        recyclerView.setAdapter(contactFavouriteAdapter);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.color_blue_accent));
        swipeRefreshLayout.setOnRefreshListener(() -> {
            contactOnlineViewModel.setAllContactrequest();
        });
        init();
        return view;
    }

    @Override
    protected Class<ContactOnlineViewModel> getViewModel() {
        return ContactOnlineViewModel.class;
    }

    @Override
    protected void onCreate(Bundle instance, ContactOnlineViewModel viewModel) {
        checker = new PermissionsChecker(getContext());
        contactOnlineViewModel = viewModel;
        contactOnlineViewModel.setAllContactrequest();
    }

    private void init() {
//        ProgressDialogUtils.showProgressDialog(getContext(), 0, 0);
        contactOnlineViewModel.getOnlineContact().observe(this, new Observer<RestData<List<User>>>() {
            @Override
            public void onChanged(@Nullable RestData<List<User>> listRestData) {
//                ProgressDialogUtils.dismissProgressDialog();
                if(listRestData != null){
                    contactFavouriteAdapter.setData(listRestData.data);
                    swipeRefreshLayout.setRefreshing(false);
                }

            }
        });
    }

    public void doSearch(String search) {
        contactFavouriteAdapter.getFilter().filter(search);
    }

    @Override
    public void doChat(User user) {
            Toaster.shortToast("Đang phát triển");
    }

    @Override
    public void doCall(User user) {
        this.user = user;
        if(user != null){
            if (isLoggedInChat()) {
                startCall(false);
            }

            if (checker.lacksPermissions(Consts.PERMISSIONS)) {
                startPermissionsActivity(true);
            }
        }
    }

    private boolean isLoggedInChat() {
        if (!QBChatService.getInstance().isLoggedIn()) {
            Toaster.shortToast(R.string.dlg_signal_error);
            tryReLoginToChat();
            return false;
        }
        return true;
    }

    private void tryReLoginToChat() {
        if (sharedPrefsHelper.hasQbUser()) {
            QBUser qbUser = sharedPrefsHelper.getQbUser();
            CallService.start(getContext(), qbUser);
        }
    }

    private void startPermissionsActivity(boolean checkOnlyAudio) {
        PermissionsActivity.startActivity(getActivity(), checkOnlyAudio, Consts.PERMISSIONS);
    }

    private void startCall(boolean isVideoCall) {
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

        QBRTCClient qbrtcClient = QBRTCClient.getInstance(getContext());

        QBRTCSession newQbRtcSession = qbrtcClient.createNewSessionWithOpponents(opponentsList, conferenceType);

        Map<String, String> userInfo = new HashMap<>();
        userInfo.put("userID", PreferUtils.getUserId(getContext())+"");
        userInfo.put("quickID", user.getQuickbloxId()+"");
        userInfo.put("name", PreferUtils.getName(getContext()));
        userInfo.put("image", PreferUtils.getAvatar(getContext()));

        PreferUtils.setEmailOpponent(getContext(),user.getName());
        PreferUtils.setAvatarOpponent(getContext(),user.getAvatar());

        newQbRtcSession.startCall(userInfo);


        WebRtcSessionManager.getInstance(getContext()).setCurrentSession(newQbRtcSession);

        PushNotificationSender.sendPushMessage(opponentsList, user.getName());

        CallActivity.start(getContext(), false);
    }
}