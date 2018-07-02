package com.elcom.hailpt.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import com.elcom.hailpt.model.api.RestData;
import com.elcom.hailpt.model.api.response.Contact;
import com.elcom.hailpt.model.api.response.ContactSuggest;
import com.elcom.hailpt.repository.AllContactSuggestRepository;

import java.util.List;

import javax.inject.Inject;

/**
 * @author ihsan on 12/27/17.
 */

public class AllContactSuggestViewModel extends ViewModel {

    private final MutableLiveData<Integer> AllContactrequest = new MutableLiveData<>();

    private final LiveData<RestData<List<ContactSuggest>>> timeKeepListResult;
    private final LiveData<RestData<List<Contact>>> contactResult;
    private final AllContactSuggestRepository repository;

    @Inject
    AllContactSuggestViewModel(AllContactSuggestRepository repository) {
        this.repository = repository;
        timeKeepListResult = repository.getAllContactSuggest();

        contactResult = Transformations.switchMap(AllContactrequest,
                param -> repository.getAllContact());

    }

    public LiveData<RestData<List<ContactSuggest>>> getAllContactSuggest() {
        return timeKeepListResult;
    }

    public LiveData<RestData<List<Contact>>> getAllContact() {
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
