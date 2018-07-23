package com.elcom.hailpt.view.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
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

public class ContactFavouriteAdapter extends RecyclerView.Adapter<ContactFavouriteAdapter.ViewHolder>  implements Filterable {

    private List<User> data;
    private List<User> usertList;
    private ItemSelectedListener listener;
    private ChatAndCallListener chatAndCallListener;

    public ContactFavouriteAdapter(List<User> data) {
        this.data = data;
    }

    public ContactFavouriteAdapter() {
        data = new ArrayList<>();
        usertList = new ArrayList<>();
    }

    public void setData(List<User> data) {
        this.data.clear();
        this.data.addAll(data);
        this.usertList.addAll(data);
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
                    for (User row : data) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
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
