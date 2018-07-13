package com.elcom.hailpt.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import com.elcom.hailpt.model.api.RestData;
import com.elcom.hailpt.model.api.request.LoginReq;
import com.elcom.hailpt.model.api.request.MonthReq;
import com.elcom.hailpt.model.api.request.ReasonLate;
import com.elcom.hailpt.model.api.request.TimeKeepReq;
import com.elcom.hailpt.model.api.response.User;
import com.elcom.hailpt.model.data.Article;
import com.elcom.hailpt.model.data.Source;
import com.elcom.hailpt.model.data.Statistic;
import com.elcom.hailpt.model.data.TimeKeep;
import com.elcom.hailpt.repository.DataRepository;
import com.elcom.hailpt.repository.TimeKeepingRepository;
import com.google.gson.JsonElement;

import java.util.List;

import javax.inject.Inject;

/**
 * @author ihsan on 12/27/17.
 */

public class TimeKeepingViewModel extends ViewModel {

    private final MutableLiveData<TimeKeepReq> timeKeepReq = new MutableLiveData<>();
    private final MutableLiveData<MonthReq> monthReq = new MutableLiveData<>();
    private final MutableLiveData<ReasonLate> reasonLate = new MutableLiveData<>();
    private final LiveData<RestData<List<TimeKeep>>> timeKeepListResult;
    private final LiveData<RestData<Statistic>> statisticResult;
    private final LiveData<RestData<JsonElement>> reasonLateResult;
    private final TimeKeepingRepository repository;

    @Inject
    TimeKeepingViewModel(TimeKeepingRepository repository) {
        this.repository = repository;
        timeKeepListResult = Transformations.switchMap(timeKeepReq,
                param -> repository.getTimeKeeping(timeKeepReq.getValue().getFromTime(),timeKeepReq.getValue().getToTime()));

        statisticResult = Transformations.switchMap(monthReq,
                param -> repository.getMonthInformation(monthReq.getValue().getYear(),monthReq.getValue().getMonth()));


        reasonLateResult = Transformations.switchMap(reasonLate,
                param -> repository.reasonLate(reasonLate.getValue()));
    }

    public LiveData<RestData<List<TimeKeep>>> getTimeKeepingList() {
        return timeKeepListResult;
    }

    public void setLoginParam(TimeKeepReq timeKeepReq) {
        this.timeKeepReq.setValue(timeKeepReq);
    }

    public LiveData<RestData<Statistic>> getMonthInformation() {
        return statisticResult;
    }

    public LiveData<RestData<JsonElement>> getReasonLate() {
        return reasonLateResult;
    }

    public void setMonthReq(MonthReq timeKeepReq) {
        this.monthReq.setValue(timeKeepReq);
    }

    public void setReasonLateReq(ReasonLate reasonLateReq) {
        this.reasonLate.setValue(reasonLateReq);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        repository.onClear();
    }
}
