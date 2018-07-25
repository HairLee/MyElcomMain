package com.elcom.myelcom.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import com.elcom.myelcom.model.api.RestData;
import com.elcom.myelcom.model.api.response.User;
import com.elcom.myelcom.repository.ProfileRepository;

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
