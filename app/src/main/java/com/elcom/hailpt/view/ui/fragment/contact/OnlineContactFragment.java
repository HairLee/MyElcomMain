package com.elcom.hailpt.view.ui.fragment.contact;


import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.elcom.hailpt.R;
import com.elcom.hailpt.core.base.BaseFragment;
import com.elcom.hailpt.core.listener.ChatAndCallListener;
import com.elcom.hailpt.model.api.RestData;
import com.elcom.hailpt.model.api.response.User;
import com.elcom.hailpt.util.ProgressDialogUtils;
import com.elcom.hailpt.view.adapter.ContactFavouriteAdapter;
import com.elcom.hailpt.viewmodel.ContactOnlineViewModel;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class OnlineContactFragment extends BaseFragment<ContactOnlineViewModel>  implements ChatAndCallListener {


    ContactOnlineViewModel contactOnlineViewModel;
    ContactFavouriteAdapter contactFavouriteAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    public OnlineContactFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_online_contact, container, false);
        contactFavouriteAdapter = new ContactFavouriteAdapter();
        contactFavouriteAdapter.setChatAndCallListener(this);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, OrientationHelper.VERTICAL));
        recyclerView.setAdapter(contactFavouriteAdapter);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.color_blue_accent));
        swipeRefreshLayout.setOnRefreshListener(() -> {
            contactOnlineViewModel.setAllContactrequest();
        });
        init();
        return view;
    }

    @Override
    protected Class<ContactOnlineViewModel> getViewModel() {
        return ContactOnlineViewModel.class;
    }

    @Override
    protected void onCreate(Bundle instance, ContactOnlineViewModel viewModel) {
        contactOnlineViewModel = viewModel;
        contactOnlineViewModel.setAllContactrequest();
    }

    private void init() {
        ProgressDialogUtils.showProgressDialog(getContext(), 0, 0);
        contactOnlineViewModel.getOnlineContact().observe(this, new Observer<RestData<List<User>>>() {
            @Override
            public void onChanged(@Nullable RestData<List<User>> listRestData) {
                ProgressDialogUtils.dismissProgressDialog();
                if(listRestData != null){
                    contactFavouriteAdapter.setData(listRestData.data);
                    swipeRefreshLayout.setRefreshing(false);
                }

            }
        });
    }

    @Override
    public void doChat(User user) {

    }

    @Override
    public void doCall(User user) {

    }
}
