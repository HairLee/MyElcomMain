package com.example.arc.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import com.example.arc.model.api.RestData;
import com.example.arc.model.api.request.LunchCancelReq;
import com.example.arc.model.api.request.LunchLikeReq;
import com.example.arc.model.api.response.Contact;
import com.example.arc.model.api.response.ContactSuggest;
import com.example.arc.model.api.response.User;
import com.example.arc.repository.AllContactSuggestRepository;
import com.example.arc.repository.ProfileRepository;

import java.util.List;

import javax.inject.Inject;

/**
 * @author ihsan on 12/27/17.
 */

public class ProfileViewModel extends ViewModel {
    private final MutableLiveData<String> request = new MutableLiveData<>();
    private final LiveData<RestData<User>> userResult;
    private final ProfileRepository repository;

    @Inject
    ProfileViewModel(ProfileRepository repository) {
        this.repository = repository;
        userResult = Transformations.switchMap(request,
                param -> repository.getUserProfile(request.getValue()));
    }

    public LiveData<RestData<User>> getUserProfile() {
        return userResult;
    }

    public void setLikeLunchRequest(String lunchLikeReq){
        request.setValue(lunchLikeReq);
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        repository.onClear();
    }
}
