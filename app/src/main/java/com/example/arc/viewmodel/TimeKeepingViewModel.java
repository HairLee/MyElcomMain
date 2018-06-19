package com.example.arc.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import com.example.arc.model.api.RestData;
import com.example.arc.model.api.request.LoginReq;
import com.example.arc.model.api.request.TimeKeepReq;
import com.example.arc.model.api.response.User;
import com.example.arc.model.data.Article;
import com.example.arc.model.data.Source;
import com.example.arc.model.data.TimeKeep;
import com.example.arc.repository.DataRepository;
import com.example.arc.repository.TimeKeepingRepository;

import java.util.List;

import javax.inject.Inject;

/**
 * @author ihsan on 12/27/17.
 */

public class TimeKeepingViewModel extends ViewModel {

    private final MutableLiveData<TimeKeepReq> timeKeepReq = new MutableLiveData<>();
    private final LiveData<RestData<List<TimeKeep>>> timeKeepListResult;
    private final TimeKeepingRepository repository;

    @Inject
    TimeKeepingViewModel(TimeKeepingRepository repository) {
        this.repository = repository;
        timeKeepListResult = Transformations.switchMap(timeKeepReq,
                param -> repository.getTimeKeeping(timeKeepReq.getValue().getFromTime(),timeKeepReq.getValue().getToTime()));
    }

    public LiveData<RestData<List<TimeKeep>>> getTimeKeepingList() {
        return timeKeepListResult;
    }

    public void setLoginParam(TimeKeepReq timeKeepReq) {
        this.timeKeepReq.setValue(timeKeepReq);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        repository.onClear();
    }
}
