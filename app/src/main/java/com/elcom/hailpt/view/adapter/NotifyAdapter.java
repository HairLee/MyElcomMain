package com.elcom.hailpt.view.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.elcom.hailpt.R;
import com.elcom.hailpt.model.api.response.Notification;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hailpt on 7/12/2018.
 */

public class NotifyAdapter extends RecyclerView.Adapter<NotifyAdapter.MyViewHolder> {

    List<Notification>  moviesList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, des;
        public ImageView imvAva;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.tvNotiName);
            des = (TextView) view.findViewById(R.id.tvNoTiDes);
            imvAva =  view.findViewById(R.id.imvAva);
        }
    }


    public NotifyAdapter(List<Notification> moviesList) {
        this.moviesList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notify_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Notification movie = moviesList.get(position);
        holder.title.setText(movie.getName().toString());
        holder.des.setText(movie.getMessage().toString());

        if (movie.getCategory() == 1)
            // 1 Cham cong
            // 2 An trua
            // 3 lich hop
            switch (movie.getCategory()){
                case 1:
                    holder.imvAva.setImageResource(R.drawable.notify_time_ic);
                    break;
                case 2:
                    holder.imvAva.setImageResource(R.drawable.plate_ic);
                    break;
                case 3:
                    holder.imvAva.setImageResource(R.drawable.notify_calander_ic);
                    break;
            }
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}