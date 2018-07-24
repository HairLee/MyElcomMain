package com.elcom.hailpt.view.ui.fragment.news;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.elcom.hailpt.R;
import com.elcom.hailpt.core.base.BaseActivity;
import com.elcom.hailpt.databinding.ActivityNewsAllBinding;
import com.elcom.hailpt.model.api.RestData;
import com.elcom.hailpt.model.api.request.NewsDetailRq;
import com.elcom.hailpt.model.api.response.News;
import com.elcom.hailpt.model.api.response.NewsDetailRes;
import com.elcom.hailpt.model.api.response.NewsNormal;
import com.elcom.hailpt.util.Consts;
import com.elcom.hailpt.view.adapter.ElcomNewsBottomAdapter;
import com.elcom.hailpt.view.adapter.ElcomNewsChildBottomAdapter;
import com.elcom.hailpt.view.ui.HomeActivity;
import com.elcom.hailpt.viewmodel.NewsDetailViewModel;

import java.util.List;

public class NewsAllActivity extends BaseActivity<NewsDetailViewModel, ActivityNewsAllBinding> {



    NewsDetailViewModel newsDetailViewModel;
    ActivityNewsAllBinding binding;
    @Override
    protected Class<NewsDetailViewModel> getViewModel() {
        return NewsDetailViewModel.class;
    }

    @Override
    protected void onCreate(Bundle instance, NewsDetailViewModel viewModel, ActivityNewsAllBinding binding) {
        newsDetailViewModel = viewModel;
        this.binding = binding;
        init(viewModel);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_news_all;
    }

    private void init(NewsDetailViewModel viewModel){

        viewModel.getAllNews().observe(this, new Observer<RestData<NewsDetailRes>>() {
            @Override
            public void onChanged(@Nullable RestData<NewsDetailRes> newsDetailResRestData) {

                if(newsDetailResRestData != null && newsDetailResRestData.data != null){
                    setupRecyclerViewBottom(newsDetailResRestData.data.getArticles());
                }


            }
        });


        if(getIntent().hasExtra(Consts.EXTRA_IS_ID)){
            NewsDetailRq newsDetailRq = new NewsDetailRq();
            newsDetailRq.setId(getIntent().getExtras().getInt(Consts.EXTRA_IS_ID));
            newsDetailRq.setOffset(0);
            newsDetailRq.setLimit(10);
            viewModel.setNewsDetailRes(newsDetailRq);
        }

    }

    private void setupRecyclerViewBottom(List<News> data){
        ElcomNewsChildBottomAdapter elcomNewsChildBottomAdapter = new ElcomNewsChildBottomAdapter(this);
        elcomNewsChildBottomAdapter.setData(data);
        binding.recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, OrientationHelper.VERTICAL));
        binding.recyclerView.setAdapter(elcomNewsChildBottomAdapter);
    }


    public static void start(Context context, int caterogyId) {
        Intent intent = new Intent(context, NewsAllActivity.class);
        intent.putExtra(Consts.EXTRA_IS_ID, caterogyId);
        context.startActivity(intent);
    }

}
