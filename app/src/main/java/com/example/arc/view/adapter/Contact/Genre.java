package com.example.arc.view.adapter.Contact;

import com.example.arc.model.api.response.Contact;
import com.example.arc.model.api.response.User;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

public class Genre extends ExpandableGroup<User> {

  private int iconResId;

  public Genre(String title, List<User> items, int iconResId) {
    super(title, items);
    this.iconResId = iconResId;
  }

  public int getIconResId() {
    return iconResId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Genre)) return false;

    Genre genre = (Genre) o;

    return getIconResId() == genre.getIconResId();

  }

  @Override
  public int hashCode() {
    return getIconResId();
  }
}

