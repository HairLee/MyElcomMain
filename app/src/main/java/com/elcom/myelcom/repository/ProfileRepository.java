package com.elcom.myelcom.repository;

import android.arch.lifecycle.MutableLiveData;

import com.elcom.myelcom.core.AppSchedulerProvider;
import com.elcom.myelcom.core.BaseViewModel;
import com.elcom.myelcom.model.api.Api;
import com.elcom.myelcom.model.api.RestData;
import com.elcom.myelcom.model.api.request.ChangeMobileReq;
import com.elcom.myelcom.model.api.request.ChangeStatusReq;
import com.elcom.myelcom.model.api.request.MarkUserReq;
import com.elcom.myelcom.model.api.response.User;
import com.elcom.myelcom.model.data.Source;
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
import okhttp3.MultipartBody;

/**
 * @author ihsan on 12/28/17.
 */

public class ProfileRepository implements BaseViewModel {

    private final List<Source> sourceList;
    private CompositeDisposable disposables = new CompositeDisposable();
    private final Api api;
    private final MutableLiveData<RestData<User>> userMutableLiveData;
    private final MutableLiveData<RestData<JsonElement>> markFriendMutableLiveData;
    private final MutableLiveData<RestData<JsonElement>> changeStatusMutableLiveData;
    private final MutableLiveData<RestData<User>> avatarMutableLiveData;
    private final AppSchedulerProvider schedulerProvider;
    private final SourceDao sourceDao;
    private final ArticleDao articleDao;

    @Inject
    ProfileRepository(AppDatabase database, Api api, AppSchedulerProvider schedulerProvider) {
        this.api = api;
        this.schedulerProvider = schedulerProvider;
        sourceDao = database.sourceDao();
        articleDao = database.articleDao();
        sourceList = sourceDao.getAllList();
        userMutableLiveData = new MutableLiveData<>();
        markFriendMutableLiveData = new MutableLiveData<>();
        avatarMutableLiveData = new MutableLiveData<>();
        changeStatusMutableLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<RestData<User>> getUserProfile(int userId) {
        api.getUserProfile(userId,ConstantsApp.BASE64_HEADER)
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
                        userMutableLiveData.postValue(sources);
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                    }
                });
        return userMutableLiveData;
    }

    public MutableLiveData<RestData<JsonElement>> markFriend(MarkUserReq markUserReq) {
        api.markFriend(markUserReq,ConstantsApp.BASE64_HEADER)
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
                        markFriendMutableLiveData.postValue(sources);
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                    }
                });
        return markFriendMutableLiveData;
    }

    public MutableLiveData<RestData<User>> uploadAvatar(MultipartBody.Part avatarPart) {
        api.uploadAvatar(avatarPart,ConstantsApp.BASE64_HEADER)
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
                        avatarMutableLiveData.postValue(sources);
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                    }
                });
        return avatarMutableLiveData;
    }

    public MutableLiveData<RestData<JsonElement>> updateMobile(ChangeMobileReq changeMobileReq) {
        api.changeMobile(changeMobileReq,ConstantsApp.BASE64_HEADER)
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
                        markFriendMutableLiveData.postValue(sources);
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                    }
                });
        return markFriendMutableLiveData;
    }


    public MutableLiveData<RestData<JsonElement>> changeStatus(ChangeStatusReq changeStatusReq) {
        api.changeStatus(changeStatusReq,ConstantsApp.BASE64_HEADER)
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
                        changeStatusMutableLiveData.postValue(sources);
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                    }
                });
        return changeStatusMutableLiveData;
    }





    @Override
    public void onClear() {
        disposables.clear();
    }
}
