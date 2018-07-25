package com.elcom.myelcom.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.elcom.myelcom.model.api.RestData;
import com.elcom.myelcom.model.api.response.NewsRes;
import com.elcom.myelcom.repository.NewsRepository;

import javax.inject.Inject;

/**
 * @author ihsan on 12/27/17.
 */

public class NewsViewModel extends ViewModel {


    private final LiveData<RestData<NewsRes>> newsResResult;
    private final NewsRepository repository;

    @Inject
    NewsViewModel(NewsRepository repository) {
        this.repository = repository;
        newsResResult = repository.getNews();
    }

    public LiveData<RestData<NewsRes>> getNews() {
        return newsResResult;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        repository.onClear();
    }
}
