package com.elcom.myelcom.view.ui.fragment.news;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.elcom.myelcom.R;
import com.elcom.myelcom.core.base.BaseActivity;
import com.elcom.myelcom.databinding.ActivityNewsAllBinding;
import com.elcom.myelcom.model.api.request.NewsDetailRq;
import com.elcom.myelcom.model.api.response.News;
import com.elcom.myelcom.util.Consts;
import com.elcom.myelcom.view.adapter.ElcomNewsChildBottomAdapter;
import com.elcom.myelcom.viewmodel.NewsDetailViewModel;

import java.util.List;

public class NewsAllActivity extends BaseActivity<NewsDetailViewModel, ActivityNewsAllBinding> implements ElcomNewsChildBottomAdapter.ItemChildSelectedListener {



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
        binding.tvTitle.setText("Bản Tin Elcom");
        viewModel.getAllNews().observe(this, newsDetailResRestData -> {

            if(newsDetailResRestData != null && newsDetailResRestData.data != null){
                setupRecyclerViewBottom(newsDetailResRestData.data.getArticles());
            }
        });


        if(getIntent().hasExtra(Consts.EXTRA_IS_ID)){
            NewsDetailRq newsDetailRq = new NewsDetailRq();
            newsDetailRq.setId(getIntent().getExtras().getInt(Consts.EXTRA_IS_ID));
            newsDetailRq.setOffset(0);
            newsDetailRq.setLimit(10);
            viewModel.setNewsDetailRes(newsDetailRq);
        }

        binding.imvBack.setOnClickListener(v -> onBackPressed());

    }

    private void setupRecyclerViewBottom(List<News> data){
        ElcomNewsChildBottomAdapter elcomNewsChildBottomAdapter = new ElcomNewsChildBottomAdapter(this);
        elcomNewsChildBottomAdapter.setData(data);
        elcomNewsChildBottomAdapter.setOnItemClickListener(this);
        binding.recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, OrientationHelper.VERTICAL));
        binding.recyclerView.setAdapter(elcomNewsChildBottomAdapter);
    }

    public static void start(Context context, int caterogyId) {
        Intent intent = new Intent(context, NewsAllActivity.class);
        intent.putExtra(Consts.EXTRA_IS_ID, caterogyId);
        context.startActivity(intent);
    }


    @Override
    public void onItemChildSelected(View view, News item) {
        NewsDetailActivity.start(this, item.getId());
    }
}
