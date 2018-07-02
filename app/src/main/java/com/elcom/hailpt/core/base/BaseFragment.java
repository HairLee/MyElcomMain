package com.elcom.hailpt.core.base;

import android.app.ProgressDialog;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.view.KeyEvent;

import com.elcom.hailpt.util.SharedPrefsHelper;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;


/**
 * Created by Hailpt on 6/14/2018.
 */
public abstract class BaseFragment <M extends ViewModel> extends DaggerFragment {

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    public ProgressDialog progressDialog;
    public SharedPrefsHelper sharedPrefsHelper;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPrefsHelper = SharedPrefsHelper.getInstance();
        ViewModel viewModel = ViewModelProviders.of(this, viewModelFactory).get(getViewModel());
        onCreate(savedInstanceState, (M) viewModel);


    }

    protected abstract Class<M> getViewModel();

    protected abstract void onCreate(Bundle instance, M viewModel);

    public void showProgressDialog(@StringRes int messageId) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(getContext());
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(true);
            progressDialog.setCanceledOnTouchOutside(false);

            // Disable the back button
            DialogInterface.OnKeyListener keyListener = new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    return keyCode == KeyEvent.KEYCODE_BACK;
                }
            };
            progressDialog.setOnKeyListener(keyListener);
        }

        progressDialog.setMessage(getString(messageId));

        progressDialog.show();

    }

    public void hideProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }
}
