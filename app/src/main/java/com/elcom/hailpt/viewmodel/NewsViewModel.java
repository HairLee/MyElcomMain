package com.elcom.hailpt.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import com.elcom.hailpt.model.api.RestData;
import com.elcom.hailpt.model.api.response.Contact;
import com.elcom.hailpt.model.api.response.ContactSuggest;
import com.elcom.hailpt.model.api.response.NewsRes;
import com.elcom.hailpt.repository.AllContactSuggestRepository;
import com.elcom.hailpt.repository.NewsRepository;

import java.util.List;

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
