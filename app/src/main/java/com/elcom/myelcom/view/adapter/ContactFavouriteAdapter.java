package com.elcom.myelcom.view.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.elcom.myelcom.BR;
import com.elcom.myelcom.R;
import com.elcom.myelcom.core.listener.ChatAndCallListener;
import com.elcom.myelcom.model.api.response.User;
import com.elcom.myelcom.model.data.Article;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author ihsan on 12/19/17.
 */

public class ContactFavouriteAdapter extends RecyclerView.Adapter<ContactFavouriteAdapter.ViewHolder>  implements Filterable {

    private List<User> data;
    private List<User> usertList;
    private ItemSelectedListener listener;
    private ChatAndCallListener chatAndCallListener;
    private Context context;
    private CircleImageView circleImageView;
    public ContactFavouriteAdapter(List<User> data) {
        this.data = data;
    }

    public ContactFavouriteAdapter(Context context) {
        this.context = context;
        data = new ArrayList<>();
        usertList = new ArrayList<>();
    }

    public void setData(List<User> data) {
        this.data.clear();
        this.usertList.clear();
        this.data.addAll(data);
        this.usertList.addAll(data);
        notifyDataSetChanged();

        Log.e("hailpt"," ~~~ ContactFavouriteAdapter ~~~"+this.data.size());
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

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    data = usertList;
                } else {
                    List<User> filteredList = new ArrayList<>();
                    for (User row : usertList) {
                        if (row.getName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }
                    data = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = data;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                data = (ArrayList<User>) filterResults.values;
                notifyDataSetChanged();
            }
        };
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
            CircleImageView imvAva =  binding.getRoot().findViewById(R.id.imageView2);
            imvChat.setOnClickListener(v -> chatAndCallListener.doChat(data));

            ImageView imageView15 = binding.getRoot().findViewById(R.id.imageView15);

            if (data.getStatus() == 1){
                imageView15.setVisibility(View.VISIBLE);
            } else {
                imageView15.setVisibility(View.GONE);
            }

            if (data.getAvatar() != null){
                Glide.with(context).load(data.getAvatar())
                        .thumbnail(0.5f)
                        .into(imvAva);
            } else {
                imvAva.setImageResource(R.drawable.defaul_ava);
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
