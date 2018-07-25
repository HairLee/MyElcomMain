package com.elcom.myelcom.view.ui;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.elcom.myelcom.R;
import com.elcom.myelcom.core.base.BaseActivity;
import com.elcom.myelcom.databinding.ActivityLoginBinding;
import com.elcom.myelcom.model.api.request.LoginReq;
import com.elcom.myelcom.model.api.response.User;
import com.elcom.myelcom.services.CallService;
import com.elcom.myelcom.util.ConstantsApp;
import com.elcom.myelcom.util.Consts;
import com.elcom.myelcom.util.NetworkConnectionChecker;
import com.elcom.myelcom.util.PreferUtils;
import com.elcom.myelcom.util.SharedPrefsHelper;
import com.elcom.myelcom.util.Toaster;
import com.elcom.myelcom.view.ui.activity.ForgetPasswordActivity;
import com.elcom.myelcom.viewmodel.LoginViewModel;
import com.onesignal.OneSignal;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.core.helper.StringifyArrayList;
import com.quickblox.users.QBUsers;
import com.quickblox.users.model.QBUser;

public class LoginActivity extends BaseActivity<LoginViewModel,ActivityLoginBinding> implements  NetworkConnectionChecker.OnConnectivityChangedListener  {
    private NetworkConnectionChecker networkConnectionChecker;
    private  ActivityLoginBinding binding;
    private QBUser userForSave;
    private boolean mNetAvailableNow = false;
    @Override
    protected Class<LoginViewModel> getViewModel() {
        return LoginViewModel.class;
    }

    @Override
    protected void onCreate(Bundle instance, LoginViewModel viewModel, ActivityLoginBinding binding) {
        this.binding = binding;
        init(viewModel);
        binding.btnLogin.setOnClickListener(view -> {

            if(!mNetAvailableNow){
                Toaster.shortToast(R.string.check_internet_plz);
                return;
            }

            if (binding.edtUsername.getText().toString().equals("")){
                Toaster.shortToast("Vui lòng nhập email");
                return;
            }

            if (binding.edtPw.getText().toString().equals("")){
                Toaster.shortToast("Vui lòng nhập mật khẩu");
                return;
            }

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
        initWiFiManagerListener();
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
                PreferUtils.setName(this,data.data.getName());
                ConstantsApp.BASE64_HEADER = ConstantsApp.BEAR + data.data.getApiToken();
                saveUser(data.data);
                tryToLoginQuickServer(binding.edtUsername.getText().toString(),"1234567890");

                OneSignal.sendTag("user_id",data.data.getId()+"");
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

    private void initWiFiManagerListener() {
        networkConnectionChecker = new NetworkConnectionChecker(getApplication());
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
