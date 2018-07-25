package com.elcom.myelcom.di;

import com.elcom.myelcom.view.ui.DetailActivity;
import com.elcom.myelcom.view.ui.LoginActivity;
import com.elcom.myelcom.view.ui.MainActivity;
import com.elcom.myelcom.view.ui.SourcesActivity;
import com.elcom.myelcom.view.ui.activity.ForgetPasswordActivity;
import com.elcom.myelcom.view.ui.activity.LunchCalendarRegisActivity;
import com.elcom.myelcom.view.ui.activity.LunchRegistrationActivity;
import com.elcom.myelcom.view.ui.activity.NotificationActivity;
import com.elcom.myelcom.view.ui.activity.ProfileActivity;
import com.elcom.myelcom.view.ui.activity.ProfileFavouriteActivity;
import com.elcom.myelcom.view.ui.activity.SettingActivity;
import com.elcom.myelcom.view.ui.activity.SettingChangePwActivity;
import com.elcom.myelcom.view.ui.activity.TimeKeepingActivity;
import com.elcom.myelcom.view.ui.activity.UpdateMobileActivity;
import com.elcom.myelcom.view.ui.fragment.HomeFragment;
import com.elcom.myelcom.view.ui.fragment.LunchRegistrationFragment;
import com.elcom.myelcom.view.ui.fragment.TimeKeepingFragment;
import com.elcom.myelcom.view.ui.fragment.contact.AllContactFragment;
import com.elcom.myelcom.view.ui.fragment.contact.FavouriteContactFragment;
import com.elcom.myelcom.view.ui.fragment.contact.OnlineContactFragment;
import com.elcom.myelcom.view.ui.fragment.news.NewsAllActivity;
import com.elcom.myelcom.view.ui.fragment.news.NewsDetailActivity;
import com.elcom.myelcom.view.ui.fragment.news.NewsFragment;

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

    @ContributesAndroidInjector
    abstract LunchCalendarRegisActivity bindLunchCalendarRegisActivity();

    @ContributesAndroidInjector
    abstract UpdateMobileActivity bindUpdateMobileActivity();

    @ContributesAndroidInjector
    abstract NewsAllActivity bindNewsAllActivity();

    @ContributesAndroidInjector
    abstract NewsDetailActivity bindNewsDetailActivity();



    // Fragment
    @ContributesAndroidInjector
    abstract TimeKeepingFragment bindTimeKeepingFragment();

    @ContributesAndroidInjector
    abstract AllContactFragment bindAllContactFragment();

    @ContributesAndroidInjector
    abstract FavouriteContactFragment bindFavouriteContactFragment();

    @ContributesAndroidInjector
    abstract LunchRegistrationFragment bindLunchRegistrationFragment();

    @ContributesAndroidInjector
    abstract OnlineContactFragment bindOnlineContactFragment();

    @ContributesAndroidInjector
    abstract NewsFragment bindNewsFragment();

    @ContributesAndroidInjector
    abstract HomeFragment bindHomeFragment();

}
