package com.elcom.hailpt.view.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.elcom.hailpt.R;
import com.elcom.hailpt.core.base.BaseActivity;
import com.elcom.hailpt.databinding.ActivitySettingBinding;
import com.elcom.hailpt.model.api.response.Support;
import com.elcom.hailpt.util.PreferUtils;
import com.elcom.hailpt.util.Toaster;
import com.elcom.hailpt.view.ui.LoginActivity;
import com.elcom.hailpt.viewmodel.SettingViewModel;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.users.QBUsers;

public class SettingActivity extends BaseActivity<SettingViewModel,ActivitySettingBinding> implements View.OnClickListener {


    private  SettingViewModel viewModel;
    private TextView tvEmail;
    @Override
    protected Class<SettingViewModel> getViewModel() {
        return SettingViewModel.class;
    }

    @Override
    protected void onCreate(Bundle instance, SettingViewModel viewModel, ActivitySettingBinding binding) {
        this.viewModel = viewModel;

        findViewById(R.id.rlPassword).setOnClickListener(this);
        findViewById(R.id.tvLogout).setOnClickListener(this);
        findViewById(R.id.imvBack).setOnClickListener(this);
        findViewById(R.id.rlNotification).setOnClickListener(this);
        tvEmail = findViewById(R.id.tvEmail);
        tvEmail.setText(PreferUtils.getEmail(this));
        viewModel.logout().observe(this, jsonElementRestData ->
                {
                    hideProgressDialog();
                    if (jsonElementRestData.status_code == 200){
                        PreferUtils.setToken(this,"");
                        Intent intent = new Intent(SettingActivity.this,LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    } else {
                        Toaster.longToast(jsonElementRestData.message);
                    }
                }
        );

        viewModel.getSupport().observe(this, supportRestData -> {
            if(supportRestData != null && supportRestData.data != null){
                Support support = supportRestData.data;
                binding.tvSupport.setText(support.getBuilding_techniques());
                binding.tvItSupport.setText(support.getSupport_it());
                binding.tvCofee.setText(support.getBlackbox_coffee());
            }
        });


    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_setting;
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, SettingActivity.class);
        context.startActivity(starter);
    }

    private void logout(){
        showProgressDialog();
        QBUsers.signOut().performAsync(new QBEntityCallback<Void>() {
            @Override
            public void onSuccess(Void aVoid, Bundle bundle) {
                viewModel.setRequest(PreferUtils.getToken(SettingActivity.this));
            }

            @Override
            public void onError(QBResponseException e) {
                showProgressDialog();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rlPassword:
                SettingChangePwActivity.start(this);
                break;
            case R.id.tvLogout:
                logout();
                break;
            case R.id.imvBack:
                onBackPressed();
                break;
            case R.id.rlNotification:
                NotificationActivity.start(this);
                break;

        }
    }
}
