package com.elcom.hailpt.view.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageView;

import com.elcom.hailpt.R;
import com.elcom.hailpt.core.base.BaseActivity;
import com.elcom.hailpt.databinding.ActivityNotificationBinding;
import com.elcom.hailpt.view.adapter.NotificationAdapter;
import com.elcom.hailpt.viewmodel.NotificationViewModel;

public class NotificationActivity extends BaseActivity<NotificationViewModel,ActivityNotificationBinding> {


    private NotificationAdapter notificationAdapter;

    @Override
    protected Class<NotificationViewModel> getViewModel() {
        return NotificationViewModel.class;
    }

    @Override
    protected void onCreate(Bundle instance, NotificationViewModel viewModel, ActivityNotificationBinding binding) {
        notificationAdapter = new NotificationAdapter();
        binding.recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, OrientationHelper.VERTICAL));
        binding.recyclerView.setAdapter(notificationAdapter);
        findViewById(R.id.imvBack).setOnClickListener(v -> onBackPressed());
        init(viewModel);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_notification;
    }

    private void init(NotificationViewModel viewModel){
        viewModel.getAllNotification().observe(this, listRestData -> {
            if(listRestData != null && listRestData.data != null){
                notificationAdapter.setData(listRestData.data);
            }
        });
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, NotificationActivity.class);
        context.startActivity(starter);
    }
}
