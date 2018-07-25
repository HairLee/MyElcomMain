package com.elcom.myelcom.view.dialog;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.widget.Button;
import android.widget.EditText;

import com.elcom.myelcom.R;
import com.elcom.myelcom.core.listener.ChangeMobilePhoneListener;

/**
 * Created by Hailpt on 7/2/2018.
 */
public class ChangMobileDialog extends AlertDialog {

    private ChangeMobilePhoneListener changeMobilePhoneListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        getWindow().getAttributes().windowAnimations = R.style.DialogAnimationRightLeft;
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.change_mobile_dialog_item);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);


        EditText edtMobile = findViewById(R.id.edtMobile);
        Button btnNo = findViewById(R.id.btnNo);
        Button btnYes = findViewById(R.id.btnOK);
        btnNo.setOnClickListener(v -> dismiss());
        btnYes.setOnClickListener(v -> {
            changeMobilePhoneListener.doChangeMobile(edtMobile.getText().toString());
            dismiss();
        });


    }

    public ChangMobileDialog(@NonNull Context context, ChangeMobilePhoneListener changeMobilePhoneListener) {
        super(context);
        this.changeMobilePhoneListener = changeMobilePhoneListener;
    }

    protected ChangMobileDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected ChangMobileDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }
}
