package com.elcom.hailpt.view.ui.fragment.news;


import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.elcom.hailpt.R;
import com.elcom.hailpt.core.base.BaseFragment;
import com.elcom.hailpt.model.api.RestData;
import com.elcom.hailpt.model.api.response.News;
import com.elcom.hailpt.model.api.response.NewsNormal;
import com.elcom.hailpt.model.api.response.NewsRes;
import com.elcom.hailpt.view.adapter.ElcomNewsAdapter;
import com.elcom.hailpt.view.adapter.ElcomNewsBottomAdapter;
import com.elcom.hailpt.viewmodel.NewsViewModel;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragment extends BaseFragment<NewsViewModel> implements ElcomNewsBottomAdapter.ItemSelectedListener {

    private RecyclerView recyclerViewTop;
    private RecyclerView recyclerViewBottom;
    private NewsViewModel newsViewModel;
    private ElcomNewsAdapter elcomNewsAdapter;
    private ElcomNewsBottomAdapter elcomNewsBottomAdapter;
    public NewsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_news2, container, false);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerViewTop = view.findViewById(R.id.recyclerViewTop);
        recyclerViewBottom = view.findViewById(R.id.recyclerViewBottom);
    }

    @Override
    protected Class<NewsViewModel> getViewModel() {
        return NewsViewModel.class;
    }

    @Override
    protected void onCreate(Bundle instance, NewsViewModel viewModel) {
        newsViewModel = viewModel;

        newsViewModel.getNews().observe(this, new Observer<RestData<NewsRes>>() {
            @Override
            public void onChanged(@Nullable RestData<NewsRes> newsResRestData) {
                if(newsResRestData != null && newsResRestData.status_code == 200){
                    setupRecyclerViewTop(newsResRestData.data.getHot_articles());
                    setupRecyclerViewBottom(newsResRestData.data.getNormal_articles());
                }
            }
        });
    }

    private void setupRecyclerViewTop(List<News> news){
        elcomNewsAdapter = new ElcomNewsAdapter(getContext());
        elcomNewsAdapter.setData(news);
        recyclerViewTop.setLayoutManager(new StaggeredGridLayoutManager(1, OrientationHelper.HORIZONTAL));
        recyclerViewTop.setAdapter(elcomNewsAdapter);
    }

    private void setupRecyclerViewBottom(List<NewsNormal> news){
        elcomNewsBottomAdapter = new ElcomNewsBottomAdapter(getContext());
        elcomNewsBottomAdapter.setData(news);
        elcomNewsBottomAdapter.setOnItemClickListener(this);
        recyclerViewBottom.setLayoutManager(new StaggeredGridLayoutManager(1, OrientationHelper.VERTICAL));
        recyclerViewBottom.setAdapter(elcomNewsBottomAdapter);
    }

    @Override
    public void onItemSelected(View view, NewsNormal item) {
            Log.e("hailpt"," NewsNormal " + item.getCategory_name());
            NewsAllActivity.start(getContext(),item.getCategory_id());
    }
}
