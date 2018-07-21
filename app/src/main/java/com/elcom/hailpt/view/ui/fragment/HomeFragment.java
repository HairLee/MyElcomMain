package com.elcom.hailpt.view.ui.fragment;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.elcom.hailpt.R;
import com.elcom.hailpt.util.PreferUtils;
import com.elcom.hailpt.util.Toaster;
import com.elcom.hailpt.view.ui.activity.LunchCalendarRegisActivity;
import com.elcom.hailpt.view.ui.activity.NotificationActivity;
import com.elcom.hailpt.view.ui.activity.ProfileFavouriteActivity;
import com.elcom.hailpt.view.ui.activity.SettingActivity;
import com.elcom.hailpt.view.ui.activity.TimeKeepingActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {


    private ImageView imvAva;
    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initLayout(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void initLayout(View view){
        view.findViewById(R.id.rlNote).setOnClickListener(this);
        view.findViewById(R.id.rlLunch).setOnClickListener(this);
        view.findViewById(R.id.rlSetting).setOnClickListener(this);
        view.findViewById(R.id.imvNotification).setOnClickListener(this);
        view.findViewById(R.id.rlCar).setOnClickListener(this);
        view.findViewById(R.id.rlLibrary).setOnClickListener(this);
        view.findViewById(R.id.rlTool).setOnClickListener(this);
        imvAva = view.findViewById(R.id.imvAva);
        imvAva.setOnClickListener(this);
        updateAvatar();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rlNote:
                TimeKeepingActivity.start(getContext(),1);
                break;
            case R.id.rlLunch:
                LunchCalendarRegisActivity.start(getContext(),1);
                break;
            case R.id.rlSetting:
                SettingActivity.start(getContext());
                break;
            case R.id.imvNotification:
                NotificationActivity.start(getContext());
                break;
            case R.id.imvAva:
                goToMyProfile();
                break;

            case R.id.rlCar:
                Toaster.shortToast("Đang phát triển");
                break;

            case R.id.rlLibrary:
                Toaster.shortToast("Đang phát triển");
                break;

            case R.id.rlTool:
                Toaster.shortToast("Đang phát triển");
                break;

        }
    }

    private void goToMyProfile(){
        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(getActivity(), imvAva, getString(R.string.trans_shared_image));
        ProfileFavouriteActivity.start(getContext(),PreferUtils.getUserId(getContext()),options);
    }


    public void updateAvatar(){
        if(!PreferUtils.getAvatar(getContext()).equals("")){
            Glide.with(this).load(PreferUtils.getAvatar(getContext()))
                    .thumbnail(0.5f)
                    .into(imvAva);
        }
    }

    public class MyReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(context, "Intent Detected.", Toast.LENGTH_LONG).show();
            updateAvatar();
        }
    }
}
