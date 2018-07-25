package com.elcom.myelcom.di.module;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.elcom.myelcom.di.ViewModelKey;
import com.elcom.myelcom.viewmodel.AllContactSuggestViewModel;
import com.elcom.myelcom.viewmodel.ContactFavouriteViewModel;
import com.elcom.myelcom.viewmodel.ContactOnlineViewModel;
import com.elcom.myelcom.viewmodel.DetailViewModel;
import com.elcom.myelcom.viewmodel.ForgetPasswordViewModel;
import com.elcom.myelcom.viewmodel.LoginViewModel;
import com.elcom.myelcom.viewmodel.LunchRegistrationViewModel;
import com.elcom.myelcom.viewmodel.MainViewModel;
import com.elcom.myelcom.viewmodel.NewsDetailViewModel;
import com.elcom.myelcom.viewmodel.NewsViewModel;
import com.elcom.myelcom.viewmodel.NotificationViewModel;
import com.elcom.myelcom.viewmodel.ProfileFavouriteViewModel;
import com.elcom.myelcom.viewmodel.ProfileViewModel;
import com.elcom.myelcom.viewmodel.SettingChangePasswordViewModel;
import com.elcom.myelcom.viewmodel.SettingViewModel;
import com.elcom.myelcom.viewmodel.SourceViewModel;
import com.elcom.myelcom.viewmodel.TimeKeepingViewModel;
import com.elcom.myelcom.viewmodel.ViewModelFactory;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

/**
 * @author ihsan on 12/27/17.
 */

@SuppressWarnings("WeakerAccess")
@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel.class)
    abstract ViewModel bindsMainViewModel(MainViewModel mainViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(SourceViewModel.class)
    abstract ViewModel bindsSourceViewModel(SourceViewModel sourceViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(DetailViewModel.class)
    abstract ViewModel bindsDetailViewModel(DetailViewModel sourceViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel.class)
    abstract ViewModel bindsLoginViewModel(LoginViewModel sourceViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(TimeKeepingViewModel.class)
    abstract ViewModel bindsTimeKeepingViewModel(TimeKeepingViewModel timeKeepingViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(AllContactSuggestViewModel.class)
    abstract ViewModel bindsAllContactSuggestViewModel(AllContactSuggestViewModel allContactSuggestViewModel);


    @Binds
    @IntoMap
    @ViewModelKey(ContactFavouriteViewModel.class)
    abstract ViewModel bindsContactFavouriteViewModel(ContactFavouriteViewModel contactFavouriteViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(LunchRegistrationViewModel.class)
    abstract ViewModel bindsLunchRegistrationViewModel(LunchRegistrationViewModel lunchRegistrationViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel.class)
    abstract ViewModel bindsProfileViewModel(ProfileViewModel profileViewModel);


    @Binds
    @IntoMap
    @ViewModelKey(ProfileFavouriteViewModel.class)
    abstract ViewModel bindsProfileFavouriteViewModel(ProfileFavouriteViewModel profileViewModel);


    @Binds
    @IntoMap
    @ViewModelKey(SettingChangePasswordViewModel.class)
    abstract ViewModel bindsSettingChangePasswordViewModel(SettingChangePasswordViewModel settingChangePasswordViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(NotificationViewModel.class)
    abstract ViewModel bindsNotificationViewModel(NotificationViewModel notificationViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(SettingViewModel.class)
    abstract ViewModel bindsSettingViewModel(SettingViewModel settingViewModel);


    @Binds
    @IntoMap
    @ViewModelKey(ForgetPasswordViewModel.class)
    abstract ViewModel bindsForgetPasswordViewModel(ForgetPasswordViewModel forgetPasswordViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ContactOnlineViewModel.class)
    abstract ViewModel bindsContactOnlineViewModel(ContactOnlineViewModel forgetPasswordViewModel);


    @Binds
    @IntoMap
    @ViewModelKey(NewsViewModel.class)
    abstract ViewModel bindsNewsViewModel(NewsViewModel newsViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(NewsDetailViewModel.class)
    abstract ViewModel bindsNewsDetailViewModel(NewsDetailViewModel newsViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindsViewModelFactory(ViewModelFactory viewModelFactory);



}
