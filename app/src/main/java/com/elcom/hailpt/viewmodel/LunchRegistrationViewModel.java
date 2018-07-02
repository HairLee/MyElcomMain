package com.elcom.hailpt.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import com.elcom.hailpt.model.api.RestData;
import com.elcom.hailpt.model.api.request.LunchCancelReq;
import com.elcom.hailpt.model.api.request.LunchFeedBackReq;
import com.elcom.hailpt.model.api.request.LunchLikeReq;
import com.elcom.hailpt.model.api.request.TimeKeepReq;
import com.elcom.hailpt.model.api.response.Contact;
import com.elcom.hailpt.model.api.response.ContactSuggest;
import com.elcom.hailpt.model.api.response.Lunch;
import com.elcom.hailpt.repository.AllContactSuggestRepository;
import com.elcom.hailpt.repository.LunchRegistrationRepository;
import com.google.gson.JsonElement;

import java.util.List;

import javax.inject.Inject;

/**
 * @author ihsan on 12/27/17.
 */

public class LunchRegistrationViewModel extends ViewModel {
    private final MutableLiveData<LunchCancelReq> request = new MutableLiveData<>();
    private final MutableLiveData<LunchCancelReq> requestRegisterLunch = new MutableLiveData<>();
    private final MutableLiveData<TimeKeepReq> timeReq = new MutableLiveData<>();
    private final MutableLiveData<LunchLikeReq> likeLunchRequest = new MutableLiveData<>();
    private final MutableLiveData<LunchFeedBackReq> feedBackLunchRequest = new MutableLiveData<>();

    private final LiveData<RestData<JsonElement>> timeKeepListResult;
    private final LiveData<RestData<JsonElement>> feedBackLunchListResult;
    private final LiveData<RestData<JsonElement>> registerLunchResult;
    private final LiveData<RestData<JsonElement>> likeLunchResult;
    private final LiveData<RestData<List<Lunch>>> lunchListResult;
    private final LunchRegistrationRepository repository;

    @Inject
    LunchRegistrationViewModel(LunchRegistrationRepository repository) {
        this.repository = repository;

        registerLunchResult = Transformations.switchMap(requestRegisterLunch,
                param -> repository.registerLunch(requestRegisterLunch.getValue()));

        timeKeepListResult = Transformations.switchMap(request,
                param -> repository.cancelLunch(request.getValue()));

        lunchListResult = Transformations.switchMap(timeReq,
                param -> repository.getLunchMenu(timeReq.getValue().getFromTime(),timeReq.getValue().getToTime()));


        likeLunchResult = Transformations.switchMap(likeLunchRequest,
                param -> repository.likeLunch(likeLunchRequest.getValue()));

        feedBackLunchListResult = Transformations.switchMap(feedBackLunchRequest,
                param -> repository.sendLunchFeedBack(feedBackLunchRequest.getValue()));
    }

    public LiveData<RestData<JsonElement>> cancelLunch() {
        return timeKeepListResult;
    }

    public LiveData<RestData<JsonElement>> registerLunch() {
        return registerLunchResult;
    }

    public LiveData<RestData<List<Lunch>>> getLunchMenu() {
        return lunchListResult;
    }

    public LiveData<RestData<JsonElement>> getLikeLunch() {
        return likeLunchResult;
    }

    public LiveData<RestData<JsonElement>> sendFeedBackLunch() {
        return feedBackLunchListResult;
    }

    public void setRequest(LunchCancelReq lunchCancelReq){
        request.setValue(lunchCancelReq);
    }

    public void setRegisterLunchRequest(LunchCancelReq lunchCancelReq){
        requestRegisterLunch.setValue(lunchCancelReq);
    }

    public void setLikeLunchRequest(LunchLikeReq lunchLikeReq){
        likeLunchRequest.setValue(lunchLikeReq);
    }

    public void setFeedBackunchRequest(LunchFeedBackReq feedBackunchRequest){
        feedBackLunchRequest.setValue(feedBackunchRequest);
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
