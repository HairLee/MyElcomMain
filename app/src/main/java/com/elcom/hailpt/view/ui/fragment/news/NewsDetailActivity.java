package com.elcom.hailpt.view.ui.fragment.news;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebSettings;

import com.elcom.hailpt.R;
import com.elcom.hailpt.core.base.BaseActivity;
import com.elcom.hailpt.databinding.ActivityNewsDetailBinding;
import com.elcom.hailpt.model.api.RestData;
import com.elcom.hailpt.model.api.request.LikeCommentReq;
import com.elcom.hailpt.model.api.request.NewsDetailRq;
import com.elcom.hailpt.model.api.request.SendCommentReq;
import com.elcom.hailpt.model.api.response.Comment;
import com.elcom.hailpt.model.api.response.News;
import com.elcom.hailpt.util.Consts;
import com.elcom.hailpt.util.KeyBoardUtils;
import com.elcom.hailpt.view.adapter.CommentAdapter;
import com.elcom.hailpt.view.adapter.ElcomNewsChildBottomAdapter;
import com.elcom.hailpt.viewmodel.NewsDetailViewModel;
import com.google.gson.JsonElement;
import com.onesignal.OneSignal;

import java.util.List;

public class NewsDetailActivity extends BaseActivity<NewsDetailViewModel,ActivityNewsDetailBinding> {


    NewsDetailViewModel newsDetailViewModel;
    private ActivityNewsDetailBinding activityNewsDetailBinding;
    private int caterogyId = 0;
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

                    setupListComment(newsRestData.data.getComment());
                }
            }
        });

        newsDetailViewModel.sendComment().observe(this, jsonElementRestData -> {
            if (jsonElementRestData.status_code == 200){
                activityNewsDetailBinding.edtComment.setText("");
                KeyBoardUtils.hideKeyboard(NewsDetailActivity.this);
                getData();


            }
        });

        newsDetailViewModel.likeComment().observe(this, jsonElementRestData -> {
            if (jsonElementRestData.status_code == 200){
                getData();
            }
        });

        if(getIntent().hasExtra(Consts.EXTRA_IS_ID)){
            caterogyId = getIntent().getExtras().getInt(Consts.EXTRA_IS_ID);
            getData();
        }

        activityNewsDetailBinding.tvSendCommment.setOnClickListener(v -> {
            SendCommentReq sendCommentReq = new SendCommentReq();
            sendCommentReq.setArticle_id(caterogyId);
            sendCommentReq.setComment_content(activityNewsDetailBinding.edtComment.getText().toString());
            viewModel.setComment(sendCommentReq);
        });

        activityNewsDetailBinding.rlLike.setOnClickListener(v -> {
            LikeCommentReq likeCommentReq = new LikeCommentReq();
            likeCommentReq.setArticle_id(caterogyId);
            viewModel.likeComment(likeCommentReq);
        });

        activityNewsDetailBinding.rlShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        activityNewsDetailBinding.imvBack.setOnClickListener(v -> onBackPressed());

    }

    private void getData(){
        NewsDetailRq newsDetailRq = new NewsDetailRq();
        newsDetailRq.setId(caterogyId);
        newsDetailRq.setOffset(0);
        newsDetailRq.setLimit(10);
        newsDetailViewModel.setDetailReq(newsDetailRq);
    }

//    private int getScale(){
//        Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
//        int width = display.getWidth();
//        Double val = new Double(width)/new Double(PIC_WIDTH);
//        val = val * 100d;
//        return val.intValue();
//    }

    private void setupView(News news){
        String myHtmlString = "<html><body>"+news.getContent()+"</body></html>";
//        activityNewsDetailBinding.webContent.setFocusable(true);
//        activityNewsDetailBinding.webContent.setFocusableInTouchMode(true);
        activityNewsDetailBinding.webContent.getSettings().setJavaScriptEnabled(true);
        activityNewsDetailBinding.webContent.getSettings().setLoadWithOverviewMode(true);
        activityNewsDetailBinding.webContent.getSettings().setUseWideViewPort(true);
//        activityNewsDetailBinding.webContent.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

//        activityNewsDetailBinding.webContent.getSettings().setJavaScriptEnabled(true);
//        activityNewsDetailBinding.webContent.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
//        activityNewsDetailBinding.webContent.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
//        activityNewsDetailBinding.webContent.getSettings().setDomStorageEnabled(true);
//        activityNewsDetailBinding.webContent.getSettings().setDatabaseEnabled(true);
//        activityNewsDetailBinding.webContent.getSettings().setAppCacheEnabled(true);
//        activityNewsDetailBinding.webContent.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        activityNewsDetailBinding.webContent.loadData(myHtmlString, "text/html", null);

        activityNewsDetailBinding.tvComment.setText(news.getSum_comment()+ " Likes");
        activityNewsDetailBinding.tvLike.setText(news.getSum_like()+" Bình luận");

        if(news.getUrl_article() != null){
            activityNewsDetailBinding.rlShare.setVisibility(View.GONE);
        } else {
            activityNewsDetailBinding.rlShare.setVisibility(View.VISIBLE);
        }

    }

    private void testLoad(String url){
        Display display = getWindowManager().getDefaultDisplay();
        int width=display.getWidth();

        String data="<html><head><title>Example</title><meta name=\"viewport\"\"content=\"width="+width+", initial-scale=0.65 \" /></head>";
        data=data+"<body><center><img width=\""+width+"\" src=\""+url+"\" /></center></body></html>";
        activityNewsDetailBinding.webContent.loadData(data, "text/html", null);
    }

    private void setupListComment(List<Comment> data){
        CommentAdapter elcomNewsChildBottomAdapter = new CommentAdapter(this);
        elcomNewsChildBottomAdapter.setData(data);
//        elcomNewsChildBottomAdapter.setOnItemClickListener(this);
        activityNewsDetailBinding.recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, OrientationHelper.VERTICAL));
        activityNewsDetailBinding.recyclerView.setNestedScrollingEnabled(false);
        activityNewsDetailBinding.recyclerView.setAdapter(elcomNewsChildBottomAdapter);
    }

    public static void start(Context context, int caterogyId) {
        Intent intent = new Intent(context, NewsDetailActivity.class);
        intent.putExtra(Consts.EXTRA_IS_ID, caterogyId);
        context.startActivity(intent);
    }
}
