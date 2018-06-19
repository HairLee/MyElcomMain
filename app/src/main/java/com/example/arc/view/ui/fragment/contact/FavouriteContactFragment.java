package com.example.arc.view.ui.fragment.contact;


import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.StaggeredGridLayoutManager;
import com.example.arc.R;
import com.example.arc.core.base.BaseFragment;
import com.example.arc.model.api.RestData;
import com.example.arc.model.api.response.Contact;
import com.example.arc.model.api.response.User;
import com.example.arc.view.adapter.ContactFavouriteAdapter;
import com.example.arc.viewmodel.ContactFavouriteViewModel;
import android.support.v7.widget.OrientationHelper;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavouriteContactFragment extends BaseFragment<ContactFavouriteViewModel> {


    ContactFavouriteViewModel contactFavouriteViewModel;
    ContactFavouriteAdapter contactFavouriteAdapter;
    public FavouriteContactFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favourite_contact, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        contactFavouriteAdapter = new ContactFavouriteAdapter();
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, OrientationHelper.VERTICAL));
        recyclerView.setAdapter(contactFavouriteAdapter);

        init();

        return view;
    }

    @Override
    protected Class<ContactFavouriteViewModel> getViewModel() {
        return ContactFavouriteViewModel.class;
    }

    @Override
    protected void onCreate(Bundle instance, ContactFavouriteViewModel viewModel) {
        contactFavouriteViewModel = viewModel;
    }

    private void init(){
        contactFavouriteViewModel.getFavouriteContact().observe(this, new Observer<RestData<List<User>>>() {
            @Override
            public void onChanged(@Nullable RestData<List<User>> listRestData) {
                if(listRestData != null){
                    contactFavouriteAdapter.setData(listRestData.data);
                }
            }
        });
    }

}
