package com.example.arc.view.ui.activity;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.arc.R;
import com.example.arc.core.base.BaseActivity;
import com.example.arc.databinding.ActivitySettingBinding;
import com.example.arc.model.api.RestData;
import com.example.arc.util.Consts;
import com.example.arc.util.Toaster;
import com.example.arc.viewmodel.SettingViewModel;
import com.google.gson.JsonElement;

public class SettingActivity extends BaseActivity<SettingViewModel,ActivitySettingBinding> implements View.OnClickListener {


    private  SettingViewModel viewModel;
    @Override
    protected Class<SettingViewModel> getViewModel() {
        return SettingViewModel.class;
    }

    @Override
    protected void onCreate(Bundle instance, SettingViewModel viewModel, ActivitySettingBinding binding) {
        this.viewModel = viewModel;

        findViewById(R.id.rlPassword).setOnClickListener(this);
        findViewById(R.id.tvLogout).setOnClickListener(this);

        viewModel.logout().observe(this, jsonElementRestData -> Toaster.longToast(jsonElementRestData.message));
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_setting;
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, SettingActivity.class);
        context.startActivity(starter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rlPassword:
                SettingChangePwActivity.start(this);
                break;
            case  R.id.tvLogout:
                viewModel.setRequest("Logout");
                break;
        }
    }
}
