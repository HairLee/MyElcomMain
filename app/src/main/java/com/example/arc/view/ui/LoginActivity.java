package com.example.arc.view.ui;

import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.arc.R;
import com.example.arc.core.base.BaseActivity;
import com.example.arc.databinding.ActivityLoginBinding;
import com.example.arc.model.api.request.LoginReq;
import com.example.arc.model.data.Article;
import com.example.arc.services.CallService;
import com.example.arc.util.ConstantsApp;
import com.example.arc.util.Consts;
import com.example.arc.util.PreferUtils;
import com.example.arc.util.SharedPrefsHelper;
import com.example.arc.view.ui.activity.AllFriendQuickBloxActivity;
import com.example.arc.view.ui.activity.OpponentsActivity;
import com.example.arc.viewmodel.LoginViewModel;
import com.example.arc.viewmodel.MainViewModel;
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
            showProgressDialog(R.string.action_start_login);
            LoginReq loginReq = new LoginReq();
            loginReq.setEmail(binding.edtUsername.getText().toString());
            loginReq.setPassword(binding.edtPw.getText().toString());
            viewModel.setLoginParam(loginReq);

//            tryToLoginQuickServer(binding.edtUsername.getText().toString(),"12345678");

//            startOpponentsActivity(); // For test
        });
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_login;
    }

    private void init(LoginViewModel viewModel) {
//        tryToLoginQuickServer(binding.edtUsername.getText().toString(),"12345678");
        viewModel.getLoginResult().observe(this, data -> {
//            tryToLoginQuickServer("hailt1","12345678");
            if(data != null){
                hideProgressDialog();
                PreferUtils.setToken(this,ConstantsApp.BEAR + data.data.getApiToken());
                ConstantsApp.BASE64_HEADER = ConstantsApp.BEAR + data.data.getApiToken();
                tryToLoginQuickServer(binding.edtUsername.getText().toString(),"1234567890");
            }
        });
    }

    public void tryToLoginQuickServer(String username, String pv){
        showProgressDialog(R.string.login_quick);
        QBUser user = new QBUser(username, pv);
        QBUsers.signIn(user).performAsync(new QBEntityCallback<QBUser>() {
            @Override
            public void onSuccess(QBUser qbUser, Bundle bundle) {
                Log.e("hailpt"," login onSuccess "+qbUser.getEmail());
                startLoginService(qbUser);
            }

            @Override
            public void onError(QBResponseException e) {
                hideProgressDialog();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Consts.EXTRA_LOGIN_RESULT_CODE) {
            hideProgressDialog();
            boolean isLoginSuccess = data.getBooleanExtra(Consts.EXTRA_LOGIN_RESULT, false);
            String errorMessage = data.getStringExtra(Consts.EXTRA_LOGIN_ERROR_MESSAGE);
            if (isLoginSuccess) {
                saveUserData(userForSave);
                startOpponentsActivity();
            }
        }
    }
}
