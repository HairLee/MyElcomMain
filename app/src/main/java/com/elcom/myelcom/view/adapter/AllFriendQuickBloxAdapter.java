package com.elcom.myelcom.view.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.elcom.myelcom.BR;
import com.elcom.myelcom.R;
import com.quickblox.users.model.QBUser;

import java.util.ArrayList;

/**
 * @author ihsan on 12/19/17.
 */

public class AllFriendQuickBloxAdapter extends RecyclerView.Adapter<AllFriendQuickBloxAdapter.ViewHolder> {

    private ArrayList<QBUser> data;
    private ItemSelectedListener listener;

    public AllFriendQuickBloxAdapter() {
        data = new ArrayList<>();
    }

    public void setData(ArrayList<QBUser> data) {
        this.data.clear();
        this.data.addAll(data);
        notifyDataSetChanged();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewDataBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()), R.layout.item_all_friend_item, parent, false);
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
        void onItemSelected(View view, QBUser item);
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

        void bind(QBUser data) {

            Log.e("hailpt"," ViewHolder getEmail " +data.getFullName());

            binding.setVariable(BR.user, data);
            binding.executePendingBindings();
        }

        @Override
        public void onClick(View view) {
            if (listener != null) {
                listener.onItemSelected(view, data.get(getAdapterPosition()));
            }
        }
    }
}
