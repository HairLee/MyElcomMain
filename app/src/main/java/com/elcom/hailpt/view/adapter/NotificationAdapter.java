package com.elcom.hailpt.view.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.elcom.hailpt.BR;
import com.elcom.hailpt.R;
import com.elcom.hailpt.model.api.response.Notification;
import com.elcom.hailpt.model.data.Article;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ihsan on 12/19/17.
 */

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    private ArrayList<Notification> data;
    private ItemSelectedListener listener;
    // 1 Cham cong
    // 2 An trua
    // 3 lich hop
    public NotificationAdapter() {
        data = new ArrayList<>();
    }

    public void setData(List<Notification> data) {
        this.data.clear();
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    public ArrayList<Notification> getData() {
        return data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewDataBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()), R.layout.notification_item, parent, false);
        return new ViewHolder(binding, listener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public interface ItemSelectedListener {
        void onItemSelected(View view, Article item);
    }

    public void setOnItemClickListener(ItemSelectedListener listener) {
        this.listener = listener;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final ViewDataBinding binding;
        private final ItemSelectedListener listener;

        ViewHolder(ViewDataBinding binding, ItemSelectedListener listener) {
            super(binding.getRoot());
            this.binding = binding;
            this.listener = listener;
            binding.getRoot().setOnClickListener(this);
        }

        void bind(Notification data) {
            binding.setVariable(BR.notification, data);
            binding.executePendingBindings();
        }

        @Override
        public void onClick(View view) {
            if (listener != null) {
//                listener.onItemSelected(view, data.get(getAdapterPosition()));
            }
        }
    }
}
