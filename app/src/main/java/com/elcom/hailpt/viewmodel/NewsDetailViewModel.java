package com.elcom.hailpt.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import com.elcom.hailpt.model.api.RestData;
import com.elcom.hailpt.model.api.request.LikeCommentReq;
import com.elcom.hailpt.model.api.request.NewsDetailRq;
import com.elcom.hailpt.model.api.request.SendCommentReq;
import com.elcom.hailpt.model.api.response.Contact;
import com.elcom.hailpt.model.api.response.ContactSuggest;
import com.elcom.hailpt.model.api.response.News;
import com.elcom.hailpt.model.api.response.NewsDetailRes;
import com.elcom.hailpt.repository.AllContactSuggestRepository;
import com.elcom.hailpt.repository.NewsDetailRepository;
import com.google.gson.JsonElement;

import java.util.List;

import javax.inject.Inject;

/**
 * @author ihsan on 12/27/17.
 */

public class NewsDetailViewModel extends ViewModel {

    private final MutableLiveData<NewsDetailRq> newsDetailRq = new MutableLiveData<>();
    private final MutableLiveData<NewsDetailRq> detailRq = new MutableLiveData<>();
    private final MutableLiveData<SendCommentReq> sendCommentReq = new MutableLiveData<>();
    private final MutableLiveData<LikeCommentReq> likeCommentReq = new MutableLiveData<>();

    private final LiveData<RestData<NewsDetailRes>> newsDetailResResult;
    private final LiveData<RestData<News>> detailResResult;
    private final LiveData<RestData<JsonElement>> sendResResult;
    private final LiveData<RestData<JsonElement>> likeResResult;
    private final NewsDetailRepository repository;

    @Inject
    NewsDetailViewModel(NewsDetailRepository repository) {
        this.repository = repository;

        newsDetailResResult = Transformations.switchMap(newsDetailRq,
                param -> repository.getAllNews(newsDetailRq.getValue().getId(),newsDetailRq.getValue().getOffset(), newsDetailRq.getValue().getLimit()));


        detailResResult = Transformations.switchMap(detailRq,
                param -> repository.getNewsDetail(detailRq.getValue().getId()));


        sendResResult = Transformations.switchMap(sendCommentReq,
                param -> repository.sendComment(sendCommentReq.getValue()));


        likeResResult = Transformations.switchMap(likeCommentReq,
                param -> repository.likeComment(likeCommentReq.getValue()));
    }



    public LiveData<RestData<NewsDetailRes>> getAllNews() {
        return newsDetailResResult;
    }

    public LiveData<RestData<News>> getNewsDetail() {
        return detailResResult;
    }

    public LiveData<RestData<JsonElement>> sendComment() {
        return sendResResult;
    }

    public LiveData<RestData<JsonElement>> likeComment() {
        return likeResResult;
    }

    public void setNewsDetailRes(NewsDetailRq newsDetailRes){
        newsDetailRq.setValue(newsDetailRes);
    }

    public void setDetailReq(NewsDetailRq newsDetailRes){
        detailRq.setValue(newsDetailRes);
    }

    public void setComment(SendCommentReq pSendCommentReq){
        sendCommentReq.setValue(pSendCommentReq);
    }

    public void likeComment(LikeCommentReq pLikeCommentReq){
        likeCommentReq.setValue(pLikeCommentReq);
    }



    @Override
    protected void onCleared() {
        super.onCleared();
        repository.onClear();
    }
}
