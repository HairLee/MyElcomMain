package com.elcom.hailpt.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import com.elcom.hailpt.model.api.RestData;
import com.elcom.hailpt.model.api.request.LunchCancelReq;
import com.elcom.hailpt.model.api.request.LunchLikeReq;
import com.elcom.hailpt.model.api.response.Contact;
import com.elcom.hailpt.model.api.response.ContactSuggest;
import com.elcom.hailpt.model.api.response.User;
import com.elcom.hailpt.repository.AllContactSuggestRepository;
import com.elcom.hailpt.repository.ProfileRepository;

import java.util.List;

import javax.inject.Inject;

/**
 * @author ihsan on 12/27/17.
 */

public class ProfileViewModel extends ViewModel {
    private final MutableLiveData<Integer> request = new MutableLiveData<>();
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

    public void setRequest(int userId){
        request.setValue(userId);
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        repository.onClear();
    }
}
