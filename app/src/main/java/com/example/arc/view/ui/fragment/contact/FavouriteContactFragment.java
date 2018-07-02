package com.example.arc.view.ui.fragment.contact;


import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.StaggeredGridLayoutManager;
import com.example.arc.R;
import com.example.arc.core.base.BaseFragment;
import com.example.arc.core.listener.ChatAndCallListener;
import com.example.arc.model.api.RestData;
import com.example.arc.model.api.response.Contact;
import com.example.arc.model.api.response.User;
import com.example.arc.services.CallService;
import com.example.arc.util.Consts;
import com.example.arc.util.PermissionsChecker;
import com.example.arc.util.PushNotificationSender;
import com.example.arc.util.Toaster;
import com.example.arc.util.WebRtcSessionManager;
import com.example.arc.view.adapter.ContactFavouriteAdapter;
import com.example.arc.view.ui.CallActivity;
import com.example.arc.view.ui.PermissionsActivity;
import com.example.arc.viewmodel.ContactFavouriteViewModel;
import com.quickblox.chat.QBChatService;
import com.quickblox.users.model.QBUser;
import com.quickblox.videochat.webrtc.QBRTCClient;
import com.quickblox.videochat.webrtc.QBRTCSession;
import com.quickblox.videochat.webrtc.QBRTCTypes;

import android.support.v7.widget.OrientationHelper;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavouriteContactFragment extends BaseFragment<ContactFavouriteViewModel> implements ChatAndCallListener {


    ContactFavouriteViewModel contactFavouriteViewModel;
    ContactFavouriteAdapter contactFavouriteAdapter;
    private User user;
    private PermissionsChecker checker;
    public FavouriteContactFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favourite_contact, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        contactFavouriteAdapter = new ContactFavouriteAdapter();
        contactFavouriteAdapter.setChatAndCallListener(this);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, OrientationHelper.VERTICAL));
        recyclerView.setAdapter(contactFavouriteAdapter);

        init();

        return view;
    }

    @Override
    protected Class<ContactFavouriteViewModel> getViewModel() {
        return ContactFavouriteViewModel.class;
    }

    @Override
    protected void onCreate(Bundle instance, ContactFavouriteViewModel viewModel) {
        contactFavouriteViewModel = viewModel;
        checker = new PermissionsChecker(getContext());
    }

    private void init(){
        contactFavouriteViewModel.getFavouriteContact().observe(this, new Observer<RestData<List<User>>>() {
            @Override
            public void onChanged(@Nullable RestData<List<User>> listRestData) {
                if(listRestData != null){
                    contactFavouriteAdapter.setData(listRestData.data);
                }
            }
        });
    }

    @Override
    public void doChat(User user) {
        Log.e("hailpt"," doChat");
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

        WebRtcSessionManager.getInstance(getContext()).setCurrentSession(newQbRtcSession);

        PushNotificationSender.sendPushMessage(opponentsList, user.getName());

        CallActivity.start(getContext(), false);
    }
}
