package com.example.arc.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import com.example.arc.model.api.RestData;
import com.example.arc.model.api.request.ChangePwRq;
import com.example.arc.model.api.response.Contact;
import com.example.arc.model.api.response.ContactSuggest;
import com.example.arc.repository.AllContactSuggestRepository;
import com.example.arc.repository.SettingChangePasswordRepository;
import com.google.gson.JsonElement;

import java.util.List;

import javax.inject.Inject;

/**
 * @author ihsan on 12/27/17.
 */

public class SettingChangePasswordViewModel extends ViewModel {

    private final MutableLiveData<ChangePwRq> requestChangePwRq = new MutableLiveData<>();
    private final LiveData<RestData<JsonElement>> changePwResult;
    private final SettingChangePasswordRepository repository;

    @Inject
    SettingChangePasswordViewModel(SettingChangePasswordRepository repository) {
        this.repository = repository;
        changePwResult = Transformations.switchMap(requestChangePwRq,
                param -> repository.changePw(requestChangePwRq.getValue()));
    }

    public LiveData<RestData<JsonElement>> getChangePwResult() {
        return changePwResult;
    }

    public void setChangePwRequest(ChangePwRq changePwRequest){
        requestChangePwRq.setValue(changePwRequest);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        repository.onClear();
    }
}
