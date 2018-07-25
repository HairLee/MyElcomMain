package com.elcom.hailpt.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import com.elcom.hailpt.model.data.Article;
import com.elcom.hailpt.model.data.Source;
import com.elcom.hailpt.repository.DataRepository;
import com.google.gson.JsonElement;

import java.util.List;

import javax.inject.Inject;

/**
 * @author ihsan on 12/27/17.
 */

public class MainViewModel extends ViewModel {

    private final LiveData<List<Article>> articleList;
    private final LiveData<List<Source>> sourceList;
    private final LiveData<JsonElement> notificationCount;
    private final DataRepository repository;
    private final MutableLiveData<Integer>  notificationRequest = new MutableLiveData<>();
    @Inject
    MainViewModel(DataRepository repository) {
        this.repository = repository;
        articleList = repository.getArticleLiveList();
        sourceList = repository.getSourceLiveList();


        notificationCount = Transformations.switchMap(notificationRequest,
                param -> repository.getNotificationCount());
    }

    public LiveData<List<Article>> getArticleList() {
        return articleList;
    }

    public LiveData<List<Source>> getSourceList() {
        return sourceList;
    }

    public LiveData<JsonElement> getNotificationCount() {
        return notificationCount;
    }

    public void setNotificationRequest(){
        notificationRequest.setValue(1);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        repository.onClear();
    }
}
