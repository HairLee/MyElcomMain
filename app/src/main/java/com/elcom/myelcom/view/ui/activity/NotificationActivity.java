package com.elcom.myelcom.view.ui.activity;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.widget.TextView;

import com.elcom.myelcom.R;
import com.elcom.myelcom.core.base.BaseActivity;
import com.elcom.myelcom.databinding.ActivityNotificationBinding;
import com.elcom.myelcom.model.api.RestData;
import com.elcom.myelcom.model.api.request.RemoveNotificationReq;
import com.elcom.myelcom.model.api.response.Notification;
import com.elcom.myelcom.util.RecyclerItemTouchHelper;
import com.elcom.myelcom.view.adapter.NotifyAdapter;
import com.elcom.myelcom.viewmodel.NotificationViewModel;
import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.List;

public class NotificationActivity extends BaseActivity<NotificationViewModel,ActivityNotificationBinding> implements RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {


    private NotifyAdapter notificationAdapter;
    private TextView tvTitle;
    List<Notification> data  = new ArrayList<>();
    private ActivityNotificationBinding binding;
    private NotificationViewModel notificationViewModel;
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
        notificationViewModel = viewModel;
        viewModel.getAllNotification().observe(this, listRestData -> {
            if(listRestData != null && listRestData.data != null){
                data = listRestData.data;
                notificationAdapter = new NotifyAdapter(data);

                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                binding.recyclerView.setLayoutManager(mLayoutManager);
                binding.recyclerView.setItemAnimator(new DefaultItemAnimator());
                binding.recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
                binding.recyclerView.setAdapter(notificationAdapter);

                ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
                new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView( binding.recyclerView);
            }
        });


        viewModel.getRemoveNotificationRq().observe(this, jsonElementRestData -> {



        });

        viewModel.getViewNotificationRq().observe(this, new Observer<RestData<JsonElement>>() {
            @Override
            public void onChanged(@Nullable RestData<JsonElement> jsonElementRestData) {
                Log.e("hailpt"," jsonElementRestData "+jsonElementRestData);
            }
        });


    }

    public static void start(Context context) {
        Intent starter = new Intent(context, NotificationActivity.class);
        context.startActivity(starter);
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof NotifyAdapter.MyViewHolder) {
            // get the removed item name to display it in snack bar
            String name = data.get(viewHolder.getAdapterPosition()).getName();
            final Notification deletedItem = data.get(viewHolder.getAdapterPosition());
            final int deletedIndex = viewHolder.getAdapterPosition();
            notificationAdapter.removeItem(viewHolder.getAdapterPosition());
            RemoveNotificationReq removeNotificationReq = new RemoveNotificationReq();
            removeNotificationReq.setId(deletedItem.getId().toString());

            notificationViewModel.setRemoveNotificationRq(removeNotificationReq);

        }
    }
}
