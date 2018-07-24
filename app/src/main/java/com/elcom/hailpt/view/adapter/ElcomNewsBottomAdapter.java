package com.elcom.hailpt.view.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.elcom.hailpt.BR;
import com.elcom.hailpt.R;
import com.elcom.hailpt.model.api.response.News;
import com.elcom.hailpt.model.api.response.NewsNormal;
import com.elcom.hailpt.model.data.Article;
import com.elcom.hailpt.util.DateTimeUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ihsan on 12/19/17.
 */

public class ElcomNewsBottomAdapter extends RecyclerView.Adapter<ElcomNewsBottomAdapter.ViewHolder> {

    private ArrayList<NewsNormal> data;
    private ItemSelectedListener listener;
    private ElcomNewsChildBottomAdapter.ItemSelectedListener childListener;
    private Context context;
    public ElcomNewsBottomAdapter(Context context) {
        this.context = context;
        data = new ArrayList<>();
    }

    public void setData(List<NewsNormal> data) {
        this.data.clear();
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    public ArrayList<NewsNormal> getData() {
        return data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewDataBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()), R.layout.elcom_news_bottom_item, parent, false);
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
        void onItemSelected(View view, NewsNormal item);
    }

    public void setOnItemClickListener(ElcomNewsChildBottomAdapter.ItemSelectedListener listener) {
        this.childListener = listener;
    }

    public void setOnItemChildClickListener(ItemSelectedListener listener) {
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

        void bind(NewsNormal data) {
            binding.setVariable(BR.newsnormal, data);
            binding.executePendingBindings();

            TextView tvTitle = binding.getRoot().findViewById(R.id.tvTitle);
            TextView tvViewAll = binding.getRoot().findViewById(R.id.tvViewAll);
            tvTitle.setText(data.getCategory_name());
            RecyclerView recyclerView = binding.getRoot().findViewById(R.id.recyclerView);

            ElcomNewsChildBottomAdapter elcomNewsChildBottomAdapter = new ElcomNewsChildBottomAdapter(context);
            elcomNewsChildBottomAdapter.setData(data.getArticles());
            elcomNewsChildBottomAdapter.setOnItemClickListener(childListener);
            recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, OrientationHelper.VERTICAL));
            recyclerView.setAdapter(elcomNewsChildBottomAdapter);

            tvViewAll.setOnClickListener(v -> listener.onItemSelected(v,data));
//            binding.getRoot().setOnClickListener(v,data);
        }

        @Override
        public void onClick(View view) {
            if (listener != null) {
                listener.onItemSelected(view, data.get(getAdapterPosition()));
            }
        }
    }
}
