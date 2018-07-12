package com.elcom.hailpt.view.ui;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;

import com.elcom.hailpt.R;
import com.elcom.hailpt.core.base.BaseActivity;
import com.elcom.hailpt.databinding.ActivityLoginBinding;
import com.elcom.hailpt.model.api.request.LoginReq;
import com.elcom.hailpt.model.api.response.User;
import com.elcom.hailpt.model.data.Article;
import com.elcom.hailpt.services.CallService;
import com.elcom.hailpt.util.ConstantsApp;
import com.elcom.hailpt.util.Consts;
import com.elcom.hailpt.util.PreferUtils;
import com.elcom.hailpt.util.SharedPrefsHelper;
import com.elcom.hailpt.view.ui.activity.AllFriendQuickBloxActivity;
import com.elcom.hailpt.view.ui.activity.ForgetPasswordActivity;
import com.elcom.hailpt.view.ui.activity.OpponentsActivity;
import com.elcom.hailpt.viewmodel.LoginViewModel;
import com.elcom.hailpt.viewmodel.MainViewModel;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.core.helper.StringifyArrayList;
import com.quickblox.users.QBUsers;
import com.quickblox.users.model.QBUser;

import java.util.ArrayList;

public class LoginActivity extends BaseActivity<LoginViewModel,ActivityLoginBinding> {

    private  ActivityLoginBinding binding;
    private QBUser userForSave;
    @Override
    protected Class<LoginViewModel> getViewModel() {
        return LoginViewModel.class;
    }

    @Override
    protected void onCreate(Bundle instance, LoginViewModel viewModel, ActivityLoginBinding binding) {
        this.binding = binding;
        init(viewModel);
        binding.btnLogin.setOnClickListener(view -> {
            binding.btnLogin.setEnabled(false);
            binding.rlLoading.setVisibility(View.VISIBLE);
            binding.lnWrongPw.setVisibility(View.GONE);
            makeDialogProgress();
            LoginReq loginReq = new LoginReq();
            loginReq.setEmail(binding.edtUsername.getText().toString());
            loginReq.setPassword(binding.edtPw.getText().toString());
            viewModel.setLoginParam(loginReq);
        });

        binding.tvForgetPw.setOnClickListener(view-> {
            ForgetPasswordActivity.start(this);
        });
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_login;
    }

    private void init(LoginViewModel viewModel) {
        viewModel.getLoginResult().observe(this, data -> {
            binding.btnLogin.setEnabled(true);
            if (data != null &&  data.status_code != 200){
                binding.lnWrongPw.setVisibility(View.VISIBLE);
                binding.rlLoading.setVisibility(View.GONE);
                return;
            }

            if(data != null && data.data != null){

                PreferUtils.setToken(this,data.data.getApiToken());
                PreferUtils.setPassword(this,binding.edtPw.getText().toString());
                PreferUtils.setUserId(this,data.data.getId());
                PreferUtils.setAvatar(this,data.data.getAvatar());
                PreferUtils.setEmail(this,data.data.getEmail());
                ConstantsApp.BASE64_HEADER = ConstantsApp.BEAR + data.data.getApiToken();
                saveUser(data.data);
                tryToLoginQuickServer(binding.edtUsername.getText().toString(),"1234567890");
            }
        });

        binding.edtUsername.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus){
                binding.edtUsername.setBackground(getResources().getDrawable(R.drawable.radius_edittext_white_bg_blue_srtoke_layout));
            } else {
                binding.edtUsername.setBackground(getResources().getDrawable(R.drawable.radius_edittext_white_bg_gray_srtoke_layout));
            }
        });

        binding.edtPw.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus){
                binding.edtPw.setBackground(getResources().getDrawable(R.drawable.radius_edittext_white_bg_blue_srtoke_layout));
            } else {
                binding.edtPw.setBackground(getResources().getDrawable(R.drawable.radius_edittext_white_bg_gray_srtoke_layout));
            }
        });
    }

    public void tryToLoginQuickServer(String username, String pv){
        QBUser user = new QBUser(username, pv);
        QBUsers.signIn(user).performAsync(new QBEntityCallback<QBUser>() {
            @Override
            public void onSuccess(QBUser qbUser, Bundle bundle) {
                Log.e("hailpt"," login onSuccess "+qbUser.getEmail());
                startLoginService(qbUser);
            }

            @Override
            public void onError(QBResponseException e) {
                Log.e("hailpt"," login onError "+e.getErrors());
            }
        });
    }

    private void startLoginService(QBUser qbUser) {
        userForSave = qbUser;
        qbUser.setPassword("1234567890");
        StringifyArrayList<String> userTags = new StringifyArrayList<>();
        userTags.add(binding.edtUsername.getText().toString());
        qbUser.setTags(userTags);

        Intent tempIntent = new Intent(this, CallService.class);
        PendingIntent pendingIntent = createPendingResult(Consts.EXTRA_LOGIN_RESULT_CODE, tempIntent, 0);
        CallService.start(this, qbUser, pendingIntent);
    }

    private void startOpponentsActivity() {
        HomeActivity.start(LoginActivity.this, false);
        finish();
    }

    private void saveUserData(QBUser qbUser) {
        SharedPrefsHelper sharedPrefsHelper = SharedPrefsHelper.getInstance();
        sharedPrefsHelper.save(Consts.PREF_CURREN_ROOM_NAME, qbUser.getTags().get(0));
        sharedPrefsHelper.saveQbUser(qbUser);
    }

    private void saveUser(User user) {
        SharedPrefsHelper sharedPrefsHelper = SharedPrefsHelper.getInstance();
        sharedPrefsHelper.save(Consts.PREF_USER_NAME, user.getId().toString());
        sharedPrefsHelper.saveUser(user);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Consts.EXTRA_LOGIN_RESULT_CODE) {
            binding.rlLoading.setVisibility(View.GONE);
            boolean isLoginSuccess = data.getBooleanExtra(Consts.EXTRA_LOGIN_RESULT, false);
            String errorMessage = data.getStringExtra(Consts.EXTRA_LOGIN_ERROR_MESSAGE);
            if (isLoginSuccess) {
                saveUserData(userForSave);
                startOpponentsActivity();
            }
        }
    }

    private void makeDialogProgress(){
        Animation rotation = AnimationUtils.loadAnimation(this, R.anim.clockwise_rotation);
        rotation.setRepeatCount(Animation.INFINITE);
        binding.imvLoading.startAnimation(rotation);
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, LoginActivity.class);
        context.startActivity(starter);
    }
}
