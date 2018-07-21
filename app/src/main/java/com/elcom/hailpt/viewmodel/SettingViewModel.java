package com.elcom.hailpt.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import com.elcom.hailpt.model.api.RestData;
import com.elcom.hailpt.model.api.response.Support;
import com.elcom.hailpt.repository.SettingRepository;
import com.google.gson.JsonElement;

import javax.inject.Inject;

/**
 * @author ihsan on 12/27/17.
 */

public class SettingViewModel extends ViewModel {

    private final MutableLiveData<String> requestLogout = new MutableLiveData<>();
    private final LiveData<RestData<JsonElement>> logoutResult;
    private final LiveData<RestData<Support>> supportResult;
    private final SettingRepository repository;

    @Inject
    SettingViewModel(SettingRepository repository) {
        this.repository = repository;
        logoutResult = Transformations.switchMap(requestLogout,
                param -> repository.logout(requestLogout.getValue()));

        supportResult = repository.getSupport();
    }

    public LiveData<RestData<JsonElement>> logout() {
        return logoutResult;
    }

    public LiveData<RestData<Support>> getSupport() {
        return supportResult;
    }

    public void setRequest(String request){
        requestLogout.setValue(request);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        repository.onClear();
    }
}
