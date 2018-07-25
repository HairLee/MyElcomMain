package com.elcom.myelcom.repository;

import android.arch.lifecycle.MutableLiveData;

import com.elcom.myelcom.core.AppSchedulerProvider;
import com.elcom.myelcom.core.BaseViewModel;
import com.elcom.myelcom.model.api.Api;
import com.elcom.myelcom.model.api.RestData;
import com.elcom.myelcom.model.api.request.RemoveNotificationReq;
import com.elcom.myelcom.model.api.response.Notification;
import com.elcom.myelcom.model.db.AppDatabase;
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

public class NotificationRepository implements BaseViewModel {

    private CompositeDisposable disposables = new CompositeDisposable();
    private final Api api;
    private final MutableLiveData<RestData<List<Notification>>> articleMutableLiveData;
    private final MutableLiveData<RestData<JsonElement>> removeNotificationMutableLiveData;
    private final MutableLiveData<RestData<JsonElement>> viewNotificationMutableLiveData;
    private final AppSchedulerProvider schedulerProvider;


    @Inject
    NotificationRepository(AppDatabase database, Api api, AppSchedulerProvider schedulerProvider) {
        this.api = api;
        this.schedulerProvider = schedulerProvider;
        articleMutableLiveData = new MutableLiveData<>();
        removeNotificationMutableLiveData = new MutableLiveData<>();
        viewNotificationMutableLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<RestData<List<Notification>>> getAllNotification() {
        api.getAllNotification(ConstantsApp.BASE64_HEADER)
                .observeOn(schedulerProvider.ui())
                .subscribeOn(schedulerProvider.io())
                .map(data -> data)
                .subscribe(new Observer<RestData<List<Notification>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposables.add(d);
                    }

                    @Override
                    public void onNext(RestData<List<Notification>> sources) {
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


    public MutableLiveData<RestData<JsonElement>> removeNotification(RemoveNotificationReq removeNotificationReq) {
        api.removeNotification(removeNotificationReq,ConstantsApp.BASE64_HEADER)
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
                        removeNotificationMutableLiveData.postValue(sources);
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                    }
                });
        return removeNotificationMutableLiveData;
    }


    public MutableLiveData<RestData<JsonElement>> viewNotification() {
        api.viewNotification(ConstantsApp.BASE64_HEADER)
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
                        removeNotificationMutableLiveData.postValue(sources);
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                    }
                });
        return removeNotificationMutableLiveData;
    }



    @Override
    public void onClear() {
        disposables.clear();
    }
}
