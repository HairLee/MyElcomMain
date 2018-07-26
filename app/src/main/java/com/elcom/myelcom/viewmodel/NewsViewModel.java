package com.elcom.myelcom.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
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
    private final MutableLiveData<Integer> newsRequest = new MutableLiveData<>();
    @Inject
    NewsViewModel(NewsRepository repository) {
        this.repository = repository;

        newsResResult = Transformations.switchMap(newsRequest,
                param -> repository.getNews());
    }

    public LiveData<RestData<NewsRes>> getNews() {
        return newsResResult;
    }

    public void setNewsRequest(){
        newsRequest.setValue(1);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        repository.onClear();
    }
}
