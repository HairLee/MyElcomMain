package com.elcom.hailpt.view.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.elcom.hailpt.R;
import com.elcom.hailpt.util.ConstantsApp;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

/**
 * Created by TRAM-USER on 11/7/2016.
 */

public class CropImageActivity extends AppCompatActivity implements View.OnClickListener, CropImageView.OnSetImageUriCompleteListener, CropImageView.OnCropImageCompleteListener {

    private static final String TAG = CropImageActivity.class.getName();
    private CropImageView mCropImageView;
    private LinearLayout btnCrop;
    private String uriStr;
    static Uri uri;
    public static Bitmap bitmapCrop;
    private RelativeLayout layoutBack;

    private LinearLayout imgRotate;
    private boolean isChangeAvaOrCover = false;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_image);
        initView();
        initData();
    }



    protected void initView() {
        mCropImageView = findViewById(R.id.crop_image_view);
        mCropImageView.setOnSetImageUriCompleteListener(this);
        mCropImageView.setOnCropImageCompleteListener(this);
        mCropImageView.setImageResource(R.drawable.defaul_ava);



        btnCrop =  findViewById(R.id.lnUpload);
        btnCrop.setOnClickListener(this);

//        layoutBack = (RelativeLayout) findViewById(R.id.layout_back);
//        layoutBack.setOnClickListener(this);

        imgRotate =  findViewById(R.id.lnRote);
        imgRotate.setOnClickListener(this);

        setCropImageViewOptions();

    }


    protected void initData() {
//        uriStr = getIntent().getStringExtra(ConstantsApp.EXTRA_URI_STR);
//        uri = Uri.parse(uriStr);
        mCropImageView.setImageUriAsync(uri);
        Log.e("hailpt"," CropImageView ~~~ String  "+uriStr);
        Log.e("hailpt"," CropImageView ~~~ Uri "+uri);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.lnUpload:
                mCropImageView.getCroppedImageAsync();
                break;

//            case R.id.layout_back:
//                finish();
//                break;

            case R.id.lnRote:
                mCropImageView.rotateImage(90);
                break;

        }
    }

    /** Set the options of the crop image view to the given values. */
    public void setCropImageViewOptions() {

        CropImageViewOptions options = new CropImageViewOptions();
        options.scaleType = mCropImageView.getScaleType();
        options.cropShape = mCropImageView.getCropShape();
        options.guidelines = mCropImageView.getGuidelines();
        options.aspectRatio = mCropImageView.getAspectRatio();
        options.fixAspectRatio = mCropImageView.isFixAspectRatio();
        options.showCropOverlay = mCropImageView.isShowCropOverlay();
        options.showProgressBar = mCropImageView.isShowProgressBar();
        options.autoZoomEnabled = mCropImageView.isAutoZoomEnabled();
        options.maxZoomLevel = mCropImageView.getMaxZoom();
        options.flipHorizontally = mCropImageView.isFlippedHorizontally();
        options.flipVertically = mCropImageView.isFlippedVertically();

        mCropImageView.setScaleType(options.scaleType);
        mCropImageView.setCropShape(options.cropShape);
        mCropImageView.setGuidelines(options.guidelines);
        mCropImageView.setAspectRatio(options.aspectRatio.first, options.aspectRatio.second);
        mCropImageView.setFixedAspectRatio(options.fixAspectRatio);
        mCropImageView.setMultiTouchEnabled(options.multitouch);
        mCropImageView.setShowCropOverlay(options.showCropOverlay);
        mCropImageView.setShowProgressBar(options.showProgressBar);
        mCropImageView.setAutoZoomEnabled(options.autoZoomEnabled);
        mCropImageView.setMaxZoom(options.maxZoomLevel);
        mCropImageView.setFlippedHorizontally(options.flipHorizontally);
        mCropImageView.setFlippedVertically(options.flipVertically);
    }

    /**
     * Set the initial rectangle to use.
     */
    public void setInitialCropRect() {
        mCropImageView.setCropRect(new Rect(100, 300, 1100, 1300));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            handleCropResult(result);
        }
    }

    private void handleCropResult(CropImageView.CropResult result) {
        if (result.getError() == null) {
            if (result.getUri() != null) {


            } else {

                bitmapCrop = mCropImageView.getCropShape() == CropImageView.CropShape.OVAL
                        ? CropImage.toOvalBitmap(result.getBitmap())
                        : result.getBitmap();
                ProfileFavouriteActivity.mImage = bitmapCrop;
//                mCropImageView.setImageBitmap(mCropImageView);

                Intent returnIntent = new Intent();
                setResult(ConstantsApp.RESULT_CODE_CROP_IMAGE,returnIntent);
                finish();
            }
        } else {
            Log.e(TAG, "Failed to crop image", result.getError());
            Toast.makeText(this, "Image crop failed: " + result.getError().getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onCropImageComplete(CropImageView view, CropImageView.CropResult result) {
        handleCropResult(result);
    }

    @Override
    public void onSetImageUriComplete(CropImageView view, Uri uri, Exception error) {
//        Utils.showLongToast(this, "Crop success ok ....");
    }
}
