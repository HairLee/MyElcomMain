package com.elcom.hailpt.view.ui.fragment.contact;


import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.StaggeredGridLayoutManager;
import com.elcom.hailpt.R;
import com.elcom.hailpt.core.base.BaseFragment;
import com.elcom.hailpt.core.listener.ChatAndCallListener;
import com.elcom.hailpt.model.api.RestData;
import com.elcom.hailpt.model.api.response.Contact;
import com.elcom.hailpt.model.api.response.User;
import com.elcom.hailpt.services.CallService;
import com.elcom.hailpt.util.Consts;
import com.elcom.hailpt.util.PermissionsChecker;
import com.elcom.hailpt.util.PreferUtils;
import com.elcom.hailpt.util.ProgressDialogUtils;
import com.elcom.hailpt.util.PushNotificationSender;
import com.elcom.hailpt.util.Toaster;
import com.elcom.hailpt.util.WebRtcSessionManager;
import com.elcom.hailpt.view.adapter.ContactFavouriteAdapter;
import com.elcom.hailpt.view.ui.CallActivity;
import com.elcom.hailpt.view.ui.PermissionsActivity;
import com.elcom.hailpt.viewmodel.ContactFavouriteViewModel;
import com.quickblox.chat.QBChatService;
import com.quickblox.users.model.QBUser;
import com.quickblox.videochat.webrtc.QBRTCClient;
import com.quickblox.videochat.webrtc.QBRTCSession;
import com.quickblox.videochat.webrtc.QBRTCTypes;

import android.support.v7.widget.OrientationHelper;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavouriteContactFragment extends BaseFragment<ContactFavouriteViewModel> implements ChatAndCallListener {


    ContactFavouriteViewModel contactFavouriteViewModel;
    ContactFavouriteAdapter contactFavouriteAdapter;
    private User user;
    private PermissionsChecker checker;
    private ImageView imvLoading;
    private SwipeRefreshLayout swipeRefreshLayout;
    public FavouriteContactFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favourite_contact, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        contactFavouriteAdapter = new ContactFavouriteAdapter(getContext());
        contactFavouriteAdapter.setChatAndCallListener(this);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, OrientationHelper.VERTICAL));
        recyclerView.setAdapter(contactFavouriteAdapter);
        imvLoading = view.findViewById(R.id.imvLoading);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.color_blue_accent));
        swipeRefreshLayout.setOnRefreshListener(() -> {
            contactFavouriteViewModel.setAllContactrequest();
        });

//        makeDialogProgress();
        ProgressDialogUtils.showProgressDialog(getContext(), 0, 0);
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
        contactFavouriteViewModel.setAllContactrequest();
    }

    private void init(){
        contactFavouriteViewModel.getFavouriteContact().observe(this, new Observer<RestData<List<User>>>() {
            @Override
            public void onChanged(@Nullable RestData<List<User>> listRestData) {
                ProgressDialogUtils.dismissProgressDialog();
                if(listRestData != null){
                    contactFavouriteAdapter.setData(listRestData.data);
//                    rotation.cancel();
                    imvLoading.setVisibility(View.GONE);
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });

    }

    public void doSearch(String search)
    {
        contactFavouriteAdapter.getFilter().filter(search);
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

    Animation rotation;
    private void makeDialogProgress(){
        rotation = AnimationUtils.loadAnimation(getContext(), R.anim.clockwise_rotation);
        rotation.setRepeatCount(Animation.INFINITE);
        imvLoading.startAnimation(rotation);
    }
}
