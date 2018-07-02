package com.elcom.hailpt.view.adapter.Contact;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.elcom.hailpt.R;
import com.elcom.hailpt.core.listener.AllContactFragmentListener;
import com.elcom.hailpt.model.api.response.Contact;
import com.elcom.hailpt.model.api.response.User;
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;


import java.util.List;

public class GenreAdapter extends ExpandableRecyclerViewAdapter<GenreViewHolder, ArtistViewHolder> {


  private AllContactFragmentListener allContactFragmentListener;
  private Context context;
  public GenreAdapter(List<? extends ExpandableGroup> groups,Context context) {
    super(groups);
    this.context = context;
  }

  @Override
  public GenreViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.list_item_genre, parent, false);
    return new GenreViewHolder(view);
  }

  @Override
  public ArtistViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.contact_all_item_layout, parent, false);
    return new ArtistViewHolder(view);
  }

  @Override
  public void onBindChildViewHolder(ArtistViewHolder holder, int flatPosition,
      ExpandableGroup group, int childIndex) {

    final User user = ((Genre) group).getItems().get(childIndex);
    holder.setArtistName(user.getName(),user,context);
    holder.setAllContactFragmentListener(allContactFragmentListener);
  }

  @Override
  public void onBindGroupViewHolder(GenreViewHolder holder, int flatPosition,
      ExpandableGroup group) {

    holder.setGenreTitle(group);
  }

  public void setAllContactFragmentListener(AllContactFragmentListener allContactFragmentListener){
    this.allContactFragmentListener = allContactFragmentListener;
  }
}
