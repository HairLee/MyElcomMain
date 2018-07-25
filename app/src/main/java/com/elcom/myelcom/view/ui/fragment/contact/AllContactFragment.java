package com.elcom.myelcom.view.ui.fragment.contact;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.elcom.myelcom.R;
import com.elcom.myelcom.core.base.BaseFragment;
import com.elcom.myelcom.core.listener.AllContactFragmentListener;
import com.elcom.myelcom.model.api.response.Contact;
import com.elcom.myelcom.model.api.response.User;
import com.elcom.myelcom.model.db.QbUsersDbManager;
import com.elcom.myelcom.services.CallService;
import com.elcom.myelcom.util.ConstantsApp;
import com.elcom.myelcom.util.Consts;
import com.elcom.myelcom.util.PermissionsChecker;
import com.elcom.myelcom.util.PreferUtils;
import com.elcom.myelcom.util.PushNotificationSender;
import com.elcom.myelcom.util.Toaster;
import com.elcom.myelcom.util.WebRtcSessionManager;
import com.elcom.myelcom.view.adapter.AllContactAdapter;
import com.elcom.myelcom.view.adapter.AllContactSuggestAdapter;
import com.elcom.myelcom.view.adapter.Contact.Genre;
import com.elcom.myelcom.view.adapter.Contact.GenreAdapter;
import com.elcom.myelcom.view.ui.CallActivity;
import com.elcom.myelcom.view.ui.PermissionsActivity;
import com.elcom.myelcom.view.ui.activity.ProfileFavouriteActivity;
import com.elcom.myelcom.viewmodel.AllContactSuggestViewModel;
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
public class AllContactFragment extends BaseFragment<AllContactSuggestViewModel> implements AllContactFragmentListener {



    AllContactSuggestViewModel allContactSuggestViewModel;
    private AllContactSuggestAdapter allContactSuggestAdaprer;
    private AllContactAdapter allContactAdaprer;
    //    private List<ContactSuggest> data = new ArrayList<>();
    private List<Contact> contacts = new ArrayList<>();
    private   RecyclerView recyclerView;
    private ImageView imvLoading;
    private User user;

    private QbUsersDbManager dbManager;
    private PermissionsChecker checker;
    public AllContactFragment() {
        // Required empty public constructor
    }

    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if(view == null){
            view = inflater.inflate(R.layout.fragment_all_contact, container, false);
            imvLoading = view.findViewById(R.id.imvLoading);
//            ProgressDialogUtils.showProgressDialog(getContext(), 0, 0);
//            makeDialogProgress();
        }
        // Inflate the layout for this fragment
//        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
//        RecyclerView recyclerViewBottom = view.findViewById(R.id.recyclerViewBottom);
//
//        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, OrientationHelper.VERTICAL));
//        recyclerViewBottom.setLayoutManager(new StaggeredGridLayoutManager(1, OrientationHelper.VERTICAL));
//
//        allContactSuggestAdaprer = new AllContactSuggestAdapter();
//        allContactAdaprer = new AllContactAdapter();
//
//        recyclerView.setAdapter(allContactSuggestAdaprer);
//        recyclerViewBottom.setAdapter(allContactAdaprer);
//
//        Log.e("hailpt"," AllContactFragment onCreateView ");
//        showProgressDialog(R.string.dlg_loading1);
        recyclerView = view.findViewById(R.id.recyclerViewBottom);

        registerChangeAvatarReceiver();
        return view;
    }

    public void doSearch(String searh)
    {
        Log.e("hailpt"," doChat"+searh);
    }

    @Override
    protected Class<AllContactSuggestViewModel> getViewModel() {
        return AllContactSuggestViewModel.class;
    }

    @Override
    protected void onCreate(Bundle instance, AllContactSuggestViewModel viewModel) {
        checker = new PermissionsChecker(getContext());
        allContactSuggestViewModel = viewModel;
        allContactSuggestViewModel.getAllContactSuggest().observe(this, contactSuggestRestData -> {
            Log.e("hailpt"," AllContactFragment onCreate ");
            if(contactSuggestRestData != null){
//                allContactSuggestAdaprer.setData(contactSuggestRestData.data);
            }
        });

        allContactSuggestViewModel.getAllContact().observe(this, listRestData -> {
//            allContactAdaprer.setData(listRestData.data);
//            hideProgressDialog();
            contacts = listRestData.data;
//            rotation.cancel();
            imvLoading.setVisibility(View.GONE);
            setupViewTest();
//            ProgressDialogUtils.dismissProgressDialog();
        });


        allContactSuggestViewModel.setAllContactrequest();
    }


    private void setupViewTest(){

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        RecyclerView.ItemAnimator animator = recyclerView.getItemAnimator();
        if (animator instanceof DefaultItemAnimator) {
            ((DefaultItemAnimator) animator).setSupportsChangeAnimations(false);
        }

        GenreAdapter adapter = new GenreAdapter(makeGenres(),getContext());
        adapter.setAllContactFragmentListener(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    public List<Genre> makeGenres() {
        List<Genre> genres = new ArrayList<>();
        for (int i = 0; i < contacts.size(); i++) {
            Genre genre = new Genre(contacts.get(i).getGroupName(), contacts.get(i).getUsers(),R.drawable.expand_down_ic);
            genres.add(genre);
        }
        return genres;
    }



    @Override
    public void onViewProfile(View view,int userId) {
        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(getActivity(), view, getString(R.string.trans_shared_image));
        ProfileFavouriteActivity.start(getContext(),userId,options);
    }

    @Override
    public void onCallVideo(User user) {
        this.user = user;
        if(user != null){
            if (isLoggedInChat()) {
                startCall(false);
            }

            if (checker.lacksPermissions(Consts.PERMISSIONS)) {
                startPermissionsActivity(true);
            }
        }
//        Toaster.longToast("Call "+user.getQuickbloxId());
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
        newQbRtcSession.startCall(userInfo);


        PreferUtils.setEmailOpponent(getContext(),user.getName());
        PreferUtils.setAvatarOpponent(getContext(),user.getAvatar());

        WebRtcSessionManager.getInstance(getContext()).setCurrentSession(newQbRtcSession);

        PushNotificationSender.sendPushMessage(opponentsList, user.getName());

        CallActivity.start(getContext(), false);
    }


    private void registerChangeAvatarReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        ChangeAvatarReceiver changeAvatarReceiver = new ChangeAvatarReceiver();
        intentFilter.addAction(ConstantsApp.BROARD_CHANGE_AVATAR);
        getActivity().registerReceiver(changeAvatarReceiver, intentFilter);
    }

    Animation rotation;
    private void makeDialogProgress(){
        rotation = AnimationUtils.loadAnimation(getContext(), R.anim.clockwise_rotation);
        rotation.setRepeatCount(Animation.INFINITE);
        imvLoading.startAnimation(rotation);
    }

    public class ChangeAvatarReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            allContactSuggestViewModel.setAllContactrequest();
        }
    }


}
