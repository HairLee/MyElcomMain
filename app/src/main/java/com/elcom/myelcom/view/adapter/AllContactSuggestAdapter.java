package com.elcom.myelcom.view.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.elcom.myelcom.BR;
import com.elcom.myelcom.R;
import com.elcom.myelcom.model.api.response.ContactSuggest;
import com.elcom.myelcom.model.data.Article;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ihsan on 12/19/17.
 */

public class AllContactSuggestAdapter extends RecyclerView.Adapter<AllContactSuggestAdapter.ViewHolder> {

    private List<ContactSuggest> data;
    private ItemSelectedListener listener;

    public AllContactSuggestAdapter(List<ContactSuggest> data) {
        this.data = data;
    }

    public AllContactSuggestAdapter() {
        data = new ArrayList<>();
    }

    public void setData(List<ContactSuggest> data) {
        this.data.clear();
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    public List<ContactSuggest> getData() {
        return data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewDataBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()), R.layout.contact_all_suggest_item_layout, parent, false);
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

        void bind(ContactSuggest data) {
            binding.setVariable(BR.article, data);
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
