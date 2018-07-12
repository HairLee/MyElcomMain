package com.elcom.hailpt.view.ui.activity;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;

import com.elcom.hailpt.R;
import com.elcom.hailpt.core.base.BaseActivity;
import com.elcom.hailpt.databinding.ActivityForgetPasswordBinding;
import com.elcom.hailpt.model.api.RestData;
import com.elcom.hailpt.model.api.request.ForgetPwReq;
import com.elcom.hailpt.util.Toaster;
import com.elcom.hailpt.viewmodel.ForgetPasswordViewModel;
import com.google.gson.JsonElement;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

            viewModel.getForgetPw().observe(this, jsonElementRestData -> {
                hideProgressDialog();
                if(jsonElementRestData != null && jsonElementRestData.status_code == 200){
                    onBackPressed();
                    Toaster.shortToast("Vào email để đổi mật khẩu");
                } else {
                    Toaster.shortToast(jsonElementRestData.message);
                }

            });

            binding.btnOk.setOnClickListener(v -> {

                if(binding.edtEmail.getText().toString().equals("")){
                    Toaster.shortToast("Vui lòng nhập email");
                    return;
                }

                if(!emailValidator(binding.edtEmail.getText().toString())){
                    Toaster.shortToast("Sai định dạng email");
                    return;
                }

                showProgressDialog();
                ForgetPwReq forgetPwReq = new ForgetPwReq();
                forgetPwReq.setEmail(binding.edtEmail.getText().toString());
                viewModel.setForgetPwReq(forgetPwReq);
            });
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_forget_password;
    }


    public boolean emailValidator(String email)
    {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }


    public static void start(Context context) {
        Intent starter = new Intent(context, ForgetPasswordActivity.class);
        context.startActivity(starter);
    }
}
