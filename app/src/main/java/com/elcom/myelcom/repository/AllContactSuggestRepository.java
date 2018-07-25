package com.elcom.myelcom.repository;

import android.arch.lifecycle.MutableLiveData;

import com.elcom.myelcom.core.AppSchedulerProvider;
import com.elcom.myelcom.core.BaseViewModel;
import com.elcom.myelcom.model.api.Api;
import com.elcom.myelcom.model.api.RestData;
import com.elcom.myelcom.model.api.response.Contact;
import com.elcom.myelcom.model.api.response.ContactSuggest;
import com.elcom.myelcom.model.data.Source;
import com.elcom.myelcom.model.db.AppDatabase;
import com.elcom.myelcom.model.db.ArticleDao;
import com.elcom.myelcom.model.db.SourceDao;
import com.elcom.myelcom.util.ConstantsApp;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * @author ihsan on 12/28/17.
 */

public class AllContactSuggestRepository implements BaseViewModel {

    private final List<Source> sourceList;
    private CompositeDisposable disposables = new CompositeDisposable();
    private final Api api;
    private final MutableLiveData<RestData<List<ContactSuggest>>> articleMutableLiveData;
    private final MutableLiveData<RestData<List<Contact>>> contactMutableLiveData;
    private final AppSchedulerProvider schedulerProvider;
    private final SourceDao sourceDao;
    private final ArticleDao articleDao;


    @Inject
    AllContactSuggestRepository(AppDatabase database, Api api, AppSchedulerProvider schedulerProvider) {
        this.api = api;
        this.schedulerProvider = schedulerProvider;
        sourceDao = database.sourceDao();
        articleDao = database.articleDao();
        sourceList = sourceDao.getAllList();
        articleMutableLiveData = new MutableLiveData<>();
        contactMutableLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<RestData<List<ContactSuggest>>> getAllContactSuggest() {
        api.getAllContactSuggest(ConstantsApp.BASE64_HEADER)
                .observeOn(schedulerProvider.ui())
                .subscribeOn(schedulerProvider.io())
                .map(data -> data)
                .subscribe(new Observer<RestData<List<ContactSuggest>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposables.add(d);
                    }

                    @Override
                    public void onNext(RestData<List<ContactSuggest>> sources) {
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


    public MutableLiveData<RestData<List<Contact>>> getAllContact() {
        api.getAllContact(ConstantsApp.BASE64_HEADER)
                .observeOn(schedulerProvider.ui())
                .subscribeOn(schedulerProvider.io())
                .map(data -> data)
                .subscribe(new Observer<RestData<List<Contact>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposables.add(d);
                    }

                    @Override
                    public void onNext(RestData<List<Contact>> sources) {
                        contactMutableLiveData.postValue(sources);
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                    }
                });
        return contactMutableLiveData;
    }

    @Override
    public void onClear() {
        disposables.clear();
    }
}
