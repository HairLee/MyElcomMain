package com.example.arc.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.arc.model.api.RestData;
import com.example.arc.model.api.response.Contact;
import com.example.arc.model.api.response.ContactSuggest;
import com.example.arc.repository.AllContactSuggestRepository;

import java.util.List;

import javax.inject.Inject;

/**
 * @author ihsan on 12/27/17.
 */

public class AllContactSuggestViewModel extends ViewModel {

    private final LiveData<RestData<List<ContactSuggest>>> timeKeepListResult;
    private final LiveData<RestData<List<Contact>>> contactResult;
    private final AllContactSuggestRepository repository;

    @Inject
    AllContactSuggestViewModel(AllContactSuggestRepository repository) {
        this.repository = repository;
        timeKeepListResult = repository.getAllContactSuggest();
        contactResult = repository.getAllContact();
    }

    public LiveData<RestData<List<ContactSuggest>>> getAllContactSuggest() {
        return timeKeepListResult;
    }

    public LiveData<RestData<List<Contact>>> getAllContact() {
        return contactResult;
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        repository.onClear();
    }
}
