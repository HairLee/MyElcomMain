package com.example.arc.core.base;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.view.KeyEvent;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

/**
 * @author ihsan on 11/29/17.
 */

public abstract class BaseActivity<M extends ViewModel, B extends ViewDataBinding> extends DaggerAppCompatActivity {
    private ProgressDialog progressDialog;
    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @SuppressWarnings("unchecked")
    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewDataBinding binding = DataBindingUtil.setContentView(this, getLayoutResId());
        ViewModel viewModel = ViewModelProviders.of(this, viewModelFactory).get(getViewModel());
        onCreate(savedInstanceState, (M) viewModel, (B) binding);
    }

    protected abstract Class<M> getViewModel();

    protected abstract void onCreate(Bundle instance, M viewModel, B binding);

    protected abstract
    @LayoutRes
    int getLayoutResId();

    public void showProgressDialog(@StringRes int messageId) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(false);
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
