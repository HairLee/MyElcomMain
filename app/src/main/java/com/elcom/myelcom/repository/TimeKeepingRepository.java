package com.elcom.myelcom.repository;

import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.elcom.myelcom.core.AppSchedulerProvider;
import com.elcom.myelcom.core.BaseViewModel;
import com.elcom.myelcom.model.api.Api;
import com.elcom.myelcom.model.api.RestData;
import com.elcom.myelcom.model.api.request.ReasonLate;
import com.elcom.myelcom.model.data.Source;
import com.elcom.myelcom.model.data.Statistic;
import com.elcom.myelcom.model.data.TimeKeep;
import com.elcom.myelcom.model.db.AppDatabase;
import com.elcom.myelcom.model.db.ArticleDao;
import com.elcom.myelcom.model.db.SourceDao;
import com.elcom.myelcom.util.ConstantsApp;
import com.google.gson.JsonElement;

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
    private final MutableLiveData<RestData<Statistic>> statisticMutableLiveData;
    private final MutableLiveData<RestData<JsonElement>> reasonLateMutableLiveData;
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
        statisticMutableLiveData = new MutableLiveData<>();
        reasonLateMutableLiveData = new MutableLiveData<>();
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



    public MutableLiveData<RestData<Statistic>> getMonthInformation(String year,String month) {

        api.getMonthInformation(year,month,ConstantsApp.BASE64_HEADER)
                .observeOn(schedulerProvider.ui())
                .subscribeOn(schedulerProvider.io())
                .map(data -> data)
                .subscribe(new Observer<RestData<Statistic>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposables.add(d);
                    }

                    @Override
                    public void onNext(RestData<Statistic> sources) {
                        statisticMutableLiveData.postValue(sources);
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {

                    }
                });
        return statisticMutableLiveData;
    }


    public MutableLiveData<RestData<JsonElement>> reasonLate(ReasonLate reasonLate) {

        api.reasonLate(reasonLate,ConstantsApp.BASE64_HEADER)
                .observeOn(schedulerProvider.ui())
                .subscribeOn(schedulerProvider.io())
                .map(data -> data)
                .subscribe(new Observer<RestData<JsonElement>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposables.add(d);
                    }

                    @Override
                    public void onNext(RestData<JsonElement> sources) {
                        reasonLateMutableLiveData.postValue(sources);
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {

                    }
                });
        return reasonLateMutableLiveData;
    }

    @Override
    public void onClear() {
        disposables.clear();
    }
}
