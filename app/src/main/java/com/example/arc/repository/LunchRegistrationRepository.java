package com.example.arc.repository;

import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.example.arc.core.AppSchedulerProvider;
import com.example.arc.core.BaseViewModel;
import com.example.arc.model.api.Api;
import com.example.arc.model.api.RestData;
import com.example.arc.model.api.request.LunchCancelReq;
import com.example.arc.model.api.request.LunchFeedBackReq;
import com.example.arc.model.api.request.LunchLikeReq;
import com.example.arc.model.api.response.Contact;
import com.example.arc.model.api.response.ContactSuggest;
import com.example.arc.model.api.response.Lunch;
import com.example.arc.model.data.Source;
import com.example.arc.model.data.TimeKeep;
import com.example.arc.model.db.AppDatabase;
import com.example.arc.model.db.ArticleDao;
import com.example.arc.model.db.SourceDao;
import com.example.arc.util.ConstantsApp;
import com.google.gson.JsonElement;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * @author ihsan on 12/28/17.
 */

public class LunchRegistrationRepository implements BaseViewModel {

    private final List<Source> sourceList;
    private CompositeDisposable disposables = new CompositeDisposable();
    private final Api api;
    private final MutableLiveData<RestData<JsonElement>> articleMutableLiveData;
    private final MutableLiveData<RestData<JsonElement>> registerLunchMutableLiveData;
    private final MutableLiveData<RestData<JsonElement>> feedBackLunchMutableLiveData;
    private final MutableLiveData<RestData<List<Lunch>>> lunchMutableLiveData;
    private final AppSchedulerProvider schedulerProvider;
    private final SourceDao sourceDao;
    private final ArticleDao articleDao;

    @Inject
    LunchRegistrationRepository(AppDatabase database, Api api, AppSchedulerProvider schedulerProvider) {
        this.api = api;
        this.schedulerProvider = schedulerProvider;
        sourceDao = database.sourceDao();
        articleDao = database.articleDao();
        sourceList = sourceDao.getAllList();
        articleMutableLiveData = new MutableLiveData<>();
        lunchMutableLiveData = new MutableLiveData<>();
        registerLunchMutableLiveData = new MutableLiveData<>();
        feedBackLunchMutableLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<RestData<JsonElement>> cancelLunch(LunchCancelReq lunchCancelReq) {
        api.cancelLunch(lunchCancelReq, ConstantsApp.BASE64_HEADER)
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
                        articleMutableLiveData.postValue(sources);
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

    public MutableLiveData<RestData<JsonElement>> registerLunch(LunchCancelReq lunchCancelReq) {
        api.registerLunch(lunchCancelReq, ConstantsApp.BASE64_HEADER)
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
                        registerLunchMutableLiveData.postValue(sources);
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                    }
                });
        return registerLunchMutableLiveData;
    }


    public MutableLiveData<RestData<JsonElement>> sendLunchFeedBack(LunchFeedBackReq lunchFeedBackReq) {
        api.sendLunchFeedBack(lunchFeedBackReq, ConstantsApp.BASE64_HEADER)
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
                        feedBackLunchMutableLiveData.postValue(sources);
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                    }
                });
        return feedBackLunchMutableLiveData;
    }

    public MutableLiveData<RestData<List<Lunch>>> getLunchMenu(String fromTime, String toTime) {
        api.getLunchMenu(fromTime,toTime,ConstantsApp.BASE64_HEADER)
                .observeOn(schedulerProvider.ui())
                .subscribeOn(schedulerProvider.io())
                .map(data -> data)
                .subscribe(new Observer<RestData<List<Lunch>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposables.add(d);
                    }

                    @Override
                    public void onNext(RestData<List<Lunch>> sources) {
                        lunchMutableLiveData.postValue(sources);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
        return lunchMutableLiveData;
    }

    public MutableLiveData<RestData<JsonElement>> likeLunch(LunchLikeReq lunchCancelReq) {

        if(lunchCancelReq.isLike()){
            api.likeLunch(lunchCancelReq, ConstantsApp.BASE64_HEADER)
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
                            articleMutableLiveData.postValue(sources);
                        }

                        @Override
                        public void onError(Throwable e) {
                        }

                        @Override
                        public void onComplete() {
                        }
                    });
        } else {
            api.dislikeLunch(lunchCancelReq, ConstantsApp.BASE64_HEADER)
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
                            articleMutableLiveData.postValue(sources);
                        }

                        @Override
                        public void onError(Throwable e) {
                        }

                        @Override
                        public void onComplete() {
                        }
                    });
        }


        return articleMutableLiveData;
    }


    @Override
    public void onClear() {
        disposables.clear();
    }
}
