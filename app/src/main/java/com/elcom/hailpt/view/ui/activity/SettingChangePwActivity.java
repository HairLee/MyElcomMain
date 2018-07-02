package com.elcom.hailpt.view.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.elcom.hailpt.R;
import com.elcom.hailpt.core.base.BaseActivity;
import com.elcom.hailpt.databinding.ActivitySettingChangePwBinding;
import com.elcom.hailpt.model.api.request.ChangePwRq;
import com.elcom.hailpt.util.Toaster;
import com.elcom.hailpt.viewmodel.SettingChangePasswordViewModel;

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
