package com.example.arc.di.module;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.example.arc.di.ViewModelKey;
import com.example.arc.viewmodel.AllContactSuggestViewModel;
import com.example.arc.viewmodel.ContactFavouriteViewModel;
import com.example.arc.viewmodel.DetailViewModel;
import com.example.arc.viewmodel.ForgetPasswordViewModel;
import com.example.arc.viewmodel.LoginViewModel;
import com.example.arc.viewmodel.LunchRegistrationViewModel;
import com.example.arc.viewmodel.MainViewModel;
import com.example.arc.viewmodel.NotificationViewModel;
import com.example.arc.viewmodel.ProfileFavouriteViewModel;
import com.example.arc.viewmodel.ProfileViewModel;
import com.example.arc.viewmodel.SettingChangePasswordViewModel;
import com.example.arc.viewmodel.SettingViewModel;
import com.example.arc.viewmodel.SourceViewModel;
import com.example.arc.viewmodel.TimeKeepingViewModel;
import com.example.arc.viewmodel.ViewModelFactory;

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
    abstract ViewModelProvider.Factory bindsViewModelFactory(ViewModelFactory viewModelFactory);



}
