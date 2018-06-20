package com.example.arc.view.adapter.Contact;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.arc.R;
import com.example.arc.core.listener.AllContactFragmentListener;
import com.example.arc.model.api.response.User;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

public class ArtistViewHolder extends ChildViewHolder {

  private TextView childTextView;
  private ImageView imageView3;
  private AllContactFragmentListener allContactFragmentListener;
  private User user;
  public ArtistViewHolder(View itemView) {
    super(itemView);
    childTextView = (TextView) itemView.findViewById(R.id.list_item_artist_name);
    imageView3 = (ImageView) itemView.findViewById(R.id.imageView3);

    imageView3.setOnClickListener(v -> Log.e("hailpt"," ~~~~~Call~~~~~"));

    childTextView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        allContactFragmentListener.onViewProfile(imageView3,user.getId());
      }
    });
  }

  public void setArtistName(String name, User user, Context context) {
    this.user = user;
    childTextView.setText(name);
    if (user.getAvatar() != null){
      Glide.with(context).load(user.getAvatar())
              .thumbnail(0.5f)
              .into(imageView3);
    }
  }

  public void setAllContactFragmentListener(AllContactFragmentListener allContactFragmentListener){
    this.allContactFragmentListener = allContactFragmentListener;
  }
}
