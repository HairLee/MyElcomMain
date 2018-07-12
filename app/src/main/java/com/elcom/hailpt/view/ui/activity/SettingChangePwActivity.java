package com.elcom.hailpt.view.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.elcom.hailpt.R;
import com.elcom.hailpt.core.base.BaseActivity;
import com.elcom.hailpt.databinding.ActivitySettingChangePwBinding;
import com.elcom.hailpt.model.api.request.ChangePwRq;
import com.elcom.hailpt.util.PreferUtils;
import com.elcom.hailpt.util.Toaster;
import com.elcom.hailpt.view.ui.LoginActivity;
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
                        Toaster.shortToast("Mật khẩu đã được thay đổi !");
                        PreferUtils.setToken(this,"");
                        Intent intent = new Intent(SettingChangePwActivity.this,LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                }
        );

        binding.btnOk.setOnClickListener(v -> {


            if(!binding.edtOldPw.getText().toString().equals(PreferUtils.getPassword(this))){
                Toaster.shortToast("Nhập sai password hiện tại !");
                return;
            }

            if (binding.edtNewPw.getText().toString().equals(binding.edtNewPwAgain.getText().toString())){
                showProgressDialog();
                ChangePwRq changePwRq = new ChangePwRq();
                changePwRq.setNewPassword(binding.edtNewPw.getText().toString());
                changePwRq.setOldPassword(PreferUtils.getPassword(this));
                viewModel.setChangePwRequest(changePwRq);
            } else {
                Toaster.shortToast("Xác nhận mật khẩu không đúng !");
            }

        });

        binding.imvBack.setOnClickListener(v -> onBackPressed());
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, SettingChangePwActivity.class);
        context.startActivity(starter);
    }

}
