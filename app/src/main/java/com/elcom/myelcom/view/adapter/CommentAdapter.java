package com.elcom.myelcom.view.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.elcom.myelcom.BR;
import com.elcom.myelcom.R;
import com.elcom.myelcom.model.api.response.Comment;
import com.elcom.myelcom.model.data.Article;
import com.elcom.myelcom.util.DateTimeUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ihsan on 12/19/17.
 */

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {

    private ArrayList<Comment> data;
    private ItemSelectedListener listener;
    private Context context;

    public CommentAdapter(Context context) {
        this.context = context;
        data = new ArrayList<>();
    }

    public void setData(List<Comment> data) {
        this.data.clear();
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    public ArrayList<Comment> getData() {
        return data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewDataBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()), R.layout.news_comment_item, parent, false);
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

        void bind(Comment data) {
            binding.setVariable(BR.comment, data);
            binding.executePendingBindings();

            TextView tvNoTiDes = binding.getRoot().findViewById(R.id.tvNoTiDes);

            tvNoTiDes.setText(DateTimeUtils.getDateFromMillis(Long.parseLong(data.getCreatedAt().toString())*1000));

            de.hdodenhof.circleimageview.CircleImageView imvAva = binding.getRoot().findViewById(R.id.imvAva);
                Glide.with(context).load(data.getAvatar())
                        .into(imvAva);

        }

        @Override
        public void onClick(View view) {
            if (listener != null) {
//                listener.onItemSelected(view, data.get(getAdapterPosition()));
            }
        }
    }
}
