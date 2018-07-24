package com.elcom.hailpt.view.ui.fragment.news;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;

import com.elcom.hailpt.R;
import com.elcom.hailpt.core.base.BaseActivity;
import com.elcom.hailpt.databinding.ActivityNewsDetailBinding;
import com.elcom.hailpt.model.api.RestData;
import com.elcom.hailpt.model.api.request.NewsDetailRq;
import com.elcom.hailpt.model.api.response.News;
import com.elcom.hailpt.util.Consts;
import com.elcom.hailpt.viewmodel.NewsDetailViewModel;

public class NewsDetailActivity extends BaseActivity<NewsDetailViewModel,ActivityNewsDetailBinding> {


    NewsDetailViewModel newsDetailViewModel;
    private ActivityNewsDetailBinding activityNewsDetailBinding;
    @Override
    protected Class<NewsDetailViewModel> getViewModel() {
        return NewsDetailViewModel.class;
    }

    @Override
    protected void onCreate(Bundle instance, NewsDetailViewModel viewModel, ActivityNewsDetailBinding binding) {
        newsDetailViewModel = viewModel;
        activityNewsDetailBinding = binding;
        init(newsDetailViewModel);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_news_detail;
    }

    private void init(NewsDetailViewModel viewModel){
        newsDetailViewModel.getNewsDetail().observe(this, new Observer<RestData<News>>() {
            @Override
            public void onChanged(@Nullable RestData<News> newsRestData) {
                Log.e("hailpt"," newsRestData "+newsRestData);
                if(newsRestData != null && newsRestData.status_code == 200){
                    setupView(newsRestData.data);
                }
            }
        });

        if(getIntent().hasExtra(Consts.EXTRA_IS_ID)){
            NewsDetailRq newsDetailRq = new NewsDetailRq();
            newsDetailRq.setId(getIntent().getExtras().getInt(Consts.EXTRA_IS_ID));
            newsDetailRq.setOffset(0);
            newsDetailRq.setLimit(10);
            viewModel.setDetailReq(newsDetailRq);
        }

    }

    private void setupView(News news){
        String myHtmlString = "<html><body>"+news.getContent()+"</body></html>";
        activityNewsDetailBinding.webContent.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        activityNewsDetailBinding.webContent.loadData(myHtmlString, "text/html", null);
    }

    public static void start(Context context, int caterogyId) {
        Intent intent = new Intent(context, NewsDetailActivity.class);
        intent.putExtra(Consts.EXTRA_IS_ID, caterogyId);
        context.startActivity(intent);
    }
}
