package com.example.arc.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.example.arc.core.AppSchedulerProvider;
import com.example.arc.core.BaseViewModel;
import com.example.arc.model.api.Api;
import com.example.arc.model.api.ApiResponse;
import com.example.arc.model.api.RestData;
import com.example.arc.model.api.request.LoginReq;
import com.example.arc.model.api.response.User;
import com.example.arc.model.data.Source;
import com.example.arc.model.data.Sources;
import com.example.arc.model.db.AppDatabase;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by Hailpt on 6/11/2018.
 */
public class LoginRepository implements BaseViewModel {

    private CompositeDisposable disposables = new CompositeDisposable();
    private final AppSchedulerProvider schedulerProvider;
    private final MutableLiveData<RestData<User>> userMutableLiveData;
    private Api api;

    @Inject
    LoginRepository(AppDatabase database, Api api, AppSchedulerProvider schedulerProvider) {
        this.api = api;
        this.schedulerProvider = schedulerProvider;
        userMutableLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<RestData<User>> login2(LoginReq loginReq) {
        api.login(loginReq)
                .observeOn(schedulerProvider.ui())
                .subscribeOn(schedulerProvider.io())
                .map(data -> data)
                .subscribe(new Observer<RestData<User>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposables.add(d);
                    }

                    @Override
                    public void onNext(RestData<User> sources) {
                        Log.e("hailpt"," login2 onNext ");
                        userMutableLiveData.postValue(sources);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("hailpt"," login2 onError "+e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.e("hailpt"," login2 onComplete");
                    }
                });
        return userMutableLiveData;
    }

    @Override
    public void onClear() {

    }
}
