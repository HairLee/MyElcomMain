package com.elcom.hailpt.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.elcom.hailpt.model.api.RestData;
import com.elcom.hailpt.model.api.response.Contact;
import com.elcom.hailpt.model.api.response.ContactSuggest;
import com.elcom.hailpt.model.api.response.User;
import com.elcom.hailpt.repository.AllContactSuggestRepository;
import com.elcom.hailpt.repository.ContactFavouriteRepository;

import java.util.List;

import javax.inject.Inject;

/**
 * @author ihsan on 12/27/17.
 */

public class ContactFavouriteViewModel extends ViewModel {

    private final LiveData<RestData<List<User>>> contactResult;
    private final ContactFavouriteRepository repository;

    @Inject
    ContactFavouriteViewModel(ContactFavouriteRepository repository) {
        this.repository = repository;
        contactResult = repository.getFavouriteContact();
    }


    public LiveData<RestData<List<User>>> getFavouriteContact() {
        return contactResult;
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        repository.onClear();
    }
}