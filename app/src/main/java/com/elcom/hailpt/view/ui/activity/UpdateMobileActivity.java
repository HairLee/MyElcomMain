package com.elcom.hailpt.view.ui.activity;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.elcom.hailpt.R;
import com.elcom.hailpt.core.base.BaseActivity;
import com.elcom.hailpt.databinding.ActivityUpdateMobileBinding;
import com.elcom.hailpt.model.api.RestData;
import com.elcom.hailpt.model.api.request.ChangeMobileReq;
import com.elcom.hailpt.util.Toaster;
import com.elcom.hailpt.viewmodel.ProfileFavouriteViewModel;
import com.google.gson.JsonElement;

public class UpdateMobileActivity extends BaseActivity<ProfileFavouriteViewModel, ActivityUpdateMobileBinding> {


    ProfileFavouriteViewModel profileFavouriteViewModel;
    private static boolean isChangeMobileOrHotline = false;
    @Override
    protected Class<ProfileFavouriteViewModel> getViewModel() {
        return ProfileFavouriteViewModel.class;
    }

    @Override
    protected void onCreate(Bundle instance, ProfileFavouriteViewModel viewModel, ActivityUpdateMobileBinding binding) {
        profileFavouriteViewModel = viewModel;

        binding.btnOK.setOnClickListener(view -> {

            if(binding.edtMobile.getText().toString().trim().equals("")){
                if(isChangeMobileOrHotline){
                    Toaster.shortToast("Vui lòng nhập số điện thoại");
                } else {
                    Toaster.shortToast("Vui lòng nhập số hotline");
                }
                return;
            }

            String mobileOrHotline = binding.edtMobile.getText().toString();
            ChangeMobileReq changeMobileReq = new ChangeMobileReq();
            if (isChangeMobileOrHotline){
                changeMobileReq.setHotline("");
                changeMobileReq.setMobile(mobileOrHotline);
            } else {
                changeMobileReq.setHotline(mobileOrHotline);
                changeMobileReq.setMobile("");
            }
            viewModel.setChangeMobileRequest(changeMobileReq);
        });

        init(viewModel);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_update_mobile;
    }

    private void init(ProfileFavouriteViewModel viewModel){

        viewModel.getChangeMobileResponse().observe(this, new Observer<RestData<JsonElement>>() {
            @Override
            public void onChanged(@Nullable RestData<JsonElement> jsonElementRestData) {
                Toaster.shortToast("Change OK");
            }
        });



    }


    public static void start(Context context, boolean pIsChangeMobileOrHotline) {
        isChangeMobileOrHotline = pIsChangeMobileOrHotline;
        Intent intent = new Intent(context, UpdateMobileActivity.class);
        context.startActivity(intent);
    }
}


