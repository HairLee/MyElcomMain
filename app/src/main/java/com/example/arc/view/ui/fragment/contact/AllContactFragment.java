package com.example.arc.view.ui.fragment.contact;


import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.arc.R;
import com.example.arc.core.base.BaseFragment;
import com.example.arc.model.api.RestData;
import com.example.arc.model.api.response.Contact;
import com.example.arc.model.api.response.ContactSuggest;
import com.example.arc.model.api.response.User;
import com.example.arc.view.adapter.AllContactAdapter;
import com.example.arc.view.adapter.AllContactSuggestAdapter;
import com.example.arc.view.adapter.Contact.Genre;
import com.example.arc.view.adapter.Contact.GenreAdapter;
import com.example.arc.viewmodel.AllContactSuggestViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllContactFragment extends BaseFragment<AllContactSuggestViewModel> {



    AllContactSuggestViewModel allContactSuggestViewModel;
    private AllContactSuggestAdapter allContactSuggestAdaprer;
    private AllContactAdapter allContactAdaprer;
    //    private List<ContactSuggest> data = new ArrayList<>();
    private List<Contact> contacts = new ArrayList<>();
    private   RecyclerView recyclerView;
    public AllContactFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_contact, container, false);
        // Inflate the layout for this fragment
//        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
//        RecyclerView recyclerViewBottom = view.findViewById(R.id.recyclerViewBottom);
//
//        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, OrientationHelper.VERTICAL));
//        recyclerViewBottom.setLayoutManager(new StaggeredGridLayoutManager(1, OrientationHelper.VERTICAL));
//
//        allContactSuggestAdaprer = new AllContactSuggestAdapter();
//        allContactAdaprer = new AllContactAdapter();
//
//        recyclerView.setAdapter(allContactSuggestAdaprer);
//        recyclerViewBottom.setAdapter(allContactAdaprer);
//
//        Log.e("hailpt"," AllContactFragment onCreateView ");
//        showProgressDialog(R.string.dlg_loading1);
        recyclerView = view.findViewById(R.id.recyclerViewBottom);
        setupViewTest();
        return view;
    }

    @Override
    protected Class<AllContactSuggestViewModel> getViewModel() {
        return AllContactSuggestViewModel.class;
    }

    @Override
    protected void onCreate(Bundle instance, AllContactSuggestViewModel viewModel) {
        allContactSuggestViewModel = viewModel;
        allContactSuggestViewModel.getAllContactSuggest().observe(this, contactSuggestRestData -> {
            Log.e("hailpt"," AllContactFragment onCreate ");
            if(contactSuggestRestData != null){
//                allContactSuggestAdaprer.setData(contactSuggestRestData.data);
            }

        });

        allContactSuggestViewModel.getAllContact().observe(this, listRestData -> {
//            allContactAdaprer.setData(listRestData.data);
//            hideProgressDialog();
            contacts = listRestData.data;
            setupViewTest();
        });
    }


    private void setupViewTest(){

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());

        // RecyclerView has some built in animations to it, using the DefaultItemAnimator.
        // Specifically when you call notifyItemChanged() it does a fade animation for the changing
        // of the data in the ViewHolder. If you would like to disable this you can use the following:
        RecyclerView.ItemAnimator animator = recyclerView.getItemAnimator();
        if (animator instanceof DefaultItemAnimator) {
            ((DefaultItemAnimator) animator).setSupportsChangeAnimations(false);
        }

        GenreAdapter adapter = new GenreAdapter(makeGenres());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    public List<Genre> makeGenres() {
        List<Genre> genres = new ArrayList<>();
        for (int i = 0; i < contacts.size(); i++) {
            Genre genre = new Genre(contacts.get(i).getGroupName(), contacts.get(i).getUsers(),R.drawable.expand_down_ic);
            genres.add(genre);
        }
        return genres;
    }
}
