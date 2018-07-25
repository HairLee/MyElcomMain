package com.elcom.myelcom.view.ui.fragment;


import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.elcom.myelcom.R;
import com.elcom.myelcom.core.base.BaseFragment;
import com.elcom.myelcom.util.PreferUtils;
import com.elcom.myelcom.util.Toaster;
import com.elcom.myelcom.view.ui.activity.LunchCalendarRegisActivity;
import com.elcom.myelcom.view.ui.activity.NotificationActivity;
import com.elcom.myelcom.view.ui.activity.ProfileFavouriteActivity;
import com.elcom.myelcom.view.ui.activity.SettingActivity;
import com.elcom.myelcom.view.ui.activity.TimeKeepingActivity;
import com.elcom.myelcom.viewmodel.MainViewModel;
import com.google.gson.JsonElement;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends BaseFragment<MainViewModel> implements View.OnClickListener {


    private ImageView imvAva,imvNotification;
    private TextView numberCount;
    private MainViewModel viewModel;
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
        numberCount = view.findViewById(R.id.numberCount);
        imvNotification = view.findViewById(R.id.imvNotification);
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
        if(getContext()!= null && !PreferUtils.getAvatar(getContext()).equals("")){
            if(imvAva != null){
                Glide.with(this).load(PreferUtils.getAvatar(getContext()))
                        .thumbnail(0.5f)
                        .into(imvAva);
            }
        }
    }

    @Override
    protected Class<MainViewModel> getViewModel() {
        return MainViewModel.class;
    }

    @Override
    protected void onCreate(Bundle instance, MainViewModel viewModel) {
        this.viewModel = viewModel;



    }

    @Override
    public void onResume() {
        super.onResume();

        viewModel.getNotificationCount().observe(this, new Observer<JsonElement>() {
            @Override
            public void onChanged(@Nullable JsonElement jsonElement) {


                JSONObject currency = null;
                try {
                    currency = new JSONObject(jsonElement.toString());
                    if(currency.get("data").equals(0)){
                        imvNotification.setBackgroundResource(R.drawable.notifi_zezo_ic);
                        numberCount.setVisibility(View.GONE);
                    } else {
                        imvNotification.setBackgroundResource(R.drawable.home_notification_ic);
                        numberCount.setVisibility(View.VISIBLE);
                        numberCount.setText(currency.get("data").toString());
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

        viewModel.setNotificationRequest();
    }

//    public class MyReceiver extends BroadcastReceiver{
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            Toast.makeText(context, "Intent Detected.", Toast.LENGTH_LONG).show();
//            updateAvatar();
//        }
//    }
}
