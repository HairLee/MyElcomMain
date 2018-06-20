package com.example.arc.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.arc.model.api.RestData;
import com.example.arc.model.api.response.Contact;
import com.example.arc.model.api.response.ContactSuggest;
import com.example.arc.model.api.response.Notification;
import com.example.arc.repository.AllContactSuggestRepository;
import com.example.arc.repository.NotificationRepository;

import java.util.List;

import javax.inject.Inject;

/**
 * @author ihsan on 12/27/17.
 */

public class NotificationViewModel extends ViewModel {

    private final LiveData<RestData<List<Notification>>> timeNotificationResult;
    private final NotificationRepository repository;

    @Inject
    NotificationViewModel(NotificationRepository repository) {
        this.repository = repository;
        timeNotificationResult = repository.getAllNotification();
    }

    public LiveData<RestData<List<Notification>>> getAllNotification() {
        return timeNotificationResult;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        repository.onClear();
    }
}
