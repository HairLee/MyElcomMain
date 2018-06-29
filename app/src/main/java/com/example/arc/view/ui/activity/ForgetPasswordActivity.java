package com.example.arc.view.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.arc.R;
import com.example.arc.core.base.BaseActivity;
import com.example.arc.databinding.ActivityForgetPasswordBinding;
import com.example.arc.viewmodel.ForgetPasswordViewModel;

public class ForgetPasswordActivity extends BaseActivity<ForgetPasswordViewModel,ActivityForgetPasswordBinding> {


    @Override
    protected Class<ForgetPasswordViewModel> getViewModel() {
        return ForgetPasswordViewModel.class;
    }

    @Override
    protected void onCreate(Bundle instance, ForgetPasswordViewModel viewModel, ActivityForgetPasswordBinding binding) {
            binding.imvBack.setOnClickListener(view-> {
                onBackPressed();
            });

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_forget_password;
    }


    public static void start(Context context) {
        Intent starter = new Intent(context, ForgetPasswordActivity.class);
        context.startActivity(starter);
    }
}
