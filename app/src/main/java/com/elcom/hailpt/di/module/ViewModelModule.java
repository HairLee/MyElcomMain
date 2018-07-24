package com.elcom.hailpt.di.module;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.elcom.hailpt.di.ViewModelKey;
import com.elcom.hailpt.viewmodel.AllContactSuggestViewModel;
import com.elcom.hailpt.viewmodel.ContactFavouriteViewModel;
import com.elcom.hailpt.viewmodel.ContactOnlineViewModel;
import com.elcom.hailpt.viewmodel.DetailViewModel;
import com.elcom.hailpt.viewmodel.ForgetPasswordViewModel;
import com.elcom.hailpt.viewmodel.LoginViewModel;
import com.elcom.hailpt.viewmodel.LunchRegistrationViewModel;
import com.elcom.hailpt.viewmodel.MainViewModel;
import com.elcom.hailpt.viewmodel.NewsDetailViewModel;
import com.elcom.hailpt.viewmodel.NewsViewModel;
import com.elcom.hailpt.viewmodel.NotificationViewModel;
import com.elcom.hailpt.viewmodel.ProfileFavouriteViewModel;
import com.elcom.hailpt.viewmodel.ProfileViewModel;
import com.elcom.hailpt.viewmodel.SettingChangePasswordViewModel;
import com.elcom.hailpt.viewmodel.SettingViewModel;
import com.elcom.hailpt.viewmodel.SourceViewModel;
import com.elcom.hailpt.viewmodel.TimeKeepingViewModel;
import com.elcom.hailpt.viewmodel.ViewModelFactory;

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
