package com.elcom.hailpt.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import com.elcom.hailpt.model.api.RestData;
import com.elcom.hailpt.model.api.request.RemoveNotificationReq;
import com.elcom.hailpt.model.api.response.Notification;
import com.elcom.hailpt.repository.NotificationRepository;
import com.google.gson.JsonElement;

import java.util.List;

import javax.inject.Inject;

/**
 * @author ihsan on 12/27/17.
 */

public class NotificationViewModel extends ViewModel {

    private final LiveData<RestData<List<Notification>>> timeNotificationResult;

    private final LiveData<RestData<JsonElement>> removeNotificationResult;

    private final MutableLiveData<RemoveNotificationReq> removeNotificationRq = new MutableLiveData<>();
    private final NotificationRepository repository;

    @Inject
    NotificationViewModel(NotificationRepository repository) {
        this.repository = repository;
        timeNotificationResult = repository.getAllNotification();

        removeNotificationResult = Transformations.switchMap(removeNotificationRq,
                param -> repository.removeNotification(removeNotificationRq.getValue()));
    }

    public LiveData<RestData<List<Notification>>> getAllNotification() {
        return timeNotificationResult;
    }

    public  LiveData<RestData<JsonElement>> getRemoveNotificationRq(){
        return removeNotificationResult;
    }

    public void setRemoveNotificationRq(RemoveNotificationReq pRemoveNotificationRq){
        removeNotificationRq.setValue(pRemoveNotificationRq);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        repository.onClear();
    }
}
