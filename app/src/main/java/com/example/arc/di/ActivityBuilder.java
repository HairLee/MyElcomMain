package com.example.arc.di;

import com.example.arc.view.ui.DetailActivity;
import com.example.arc.view.ui.LoginActivity;
import com.example.arc.view.ui.MainActivity;
import com.example.arc.view.ui.SourcesActivity;
import com.example.arc.view.ui.activity.LunchRegistrationActivity;
import com.example.arc.view.ui.activity.NotificationActivity;
import com.example.arc.view.ui.activity.ProfileActivity;
import com.example.arc.view.ui.activity.ProfileFavouriteActivity;
import com.example.arc.view.ui.activity.SettingActivity;
import com.example.arc.view.ui.activity.SettingChangePwActivity;
import com.example.arc.view.ui.activity.TimeKeepingActivity;
import com.example.arc.view.ui.fragment.TimeKeepingFragment;
import com.example.arc.view.ui.fragment.contact.AllContactFragment;
import com.example.arc.view.ui.fragment.contact.FavouriteContactFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * @author ihsan on 12/2/17.
 */

@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector
    abstract SourcesActivity bindSourceActivity();

    @ContributesAndroidInjector
    abstract MainActivity bindMainActivity();

    @ContributesAndroidInjector
    abstract DetailActivity bindDetailActivity();

    @ContributesAndroidInjector
    abstract LoginActivity bindLoginActivity();

    @ContributesAndroidInjector
    abstract TimeKeepingActivity bindTimeKeepingActivity();

    @ContributesAndroidInjector
    abstract LunchRegistrationActivity bindLunchRegistrationActivity();

    @ContributesAndroidInjector
    abstract ProfileActivity bindProfileActivity();

    @ContributesAndroidInjector
    abstract ProfileFavouriteActivity bindProfileFavouriteActivity();

    @ContributesAndroidInjector
    abstract SettingChangePwActivity bindSettingChangePwActivity();

    @ContributesAndroidInjector
    abstract NotificationActivity bindNotificationActivity();

    @ContributesAndroidInjector
    abstract SettingActivity bindSettingActivity();




    // Fragment
    @ContributesAndroidInjector
    abstract TimeKeepingFragment bindTimeKeepingFragment();

    @ContributesAndroidInjector
    abstract AllContactFragment bindAllContactFragment();

    @ContributesAndroidInjector
    abstract FavouriteContactFragment bindFavouriteContactFragment();

}
