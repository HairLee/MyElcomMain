package com.elcom.hailpt.view.adapter.Contact;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.elcom.hailpt.R;
import com.elcom.hailpt.core.listener.AllContactFragmentListener;
import com.elcom.hailpt.model.api.response.User;
import com.elcom.hailpt.util.PreferUtils;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

public class ArtistViewHolder extends ChildViewHolder {

  private TextView childTextView;
  private ImageView imageView3, imageView4,imvAva,imageView16;
  private AllContactFragmentListener allContactFragmentListener;
  private User user;
  public ArtistViewHolder(View itemView) {
    super(itemView);
    childTextView = (TextView) itemView.findViewById(R.id.list_item_artist_name);
    imageView3 = (ImageView) itemView.findViewById(R.id.imageView3);
    imageView4 = (ImageView) itemView.findViewById(R.id.imageView4);
    imageView16 = (ImageView) itemView.findViewById(R.id.imageView16);
    imvAva = (ImageView) itemView.findViewById(R.id.imageView2);
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

    if (user.getStatus() == 1){
      imageView16.setVisibility(View.VISIBLE);
    } else {
      imageView16.setVisibility(View.GONE);
    }

    if (user.getId() == PreferUtils.getUserId(context)){
      imageView3.setVisibility(View.GONE);
      imageView4.setVisibility(View.GONE);
    } else {
      imageView3.setVisibility(View.VISIBLE);
      imageView4.setVisibility(View.VISIBLE);
    }

  }

  public void setAllContactFragmentListener(AllContactFragmentListener allContactFragmentListener){
    this.allContactFragmentListener = allContactFragmentListener;
  }
}
