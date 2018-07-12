package com.elcom.hailpt.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import com.elcom.hailpt.model.api.RestData;
import com.elcom.hailpt.model.api.request.ForgetPwReq;
import com.elcom.hailpt.model.api.request.LoginReq;
import com.elcom.hailpt.model.api.response.User;
import com.elcom.hailpt.repository.ForgetPasswordRepository;
import com.elcom.hailpt.repository.LoginRepository;
import com.google.gson.JsonElement;

import javax.inject.Inject;

/**
 * @author ihsan on 12/27/17.
 */

public class ForgetPasswordViewModel extends ViewModel {

    private final MutableLiveData<ForgetPwReq> forgetPwReq = new MutableLiveData<>();
    private final LiveData<RestData<JsonElement>> forgetPwRes;
    private ForgetPasswordRepository repository;
    @Inject
    public ForgetPasswordViewModel(final ForgetPasswordRepository repository) {
        this.repository = repository;
        forgetPwRes = Transformations.switchMap(forgetPwReq,
                param -> repository.forgetPassword(forgetPwReq.getValue()));
    }

    public void setForgetPwReq(ForgetPwReq pForgetPwReq) {
        forgetPwReq.setValue(pForgetPwReq);
    }

    public LiveData<RestData<JsonElement>> getForgetPw() {
        return forgetPwRes;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        repository.onClear();
    }
}
