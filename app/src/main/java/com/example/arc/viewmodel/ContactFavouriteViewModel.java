package com.example.arc.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.arc.model.api.RestData;
import com.example.arc.model.api.response.Contact;
import com.example.arc.model.api.response.ContactSuggest;
import com.example.arc.model.api.response.User;
import com.example.arc.repository.AllContactSuggestRepository;
import com.example.arc.repository.ContactFavouriteRepository;

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
