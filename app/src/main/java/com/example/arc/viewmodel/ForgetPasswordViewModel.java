package com.example.arc.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import com.example.arc.model.api.RestData;
import com.example.arc.model.api.request.LoginReq;
import com.example.arc.model.api.response.User;
import com.example.arc.repository.LoginRepository;

import javax.inject.Inject;

/**
 * @author ihsan on 12/27/17.
 */

public class ForgetPasswordViewModel extends ViewModel {

    private final MutableLiveData<LoginReq> loginParam = new MutableLiveData<>();
    private final LiveData<RestData<User>> loginResult;
    private LoginRepository repository;
    @Inject
    public ForgetPasswordViewModel(final LoginRepository repository) {
        this.repository = repository;
        loginResult = Transformations.switchMap(loginParam,
                param -> repository.login2(loginParam.getValue()));
    }

    public void setLoginParam(LoginReq loginReq) {
        loginParam.setValue(loginReq);
    }

    public LiveData<RestData<User>> getLoginResult() {
        return loginResult;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        repository.onClear();
    }
}
