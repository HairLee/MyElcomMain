package com.elcom.myelcom.view.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.elcom.myelcom.BR;
import com.elcom.myelcom.R;
import com.elcom.myelcom.model.api.response.News;
import com.elcom.myelcom.util.DateTimeUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ihsan on 12/19/17.
 */

public class ElcomNewsChildBottomAdapter extends RecyclerView.Adapter<ElcomNewsChildBottomAdapter.ViewHolder> {

    private ArrayList<News> data;
    private ItemChildSelectedListener listener;
    private Context context;
    public ElcomNewsChildBottomAdapter(Context context) {
        this.context = context;
        data = new ArrayList<>();
    }

    public void setData(List<News> data) {
        this.data.clear();
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    public ArrayList<News> getData() {
        return data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewDataBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()), R.layout.elcom_news_child_bottom_item, parent, false);
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

    public interface ItemChildSelectedListener {
        void onItemChildSelected(View view, News item);
    }

    public void setOnItemClickListener(ItemChildSelectedListener listener) {
        this.listener = listener;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final ViewDataBinding binding;
        private final ItemChildSelectedListener listener;

        ViewHolder(ViewDataBinding binding, ItemChildSelectedListener listener) {
            super(binding.getRoot());
            this.binding = binding;
            this.listener = listener;
            binding.getRoot().setOnClickListener(this);
        }

        void bind(News data) {
            binding.setVariable(BR.news, data);
            binding.executePendingBindings();

            ImageView imvDes = binding.getRoot().findViewById(R.id.imvNews);
            TextView tvTime = binding.getRoot().findViewById(R.id.tvTime);
            tvTime.setText(DateTimeUtils.getTime(Long.parseLong(data.getCreatedAt().toString())*1000));

            if(!data.getThumbnail().equals("")){
                Glide.with(context).load(data.getThumbnail())
                        .thumbnail(0.5f)
                        .into(imvDes);
            }
        }

        @Override
        public void onClick(View view) {
            if (listener != null) {
                listener.onItemChildSelected(view, data.get(getAdapterPosition()));
            }
        }
    }
}
