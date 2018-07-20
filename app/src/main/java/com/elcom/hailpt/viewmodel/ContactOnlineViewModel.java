package com.elcom.hailpt.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import com.elcom.hailpt.model.api.RestData;
import com.elcom.hailpt.model.api.response.User;
import com.elcom.hailpt.repository.ContactFavouriteRepository;
import com.elcom.hailpt.repository.ContactOnlineRepository;

import java.util.List;

import javax.inject.Inject;

/**
 * @author ihsan on 12/27/17.
 */

public class ContactOnlineViewModel extends ViewModel {

    private final LiveData<RestData<List<User>>> contactResult;
    private final ContactOnlineRepository repository;
    private final MutableLiveData<Integer> AllContactrequest = new MutableLiveData<>();
    @Inject
    ContactOnlineViewModel(ContactOnlineRepository repository) {
        this.repository = repository;

        contactResult = Transformations.switchMap(AllContactrequest,
                param ->  repository.getOnlineContact());
    }


    public LiveData<RestData<List<User>>> getOnlineContact() {
        return contactResult;
    }

    public void setAllContactrequest(){
        AllContactrequest.setValue(1);
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        repository.onClear();
    }
}
