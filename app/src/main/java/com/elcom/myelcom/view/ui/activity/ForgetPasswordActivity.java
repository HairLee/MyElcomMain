package com.elcom.myelcom.view.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.elcom.myelcom.R;
import com.elcom.myelcom.core.base.BaseActivity;
import com.elcom.myelcom.databinding.ActivityForgetPasswordBinding;
import com.elcom.myelcom.model.api.request.ForgetPwReq;
import com.elcom.myelcom.util.NetworkConnectionChecker;
import com.elcom.myelcom.util.Toaster;
import com.elcom.myelcom.viewmodel.ForgetPasswordViewModel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ForgetPasswordActivity extends BaseActivity<ForgetPasswordViewModel,ActivityForgetPasswordBinding> implements  NetworkConnectionChecker.OnConnectivityChangedListener {


    private NetworkConnectionChecker networkConnectionChecker;
    private boolean mNetAvailableNow = false;
    @Override
    protected Class<ForgetPasswordViewModel> getViewModel() {
        return ForgetPasswordViewModel.class;
    }

    @Override
    protected void onCreate(Bundle instance, ForgetPasswordViewModel viewModel, ActivityForgetPasswordBinding binding) {

            initWiFiManagerListener();
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

                if(!mNetAvailableNow){
                    Toaster.shortToast(R.string.check_internet_plz);
                    return;
                }

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

    private void initWiFiManagerListener() {
        networkConnectionChecker = new NetworkConnectionChecker(getApplication());
    }

    @Override
    protected void onResume() {
        super.onResume();
        networkConnectionChecker.registerListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        networkConnectionChecker.unregisterListener(this);
    }


    @Override
    public void connectivityChanged(boolean availableNow) {
        mNetAvailableNow = availableNow;
        if(availableNow){

        } else {
            Toaster.shortToast(R.string.check_internet_plz);
        }
    }
}
