package com.elcom.hailpt.di;

import com.elcom.hailpt.view.ui.DetailActivity;
import com.elcom.hailpt.view.ui.LoginActivity;
import com.elcom.hailpt.view.ui.MainActivity;
import com.elcom.hailpt.view.ui.SourcesActivity;
import com.elcom.hailpt.view.ui.activity.ForgetPasswordActivity;
import com.elcom.hailpt.view.ui.activity.LunchRegistrationActivity;
import com.elcom.hailpt.view.ui.activity.NotificationActivity;
import com.elcom.hailpt.view.ui.activity.ProfileActivity;
import com.elcom.hailpt.view.ui.activity.ProfileFavouriteActivity;
import com.elcom.hailpt.view.ui.activity.SettingActivity;
import com.elcom.hailpt.view.ui.activity.SettingChangePwActivity;
import com.elcom.hailpt.view.ui.activity.TimeKeepingActivity;
import com.elcom.hailpt.view.ui.fragment.TimeKeepingFragment;
import com.elcom.hailpt.view.ui.fragment.contact.AllContactFragment;
import com.elcom.hailpt.view.ui.fragment.contact.FavouriteContactFragment;

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

    @ContributesAndroidInjector
    abstract ForgetPasswordActivity bindForgetPasswordActivity();



    // Fragment
    @ContributesAndroidInjector
    abstract TimeKeepingFragment bindTimeKeepingFragment();

    @ContributesAndroidInjector
    abstract AllContactFragment bindAllContactFragment();

    @ContributesAndroidInjector
    abstract FavouriteContactFragment bindFavouriteContactFragment();

}