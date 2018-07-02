package com.example.arc.view.dialog;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.arc.R;
import com.example.arc.core.listener.DialogYesNoListener;

/**
 * Created by Hailpt on 7/2/2018.
 */
public class LunchRegisDialog extends AlertDialog {

    private boolean isCancelLunch = false;
    private DialogYesNoListener dialogYesNoListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().getAttributes().windowAnimations = R.style.DialogAnimationRightLeft;
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.lunch_regis_dialog_item);

        ImageView imvDescription = findViewById(R.id.imvDescription);
        TextView tvTitle = findViewById(R.id.tvTitle);

        if (isCancelLunch){
            tvTitle.setText("Bạn có chắc muốn hủy đăng \n" +
                    "ký cơm trưa ?");
            imvDescription.setImageResource(R.drawable.lunch_cancel_ic);
        } else {
            tvTitle.setText("Đăng ký ăn trưa trước 09:00AM hàng ngày");
            imvDescription.setImageResource(R.drawable.lunch_regis_ic);
        }

        Button btnNo = findViewById(R.id.btnNo);
        Button btnYes = findViewById(R.id.btnOK);
        btnNo.setOnClickListener(v -> dismiss());
        btnYes.setOnClickListener(v -> {
            dialogYesNoListener.doYes();
            dismiss();
        });


    }

    public LunchRegisDialog(@NonNull Context context, boolean isCancelLunch, DialogYesNoListener dialogYesNoListener) {
        super(context);
        this.isCancelLunch = isCancelLunch;
        this.dialogYesNoListener = dialogYesNoListener;
    }

    protected LunchRegisDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected LunchRegisDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }
}
