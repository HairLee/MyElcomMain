package com.elcom.hailpt.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import com.elcom.hailpt.model.api.RestData;
import com.elcom.hailpt.model.api.request.NewsDetailRq;
import com.elcom.hailpt.model.api.response.Contact;
import com.elcom.hailpt.model.api.response.ContactSuggest;
import com.elcom.hailpt.model.api.response.News;
import com.elcom.hailpt.model.api.response.NewsDetailRes;
import com.elcom.hailpt.repository.AllContactSuggestRepository;
import com.elcom.hailpt.repository.NewsDetailRepository;

import java.util.List;

import javax.inject.Inject;

/**
 * @author ihsan on 12/27/17.
 */

public class NewsDetailViewModel extends ViewModel {

    private final MutableLiveData<NewsDetailRq> newsDetailRq = new MutableLiveData<>();
    private final MutableLiveData<NewsDetailRq> detailRq = new MutableLiveData<>();

    private final LiveData<RestData<NewsDetailRes>> newsDetailResResult;
    private final LiveData<RestData<News>> detailResResult;
    private final NewsDetailRepository repository;

    @Inject
    NewsDetailViewModel(NewsDetailRepository repository) {
        this.repository = repository;

        newsDetailResResult = Transformations.switchMap(newsDetailRq,
                param -> repository.getAllNews(newsDetailRq.getValue().getId(),newsDetailRq.getValue().getOffset(), newsDetailRq.getValue().getLimit()));


        detailResResult = Transformations.switchMap(detailRq,
                param -> repository.getNewsDetail(detailRq.getValue().getId()));

    }



    public LiveData<RestData<NewsDetailRes>> getAllNews() {
        return newsDetailResResult;
    }

    public LiveData<RestData<News>> getNewsDetail() {
        return detailResResult;
    }

    public void setNewsDetailRes(NewsDetailRq newsDetailRes){
        newsDetailRq.setValue(newsDetailRes);
    }

    public void setDetailReq(NewsDetailRq newsDetailRes){
        detailRq.setValue(newsDetailRes);
    }



    @Override
    protected void onCleared() {
        super.onCleared();
        repository.onClear();
    }
}
