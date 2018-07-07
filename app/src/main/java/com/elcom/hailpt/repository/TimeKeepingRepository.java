package com.elcom.hailpt.repository;

import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.elcom.hailpt.core.AppSchedulerProvider;
import com.elcom.hailpt.core.BaseViewModel;
import com.elcom.hailpt.core.base.ArticleUtils;
import com.elcom.hailpt.model.api.Api;
import com.elcom.hailpt.model.api.RestData;
import com.elcom.hailpt.model.api.request.LoginReq;
import com.elcom.hailpt.model.api.response.User;
import com.elcom.hailpt.model.data.Article;
import com.elcom.hailpt.model.data.Articles;
import com.elcom.hailpt.model.data.Source;
import com.elcom.hailpt.model.data.Sources;
import com.elcom.hailpt.model.data.TimeKeep;
import com.elcom.hailpt.model.db.AppDatabase;
import com.elcom.hailpt.model.db.ArticleDao;
import com.elcom.hailpt.model.db.SourceDao;
import com.elcom.hailpt.util.ConstantsApp;

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