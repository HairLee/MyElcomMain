package com.elcom.hailpt.view.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.elcom.hailpt.BR;
import com.elcom.hailpt.R;
import com.elcom.hailpt.core.listener.ChatAndCallListener;
import com.elcom.hailpt.model.api.response.Contact;
import com.elcom.hailpt.model.api.response.User;
import com.elcom.hailpt.model.data.Article;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ihsan on 12/19/17.
 */

public class ContactFavouriteAdapter extends RecyclerView.Adapter<ContactFavouriteAdapter.ViewHolder> {

    private List<User> data;
    private ItemSelectedListener listener;
    private ChatAndCallListener chatAndCallListener;

    public ContactFavouriteAdapter(List<User> data) {
        this.data = data;
    }

    public ContactFavouriteAdapter() {
        data = new ArrayList<>();
    }

    public void setData(List<User> data) {
        this.data.clear();
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    public List<User> getData() {
        return data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewDataBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()), R.layout.contact_favourite_item_layout, parent, false);



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

    public void setChatAndCallListener(ChatAndCallListener chatAndCallListener) {
        this.chatAndCallListener = chatAndCallListener;
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

        void bind(User data) {
            binding.setVariable(BR.user, data);
            binding.executePendingBindings();

            ImageView imvCall =  binding.getRoot().findViewById(R.id.imageView3);
            imvCall.setOnClickListener(v -> chatAndCallListener.doCall(data));

            ImageView imvChat =  binding.getRoot().findViewById(R.id.imageView4);
            imvChat.setOnClickListener(v -> chatAndCallListener.doChat(data));

            ImageView imageView15 = binding.getRoot().findViewById(R.id.imageView15);

            if (data.getStatus() == 1){
                imageView15.setVisibility(View.VISIBLE);
            } else {
                imageView15.setVisibility(View.GONE);
            }

        }

        @Override
        public void onClick(View view) {
            if (listener != null) {
//                listener.onItemSelected(view, data.get(getAdapterPosition()));
            }
        }
    }
}
