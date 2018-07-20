package com.elcom.hailpt.repository;

import android.arch.lifecycle.MutableLiveData;

import com.elcom.hailpt.core.AppSchedulerProvider;
import com.elcom.hailpt.core.BaseViewModel;
import com.elcom.hailpt.model.api.Api;
import com.elcom.hailpt.model.api.RestData;
import com.elcom.hailpt.model.api.response.User;
import com.elcom.hailpt.model.data.Source;
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

public class ContactOnlineRepository implements BaseViewModel {

    private CompositeDisposable disposables = new CompositeDisposable();
    private final Api api;
    private final MutableLiveData<RestData<List<User>>> articleMutableLiveData;
    private final AppSchedulerProvider schedulerProvider;
    private final SourceDao sourceDao;

    @Inject
    ContactOnlineRepository(AppDatabase database, Api api, AppSchedulerProvider schedulerProvider) {
        this.api = api;
        this.schedulerProvider = schedulerProvider;
        sourceDao = database.sourceDao();
        articleMutableLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<RestData<List<User>>> getOnlineContact() {
        api.getOnlineContact(ConstantsApp.BASE64_HEADER)
                .observeOn(schedulerProvider.ui())
                .subscribeOn(schedulerProvider.io())
                .map(data -> data)
                .subscribe(new Observer<RestData<List<User>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposables.add(d);
                    }

                    @Override
                    public void onNext(RestData<List<User>> sources) {
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


    @Override
    public void onClear() {
        disposables.clear();
    }
}
