package com.example.arc.view.ui.activity;

import android.os.Bundle;

import com.example.arc.R;
import com.example.arc.core.base.BaseActivity;
import com.example.arc.databinding.ActivityProfileBinding;
import com.example.arc.viewmodel.ProfileViewModel;

public class ProfileActivity extends BaseActivity<ProfileViewModel,ActivityProfileBinding> {


    @Override
    protected Class<ProfileViewModel> getViewModel() {
        return ProfileViewModel.class;
    }

    @Override
    protected void onCreate(Bundle instance, ProfileViewModel viewModel, ActivityProfileBinding binding) {
        init(viewModel);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_profile;
    }

    private void init(ProfileViewModel viewModel){

    }


}
