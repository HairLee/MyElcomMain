package com.example.arc.view.ui.fragment.contact;


import android.arch.lifecycle.Observer;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.arc.R;
import com.example.arc.core.base.BaseFragment;
import com.example.arc.core.listener.AllContactFragmentListener;
import com.example.arc.model.api.RestData;
import com.example.arc.model.api.response.Contact;
import com.example.arc.model.api.response.ContactSuggest;
import com.example.arc.model.api.response.User;
import com.example.arc.model.db.QbUsersDbManager;
import com.example.arc.services.CallService;
import com.example.arc.util.CollectionsUtils;
import com.example.arc.util.ConstantsApp;
import com.example.arc.util.Consts;
import com.example.arc.util.PermissionsChecker;
import com.example.arc.util.PushNotificationSender;
import com.example.arc.util.SharedPrefsHelper;
import com.example.arc.util.Toaster;
import com.example.arc.util.WebRtcSessionManager;
import com.example.arc.view.adapter.AllContactAdapter;
import com.example.arc.view.adapter.AllContactSuggestAdapter;
import com.example.arc.view.adapter.Contact.Genre;
import com.example.arc.view.adapter.Contact.GenreAdapter;
import com.example.arc.view.ui.CallActivity;
import com.example.arc.view.ui.DetailActivity;
import com.example.arc.view.ui.PermissionsActivity;
import com.example.arc.view.ui.activity.ProfileActivity;
import com.example.arc.view.ui.activity.ProfileFavouriteActivity;
import com.example.arc.viewmodel.AllContactSuggestViewModel;
import com.quickblox.chat.QBChatService;
import com.quickblox.users.model.QBUser;
import com.quickblox.videochat.webrtc.QBRTCClient;
import com.quickblox.videochat.webrtc.QBRTCSession;
import com.quickblox.videochat.webrtc.QBRTCTypes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.webrtc.ContextUtils.getApplicationContext;

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
//            dbManager = QbUsersDbManager.getInstance(getContext());
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
        setupViewTest();
        registerChangeAvatarReceiver();
        return view;
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
            setupViewTest();
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

    public class ChangeAvatarReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            allContactSuggestViewModel.setAllContactrequest();
        }
    }


}
