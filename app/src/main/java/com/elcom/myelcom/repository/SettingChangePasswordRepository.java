package com.elcom.myelcom.repository;

import android.arch.lifecycle.MutableLiveData;

import com.elcom.myelcom.core.AppSchedulerProvider;
import com.elcom.myelcom.core.BaseViewModel;
import com.elcom.myelcom.model.api.Api;
import com.elcom.myelcom.model.api.RestData;
import com.elcom.myelcom.model.api.request.ChangePwRq;
import com.elcom.myelcom.model.db.AppDatabase;
import com.elcom.myelcom.model.db.ArticleDao;
import com.elcom.myelcom.model.db.SourceDao;
import com.elcom.myelcom.util.ConstantsApp;
import com.google.gson.JsonElement;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * @author ihsan on 12/28/17.
 */

public class SettingChangePasswordRepository implements BaseViewModel {

    private CompositeDisposable disposables = new CompositeDisposable();
    private final Api api;
    private final MutableLiveData<RestData<JsonElement>> articleMutableLiveData;
    private final AppSchedulerProvider schedulerProvider;
    private final SourceDao sourceDao;
    private final ArticleDao articleDao;


    @Inject
    SettingChangePasswordRepository(AppDatabase database, Api api, AppSchedulerProvider schedulerProvider) {
        this.api = api;
        this.schedulerProvider = schedulerProvider;
        sourceDao = database.sourceDao();
        articleDao = database.articleDao();
        articleMutableLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<RestData<JsonElement>> changePw(ChangePwRq changePwRq) {
        api.changePassword(changePwRq,ConstantsApp.BASE64_HEADER)
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


    @Override
    public void onClear() {
        disposables.clear();
    }
}
