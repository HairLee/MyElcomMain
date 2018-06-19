package com.example.arc.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import com.example.arc.model.api.RestData;
import com.example.arc.model.api.request.LunchCancelReq;
import com.example.arc.model.api.request.LunchLikeReq;
import com.example.arc.model.api.request.TimeKeepReq;
import com.example.arc.model.api.response.Contact;
import com.example.arc.model.api.response.ContactSuggest;
import com.example.arc.model.api.response.Lunch;
import com.example.arc.repository.AllContactSuggestRepository;
import com.example.arc.repository.LunchRegistrationRepository;
import com.google.gson.JsonElement;

import java.util.List;

import javax.inject.Inject;

/**
 * @author ihsan on 12/27/17.
 */

public class LunchRegistrationViewModel extends ViewModel {
    private final MutableLiveData<LunchCancelReq> request = new MutableLiveData<>();
    private final MutableLiveData<TimeKeepReq> timeReq = new MutableLiveData<>();
    private final MutableLiveData<LunchLikeReq> likeLunchRequest = new MutableLiveData<>();

    private final LiveData<RestData<JsonElement>> timeKeepListResult;
    private final LiveData<RestData<JsonElement>> likeLunchResult;
    private final LiveData<RestData<List<Lunch>>> lunchListResult;
    private final LunchRegistrationRepository repository;

    @Inject
    LunchRegistrationViewModel(LunchRegistrationRepository repository) {
        this.repository = repository;
        timeKeepListResult = Transformations.switchMap(request,
                param -> repository.cancelLunch(request.getValue()));

        lunchListResult = Transformations.switchMap(timeReq,
                param -> repository.getLunchMenu(timeReq.getValue().getFromTime(),timeReq.getValue().getToTime()));


        likeLunchResult = Transformations.switchMap(likeLunchRequest,
                param -> repository.likeLunch(likeLunchRequest.getValue()));
    }

    public LiveData<RestData<JsonElement>> cancelLunch() {
        return timeKeepListResult;
    }

    public LiveData<RestData<List<Lunch>>> getLunchMenu() {
        return lunchListResult;
    }

    public LiveData<RestData<JsonElement>> getLikeLunch() {
        return likeLunchResult;
    }

    public void setRequest(LunchCancelReq lunchCancelReq){
        request.setValue(lunchCancelReq);
    }

    public void setLikeLunchRequest(LunchLikeReq lunchLikeReq){
        likeLunchRequest.setValue(lunchLikeReq);
    }

    public void setTimeRequest(TimeKeepReq timeRequest){
        timeReq.setValue(timeRequest);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        repository.onClear();
    }
}
