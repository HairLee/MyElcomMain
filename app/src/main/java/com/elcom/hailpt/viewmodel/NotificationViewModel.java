package com.elcom.hailpt.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.elcom.hailpt.model.api.RestData;
import com.elcom.hailpt.model.api.response.Contact;
import com.elcom.hailpt.model.api.response.ContactSuggest;
import com.elcom.hailpt.model.api.response.Notification;
import com.elcom.hailpt.repository.AllContactSuggestRepository;
import com.elcom.hailpt.repository.NotificationRepository;

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
