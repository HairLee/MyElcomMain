package com.elcom.hailpt.view.adapter.Contact;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.elcom.hailpt.R;
import com.elcom.hailpt.core.listener.AllContactFragmentListener;
import com.elcom.hailpt.model.api.response.User;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

public class ArtistViewHolder extends ChildViewHolder {

  private TextView childTextView;
  private ImageView imageView3,imvAva;
  private AllContactFragmentListener allContactFragmentListener;
  private User user;
  public ArtistViewHolder(View itemView) {
    super(itemView);
    childTextView = (TextView) itemView.findViewById(R.id.list_item_artist_name);
    imageView3 = (ImageView) itemView.findViewById(R.id.imageView3);
    imvAva = (ImageView) itemView.findViewById(R.id.imageView2);

    imageView3.setOnClickListener(v -> allContactFragmentListener.onCallVideo(user));

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
              .into(imvAva);
    } else {
      imvAva.setImageResource(R.drawable.defaul_ava);
    }
  }

  public void setAllContactFragmentListener(AllContactFragmentListener allContactFragmentListener){
    this.allContactFragmentListener = allContactFragmentListener;
  }
}
