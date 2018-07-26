package com.elcom.myelcom.view.ui.fragment.news;


import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.elcom.myelcom.R;
import com.elcom.myelcom.core.base.BaseFragment;
import com.elcom.myelcom.model.api.RestData;
import com.elcom.myelcom.model.api.response.News;
import com.elcom.myelcom.model.api.response.NewsNormal;
import com.elcom.myelcom.model.api.response.NewsRes;
import com.elcom.myelcom.view.adapter.ElcomNewsAdapter;
import com.elcom.myelcom.view.adapter.ElcomNewsBottomAdapter;
import com.elcom.myelcom.view.adapter.ElcomNewsChildBottomAdapter;
import com.elcom.myelcom.viewmodel.NewsViewModel;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragment extends BaseFragment<NewsViewModel> implements ElcomNewsBottomAdapter.ItemSelectedListener, ElcomNewsChildBottomAdapter.ItemChildSelectedListener, ElcomNewsAdapter.ItemSelectedListener {

    private RecyclerView recyclerViewTop;
    private RecyclerView recyclerViewBottom;
    private NewsViewModel newsViewModel;
    private ElcomNewsAdapter elcomNewsAdapter;
    private ElcomNewsBottomAdapter elcomNewsBottomAdapter;
    private  SwipeRefreshLayout swipeRefreshLayout;
    public NewsFragment() {
        // Required empty public constructor
    }

    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if(view == null){
            view = inflater.inflate(R.layout.fragment_news2, container, false);
        }

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerViewTop = view.findViewById(R.id.recyclerViewTop);
        recyclerViewBottom = view.findViewById(R.id.recyclerViewBottom);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.color_blue_accent));
        swipeRefreshLayout.setOnRefreshListener(() ->  getData());
    }

    @Override
    protected Class<NewsViewModel> getViewModel() {
        return NewsViewModel.class;
    }

    @Override
    protected void onCreate(Bundle instance, NewsViewModel viewModel) {
        newsViewModel = viewModel;
        getData();
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private void getData(){
        newsViewModel.getNews().observe(this, new Observer<RestData<NewsRes>>() {
            @Override
            public void onChanged(@Nullable RestData<NewsRes> newsResRestData) {
                if(newsResRestData != null && newsResRestData.status_code == 200){
                    swipeRefreshLayout.setRefreshing(false);
                    setupRecyclerViewTop(newsResRestData.data.getHot_articles());
                    setupRecyclerViewBottom(newsResRestData.data.getNormal_articles());
                }
            }
        });

        newsViewModel.setNewsRequest();

    }

    private void setupRecyclerViewTop(List<News> news){
        elcomNewsAdapter = new ElcomNewsAdapter(getContext());
        elcomNewsAdapter.setData(news);
        elcomNewsAdapter.setOnItemClickListener(this);
        recyclerViewTop.setLayoutManager(new StaggeredGridLayoutManager(1, OrientationHelper.HORIZONTAL));
        recyclerViewTop.setAdapter(elcomNewsAdapter);
    }

    private void setupRecyclerViewBottom(List<NewsNormal> news){
        elcomNewsBottomAdapter = new ElcomNewsBottomAdapter(getContext());
        elcomNewsBottomAdapter.setData(news);
        elcomNewsBottomAdapter.setOnItemClickListener(this);
        elcomNewsBottomAdapter.setOnItemChildClickListener(this);
        recyclerViewBottom.setLayoutManager(new StaggeredGridLayoutManager(1, OrientationHelper.VERTICAL));
        recyclerViewBottom.setAdapter(elcomNewsBottomAdapter);
    }

    @Override
    public void onItemSelected(View view, NewsNormal item) { // Top
        NewsAllActivity.start(getContext(),item.getCategory_id());
    }

    @Override
    public void onItemSelected(View view, News item) { // For child
        NewsDetailActivity.start(getContext(),item.getId());
    }

    @Override
    public void onItemChildSelected(View view, News item) {
        NewsDetailActivity.start(getContext(),item.getId());
    }
}
