package com.elcom.hailpt.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.elcom.hailpt.R;
import com.elcom.hailpt.model.api.response.Notification;
import com.elcom.hailpt.util.DateTimeUtils;

import java.util.List;

/**
 * Created by Hailpt on 7/12/2018.
 */

public class NotifyAdapter extends RecyclerView.Adapter<NotifyAdapter.MyViewHolder> {

    List<Notification>  moviesList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, des;
        public ImageView imvAva;
        public RelativeLayout viewBackground;
        public LinearLayout viewForeground;
        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.tvNotiName);
            des = (TextView) view.findViewById(R.id.tvNoTiDes);
            imvAva =  view.findViewById(R.id.imvAva);
            viewForeground =  view.findViewById(R.id.viewForeground);
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
        holder.title.setText(movie.getMessage().toString());
        holder.des.setText(DateTimeUtils.convertLongToTimeDateYear(Integer.parseInt(movie.getCreatedAt().toString())*1000+""));

        if (movie.getCategory() == 1)
            // 1 Cham cong
            // 2 An trua
            // 3 lich hop
            switch (movie.getCategory()){
                case 1:
                    holder.imvAva.setImageResource(R.drawable.notify_time_ic);
                    break;
                case 2:
                    holder.imvAva.setImageResource(R.drawable.plate_x_ic);
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

    public void removeItem(int position) {
        moviesList.remove(position);
        // notify the item removed by position
        // to perform recycler view delete animations
        // NOTE: don't call notifyDataSetChanged()
        notifyItemRemoved(position);
    }

    public void restoreItem(Notification item, int position) {
        moviesList.add(position, item);
        // notify item added by position
        notifyItemInserted(position);
    }
}