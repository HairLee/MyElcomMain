package com.elcom.hailpt.view.ui.activity;

import android.os.Bundle;

import com.elcom.hailpt.R;
import com.elcom.hailpt.core.base.BaseActivity;
import com.elcom.hailpt.databinding.ActivityUpdateMobileBinding;
import com.elcom.hailpt.model.api.request.ChangeMobileReq;
import com.elcom.hailpt.util.Toaster;
import com.elcom.hailpt.viewmodel.ProfileFavouriteViewModel;

public class UpdateMobileActivity extends BaseActivity<ProfileFavouriteViewModel, ActivityUpdateMobileBinding> {


    ProfileFavouriteViewModel profileFavouriteViewModel;
    private static boolean isChangeMobileOrHotline = false;
    public static String KEY_IS_MOBILE_CHANGED = "KEY_IS_MOBILE_CHANGED";
    public static int KEY_IS_MOBILE_REQUSET = 9999;
    @Override
    protected Class<ProfileFavouriteViewModel> getViewModel() {
        return ProfileFavouriteViewModel.class;
    }

    @Override
    protected void onCreate(Bundle instance, ProfileFavouriteViewModel viewModel, ActivityUpdateMobileBinding binding) {
        profileFavouriteViewModel = viewModel;
        getSupportActionBar().hide();
        if (getIntent().hasExtra("KEY_IS_MOBILE_CHANGED")){
            isChangeMobileOrHotline = getIntent().getExtras().getBoolean("KEY_IS_MOBILE_CHANGED");
            if(isChangeMobileOrHotline){
                binding.tvTitle.setText("Nhập số điện thoại bạn muốn đổi");
            } else {
                binding.tvTitle.setText("Nhập số hotline bạn muốn đổi");
            }
        }

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

        binding.btnNo.setOnClickListener(view -> onBackPressed());

        binding.lnRoot.setOnClickListener(view-> onBackPressed());

        init(viewModel);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_update_mobile;
    }

    private void init(ProfileFavouriteViewModel viewModel){

        viewModel.getChangeMobileResponse().observe(this, jsonElementRestData -> {
            finish();
        });



    }

}


