package com.elcom.hailpt.view.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.elcom.hailpt.R;
import com.elcom.hailpt.core.base.BaseActivity;
import com.elcom.hailpt.databinding.ActivityNotificationBinding;
import com.elcom.hailpt.model.api.response.Notification;
import com.elcom.hailpt.view.adapter.NotificationAdapter;
import com.elcom.hailpt.view.adapter.NotifyAdapter;
import com.elcom.hailpt.viewmodel.NotificationViewModel;

import java.util.ArrayList;
import java.util.List;

public class NotificationActivity extends BaseActivity<NotificationViewModel,ActivityNotificationBinding> {


    private NotifyAdapter notificationAdapter;
    private TextView tvTitle;
    List<Notification> data  = new ArrayList<>();
    private ActivityNotificationBinding binding;
    @Override
    protected Class<NotificationViewModel> getViewModel() {
        return NotificationViewModel.class;
    }

    @Override
    protected void onCreate(Bundle instance, NotificationViewModel viewModel, ActivityNotificationBinding binding) {
        this.binding = binding;
        notificationAdapter = new NotifyAdapter(data);
        binding.recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, OrientationHelper.VERTICAL));
        binding.recyclerView.setAdapter(notificationAdapter);
        tvTitle = findViewById(R.id.tvTitle);
        findViewById(R.id.imvBack).setOnClickListener(v -> onBackPressed());
        tvTitle.setText("Thông báo");
        init(viewModel);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_notification;
    }

    private void init(NotificationViewModel viewModel){
        viewModel.getAllNotification().observe(this, listRestData -> {
            if(listRestData != null && listRestData.data != null){
                data = listRestData.data;
                notificationAdapter = new NotifyAdapter(data);
                binding.recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, OrientationHelper.VERTICAL));
                binding.recyclerView.setAdapter(notificationAdapter);
            }
        });


    }

    public static void start(Context context) {
        Intent starter = new Intent(context, NotificationActivity.class);
        context.startActivity(starter);
    }
}
