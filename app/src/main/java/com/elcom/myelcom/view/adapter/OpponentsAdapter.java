package com.elcom.myelcom.view.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.elcom.myelcom.R;
import com.elcom.myelcom.util.ResourceUtils;
import com.elcom.myelcom.util.UiUtils;
import com.quickblox.users.model.QBUser;

import java.util.List;

/**
 * QuickBlox team
 */
public class OpponentsAdapter extends BaseSelectableListAdapter<QBUser> {

    private SelectedItemsCountsChangedListener selectedItemsCountChangedListener;

    public OpponentsAdapter(Context context, List<QBUser> users) {
        super(context, users);
    }

    public View getView(final int position, View convertView, final ViewGroup parent) {

        final ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_opponents_list, null);
            holder = new ViewHolder();
            holder.opponentIcon = (ImageView) convertView.findViewById(R.id.image_opponent_icon);
            holder.opponentName = (TextView) convertView.findViewById(R.id.opponentsName);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final QBUser user = getItem(position);

        if (user != null) {
            holder.opponentName.setText(user.getLogin());

            if (selectedItems.contains(user)){
                convertView.setBackgroundResource(R.color.background_color_selected_user_item);
                holder.opponentIcon.setBackgroundDrawable(
                        UiUtils.getColoredCircleDrawable(ResourceUtils.getColor(R.color.icon_background_color_selected_user)));
                holder.opponentIcon.setImageResource(R.drawable.ic_home_black_24dp);
            } else {
                convertView.setBackgroundResource(R.color.background_color_normal_user_item);
                holder.opponentIcon.setBackgroundDrawable(UiUtils.getColorCircleDrawable(user.getId()));
                holder.opponentIcon.setImageResource(R.drawable.ic_call);
            }
        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleSelection(position);
                selectedItemsCountChangedListener.onCountSelectedItemsChanged(selectedItems.size());
            }
        });

        return convertView;
    }

    public static class ViewHolder {
        ImageView opponentIcon;
        TextView opponentName;
    }

    public void setSelectedItemsCountsChangedListener(SelectedItemsCountsChangedListener selectedItemsCountsChanged){
        if (selectedItemsCountsChanged != null) {
            this.selectedItemsCountChangedListener = selectedItemsCountsChanged;
        }
    }

    public interface SelectedItemsCountsChangedListener{
        void onCountSelectedItemsChanged(int count);
    }
}
