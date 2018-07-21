package com.elcom.hailpt.view.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.elcom.hailpt.R;

public class UpdateMobileActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_mobile);
    }


//    public MutableLiveData<RestData<List<ContactSuggest>>> getAllContactSuggest() {
//        api.getAllContactSuggest(ConstantsApp.BASE64_HEADER)
//                .observeOn(schedulerProvider.ui())
//                .subscribeOn(schedulerProvider.io())
//                .map(data -> data)
//                .subscribe(new Observer<RestData<List<ContactSuggest>>>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//                        disposables.add(d);
//                    }
//
//                    @Override
//                    public void onNext(RestData<List<ContactSuggest>> sources) {
//                        articleMutableLiveData.postValue(sources);
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                    }
//
//                    @Override
//                    public void onComplete() {
//                    }
//                });
//        return articleMutableLiveData;
//    }
}
