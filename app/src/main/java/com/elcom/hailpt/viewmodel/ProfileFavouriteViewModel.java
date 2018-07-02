package com.elcom.hailpt.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import com.elcom.hailpt.model.api.RestData;
import com.elcom.hailpt.model.api.request.MarkUserReq;
import com.elcom.hailpt.model.api.response.User;
import com.elcom.hailpt.repository.ProfileRepository;
import com.google.gson.JsonElement;

import javax.inject.Inject;

import okhttp3.MultipartBody;

/**
 * @author ihsan on 12/27/17.
 */

public class ProfileFavouriteViewModel extends ViewModel {
    private final MutableLiveData<Integer> request = new MutableLiveData<>();
    private final MutableLiveData<MarkUserReq> markUserReq = new MutableLiveData<>();
    private final MutableLiveData<MultipartBody.Part> avatarReq = new MutableLiveData<>();

    private final LiveData<RestData<User>> userResult;
    private final LiveData<RestData<JsonElement>> markUserResponse;
    private final LiveData<RestData<User>> avatarResponse;
    private final ProfileRepository repository;

    @Inject
    ProfileFavouriteViewModel(ProfileRepository repository) {
        this.repository = repository;
        userResult = Transformations.switchMap(request,
                param -> repository.getUserProfile(request.getValue()));

        markUserResponse = Transformations.switchMap(markUserReq, param -> repository.markFriend(markUserReq.getValue()));


        avatarResponse = Transformations.switchMap(avatarReq, param -> repository.uploadAvatar(avatarReq.getValue()));

    }

    public LiveData<RestData<User>> getUserProfile() {
        return userResult;
    }

    public LiveData<RestData<JsonElement>> getMarkFriend() {
        return markUserResponse;
    }

    public LiveData<RestData<User>> uploadAvatar() {
        return avatarResponse;
    }

    public void setRequest(int userId){
        request.setValue(userId);
    }

    public void setMarkFriendRequest(MarkUserReq markFriendRequest){
        markUserReq.setValue(markFriendRequest);
    }

    public void setAvatarRequest(MultipartBody.Part avatarRequest){
        avatarReq.setValue(avatarRequest);
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        repository.onClear();
    }
}
