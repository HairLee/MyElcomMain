package com.elcom.myelcom.view.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.View;

import com.bumptech.glide.Glide;
import com.elcom.myelcom.R;
import com.elcom.myelcom.core.base.BaseActivity;
import com.elcom.myelcom.databinding.ActivityProfileBinding;
import com.elcom.myelcom.util.Consts;
import com.elcom.myelcom.viewmodel.ProfileViewModel;

public class ProfileActivity extends BaseActivity<ProfileViewModel,ActivityProfileBinding> {


    private int userId = 0;
    private  ActivityProfileBinding binding;
    @Override
    protected Class<ProfileViewModel> getViewModel() {
        return ProfileViewModel.class;
    }

    @Override
    protected void onCreate(Bundle instance, ProfileViewModel viewModel, ActivityProfileBinding binding) {
        this.binding = binding;
        Intent intent = getIntent();
        if (intent.hasExtra(Consts.EXTRA_IS_USER_ID)){
            userId = intent.getIntExtra(Consts.EXTRA_IS_USER_ID,0);
        }

        init(viewModel);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_profile_favourite;
    }

    private void init(ProfileViewModel viewModel){
        viewModel.getUserProfile().observe(this, userRestData -> {
            if(userRestData != null){
                hideProgressDialog();
                binding.setUser(userRestData.data);
                if(userRestData.data.getAvatar() != null){
                    Glide.with(this).load(userRestData.data.getAvatar())
                            .thumbnail(0.5f)
                            .into(binding.profileImage);
                }
            }
        });

        showProgressDialog();
        viewModel.setRequest(userId);

        binding.imageView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.finishAfterTransition(ProfileActivity.this);
            }
        });
    }

    public static void start(Context context, int userId, ActivityOptionsCompat options) {
        Intent starter = new Intent(context, ProfileActivity.class);
        starter.putExtra(Consts.EXTRA_IS_USER_ID, userId);
        context.startActivity(starter, options.toBundle());
    }

}
