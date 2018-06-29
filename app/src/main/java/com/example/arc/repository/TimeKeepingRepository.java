package com.example.arc.repository;

import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.example.arc.core.AppSchedulerProvider;
import com.example.arc.core.BaseViewModel;
import com.example.arc.core.base.ArticleUtils;
import com.example.arc.model.api.Api;
import com.example.arc.model.api.RestData;
import com.example.arc.model.api.request.LoginReq;
import com.example.arc.model.api.response.User;
import com.example.arc.model.data.Article;
import com.example.arc.model.data.Articles;
import com.example.arc.model.data.Source;
import com.example.arc.model.data.Sources;
import com.example.arc.model.data.TimeKeep;
import com.example.arc.model.db.AppDatabase;
import com.example.arc.model.db.ArticleDao;
import com.example.arc.model.db.SourceDao;
import com.example.arc.util.ConstantsApp;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * @author ihsan on 12/28/17.
 */

public class TimeKeepingRepository implements BaseViewModel {

    private final List<Source> sourceList;
    private CompositeDisposable disposables = new CompositeDisposable();
    private final Api api;
    private final MutableLiveData<RestData<List<TimeKeep>>> articleMutableLiveData;
    private final AppSchedulerProvider schedulerProvider;
    private final SourceDao sourceDao;
    private final ArticleDao articleDao;

    @Inject
    TimeKeepingRepository(AppDatabase database, Api api, AppSchedulerProvider schedulerProvider) {
        this.api = api;
        this.schedulerProvider = schedulerProvider;
        sourceDao = database.sourceDao();
        articleDao = database.articleDao();
        sourceList = sourceDao.getAllList();
        articleMutableLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<RestData<List<TimeKeep>>> getTimeKeeping(String fromTime,String toTime) {

        Log.e("hailpt"," TimeKeepingRepository "+fromTime + " "+ toTime);

        api.getTimeKeeping(fromTime,toTime,ConstantsApp.BASE64_HEADER)
                .observeOn(schedulerProvider.ui())
                .subscribeOn(schedulerProvider.io())
                .map(data -> data)
                .subscribe(new Observer<RestData<List<TimeKeep>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposables.add(d);
                    }

                    @Override
                    public void onNext(RestData<List<TimeKeep>> sources) {
                        articleMutableLiveData.postValue(sources);

                        Log.e("hailpt"," TimeKeepingRepository data = "+ sources.data.size());
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {

                    }
                });
        return articleMutableLiveData;
    }

    @Override
    public void onClear() {
        disposables.clear();
    }
}
