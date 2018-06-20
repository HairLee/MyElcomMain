package com.example.arc.view.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.arc.R;
import com.example.arc.core.base.BaseActivity;
import com.example.arc.databinding.ActivitySettingChangePwBinding;
import com.example.arc.model.api.request.ChangePwRq;
import com.example.arc.util.Toaster;
import com.example.arc.viewmodel.SettingChangePasswordViewModel;

public class SettingChangePwActivity extends BaseActivity<SettingChangePasswordViewModel,ActivitySettingChangePwBinding> {

    private ActivitySettingChangePwBinding binding;
    @Override
    protected Class<SettingChangePasswordViewModel> getViewModel() {
        return SettingChangePasswordViewModel.class;
    }

    @Override
    protected void onCreate(Bundle instance, SettingChangePasswordViewModel viewModel, ActivitySettingChangePwBinding binding) {
        this.binding = binding;
        init(viewModel);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_setting_change_pw;
    }

    private void init(SettingChangePasswordViewModel viewModel){
        viewModel.getChangePwResult().observe(this, jsonElementRestData ->{
                    hideProgressDialog();
                    Toaster.longToast(jsonElementRestData.message);
                    if(jsonElementRestData.status_code == 200){
                        onBackPressed();
                    }

                }
        );

        binding.btnOk.setOnClickListener(v -> {
            showProgressDialog();
            ChangePwRq changePwRq = new ChangePwRq();
            changePwRq.setNewPassword("12345678");
            changePwRq.setOldPassword("12345678");
            changePwRq.setConfirmPassword("12345678");
            viewModel.setChangePwRequest(changePwRq);
        });
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, SettingChangePwActivity.class);
        context.startActivity(starter);
    }

}
