package com.example.arc.view.adapter.Contact;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.arc.R;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

public class ArtistViewHolder extends ChildViewHolder {

  private TextView childTextView;
  private ImageView imageView3;

  public ArtistViewHolder(View itemView) {
    super(itemView);
    childTextView = (TextView) itemView.findViewById(R.id.list_item_artist_name);
    imageView3 = (ImageView) itemView.findViewById(R.id.imageView3);

    imageView3.setOnClickListener(v -> Log.e("hailpt"," ~~~~~Call~~~~~"));
  }

  public void setArtistName(String name) {
    childTextView.setText(name);
  }
}
