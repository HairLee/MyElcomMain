package com.elcom.myelcom.view.ui.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.elcom.myelcom.R;
import com.elcom.myelcom.core.base.BaseActivity;
import com.elcom.myelcom.databinding.ActivityProfileFavouriteBinding;
import com.elcom.myelcom.model.api.RestData;
import com.elcom.myelcom.model.api.request.ChangeStatusReq;
import com.elcom.myelcom.model.api.request.MarkUserReq;
import com.elcom.myelcom.model.api.response.User;
import com.elcom.myelcom.services.CallService;
import com.elcom.myelcom.util.ConstantsApp;
import com.elcom.myelcom.util.Consts;
import com.elcom.myelcom.util.PermissionsChecker;
import com.elcom.myelcom.util.PreferUtils;
import com.elcom.myelcom.util.PushNotificationSender;
import com.elcom.myelcom.util.Toaster;
import com.elcom.myelcom.util.WebRtcSessionManager;
import com.elcom.myelcom.view.ui.CallActivity;
import com.elcom.myelcom.view.ui.PermissionsActivity;
import com.elcom.myelcom.viewmodel.ProfileFavouriteViewModel;
import com.google.gson.JsonElement;
import com.quickblox.chat.QBChatService;
import com.quickblox.users.model.QBUser;
import com.quickblox.videochat.webrtc.QBRTCClient;
import com.quickblox.videochat.webrtc.QBRTCSession;
import com.quickblox.videochat.webrtc.QBRTCTypes;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static android.os.Environment.getExternalStoragePublicDirectory;

public class ProfileFavouriteActivity extends BaseActivity<ProfileFavouriteViewModel,ActivityProfileFavouriteBinding> implements View.OnClickListener {

    private int userId = 0;
    private ActivityProfileFavouriteBinding binding;
    private ProfileFavouriteViewModel viewModel;
    private PermissionsChecker checker;
    private User user;
    static Bitmap mImage;
    private boolean isFirstTimeComeHere = true;
    @Override
    protected Class<ProfileFavouriteViewModel> getViewModel() {
        return ProfileFavouriteViewModel.class;
    }

    @Override
    protected void onCreate(Bundle instance, ProfileFavouriteViewModel viewModel, ActivityProfileFavouriteBinding binding) {
        this.binding = binding;
        binding.imageView12.setOnClickListener(this);
        binding.imageView13.setOnClickListener(this);
        binding.imageView14.setOnClickListener(this);
        checker = new PermissionsChecker(this);
        Intent intent = getIntent();
        if (intent.hasExtra(Consts.EXTRA_IS_USER_ID)){
            userId = intent.getIntExtra(Consts.EXTRA_IS_USER_ID,0);
            hideCallLayoutWithMySelfAccount();
        }

        init(viewModel);
        setupSpinner();
    }

    private void setupSpinner(){
        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter
                .createFromResource(this, R.array.brew_array,
                        R.layout.spinner_item);

        // Specify the layout to use when the list of choices appears
        staticAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner dynamicSpinner = (Spinner) findViewById(R.id.spinner);
        // Apply the adapter to the spinner
        dynamicSpinner.setAdapter(staticAdapter);

        dynamicSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                if(!isFirstTimeComeHere)
                {
                    updateStatus(position+1);
                }

                isFirstTimeComeHere = false;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_profile_favourite;
    }

    private void updateStatus(int type){
        ChangeStatusReq changeStatusReq = new ChangeStatusReq();
        changeStatusReq.setStatus(type);
        viewModel.setChangeStatusRequest(changeStatusReq);
    }

    private void init(ProfileFavouriteViewModel viewModel){



        this.viewModel = viewModel;
        viewModel.getUserProfile().observe(this, userRestData -> {
            if(userRestData != null){
//                hideProgressDialog();
                user = userRestData.data;

                if (user != null){
                    binding.setUser(user);
                    if(userRestData.data.getAvatar() != null){
                        Glide.with(this).load(userRestData.data.getAvatar())
                                .thumbnail(0.5f)
                                .into(binding.profileImage);
                    } else {
                        binding.profileImage.setImageDrawable(getResources().getDrawable(R.drawable.defaul_ava));
                    }

                    if(userRestData.data.getStatus_mark() == 1){
                        binding.imageView11.setImageResource(R.drawable.favor_ic);
                    } else {
                        binding.imageView11.setImageResource(R.drawable.favor_not_ic);
                    }

                    if (userRestData.data.getStatus() == 1){
                        binding.textView11.setText("Hoạt động");
                    } else if (userRestData.data.getStatus() == 2){
                        binding.textView11.setText("Ngoại tuyến");
                    } else  if (userRestData.data.getStatus() == 3){
                        binding.textView11.setText("Đang bận");
                    }

                    binding.textView6.setText("Tham gia từ : "+user.getDay_to_company());

                    binding.tvCompa.setText(user.getGroupName().toString());
                }

            }
        });

        viewModel.getMarkFriend().observe(this, jsonElementRestData -> {
            hideProgressDialog();
            if(jsonElementRestData != null && jsonElementRestData.message.contains("Mark successfully!")){
                binding.imageView11.setImageResource(R.drawable.favor_ic);
            } else {
                binding.imageView11.setImageResource(R.drawable.favor_not_ic);
            }
        });

        viewModel.getChangeStatusResponse().observe(this, new Observer<RestData<JsonElement>>() {
            @Override
            public void onChanged(@Nullable RestData<JsonElement> jsonElementRestData) {
                if (jsonElementRestData.status_code == 200){
                    viewModel.setRequest(userId);
                }
            }
        });

//        showProgressDialog();
        viewModel.setRequest(userId);
        viewModel.uploadAvatar().observe(this, user -> {
            if (user != null){
                hideProgressDialog();
                PreferUtils.setAvatar(this,user.data.getAvatar());
                sendImageBroadcast();
            }
        });





        binding.imageView6.setOnClickListener(v -> ActivityCompat.finishAfterTransition(ProfileFavouriteActivity.this));
        binding.imageView11.setOnClickListener(v -> {
            showProgressDialog();
            MarkUserReq markUserReq = new MarkUserReq();
            markUserReq.setMark_user_id(userId);
            viewModel.setMarkFriendRequest(markUserReq);
        });

        binding.profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userId == PreferUtils.getUserId(ProfileFavouriteActivity.this)){
                    showPictureDialog();
                }
            }
        });

        binding.textView14.setOnClickListener(v -> {
//            UpdateMobileActivity.start(this,true);
            Intent intent = new Intent(this, UpdateMobileActivity.class);
            intent.putExtra(UpdateMobileActivity.KEY_IS_MOBILE_CHANGED, true);
            startActivityForResult(intent,UpdateMobileActivity.KEY_IS_MOBILE_REQUSET);
        });

        binding.textView17.setOnClickListener(v -> {
//            UpdateMobileActivity.start(this,false);
            Intent intent = new Intent(this, UpdateMobileActivity.class);
            intent.putExtra(UpdateMobileActivity.KEY_IS_MOBILE_CHANGED, false);
            startActivityForResult(intent,UpdateMobileActivity.KEY_IS_MOBILE_REQUSET);
        });
    }


    public void goToCropActivity(String path){
        Intent intent = new Intent(ProfileFavouriteActivity.this, CropImageActivity.class);
        intent.putExtra(ConstantsApp.EXTRA_URI_STR, path);
        startActivityForResult(intent, 10);

    }

    private void hideCallLayoutWithMySelfAccount(){
        if (userId == PreferUtils.getUserId(this)){
            binding.imageView12.setVisibility(View.GONE);
            binding.imageView13.setVisibility(View.GONE);
            binding.imageView14.setVisibility(View.GONE);
            binding.view4.setVisibility(View.GONE);
            binding.imageView11.setVisibility(View.GONE);
            binding.textView14.setVisibility(View.VISIBLE);
            binding.textView17.setVisibility(View.VISIBLE);
        }

    }

    @SuppressLint("NewApi")
    private void showPictureDialog(){
        if (CropImage.isExplicitCameraPermissionRequired(this)) {
            requestPermissions(
                    new String[] {Manifest.permission.CAMERA},
                    CropImage.CAMERA_CAPTURE_PERMISSIONS_REQUEST_CODE);
        } else {
            CropImage.startPickImageActivity(this);
        }
    }

    private static final String IMAGE_DIRECTORY = "/MyElcom";
    @Override
    @SuppressLint("NewApi")
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE
                && resultCode == AppCompatActivity.RESULT_OK) {
            Uri imageUri = CropImage.getPickImageResultUri(this, data);

            Log.e("hailpt"," CropImageView "+imageUri);

            File fileLocation = new File(imageUri.getPath());
//            uploadAvatar(fileLocation);

//            binding.profileImage.setImageBitmap();
            // For API >= 23 we need to check specifically that we have permissions to read external
            // storage,
            // but we don't know if we need to for the URI so the simplest is to try open the stream and
            // see if we get error.
            boolean requirePermissions = false;
            if (CropImage.isReadExternalStoragePermissionsRequired(this, imageUri)) {

                // request permissions and handle the result in onRequestPermissionsResult()
                requirePermissions = true;
                mCropImageUri = imageUri;
                requestPermissions(
                        new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},
                        CropImage.PICK_IMAGE_PERMISSIONS_REQUEST_CODE);
            } else {
                CropImageActivity.uri = imageUri;
                goToCropActivity(imageUri.getPath());
            }
        } else if(requestCode == 10){
            if (resultCode == ConstantsApp.RESULT_CODE_CROP_IMAGE){
                binding.profileImage.setImageBitmap(mImage);
                saveImage(mImage);
            }
        } else if(requestCode == UpdateMobileActivity.KEY_IS_MOBILE_REQUSET){
            viewModel.setRequest(userId);
        }
    }

    private File avatar;
    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {
            File f = new File(wallpaperDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(this,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            avatar = f;
            uploadAvatar();
            fo.close();
            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath());

            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }

    private boolean saveFile(String fileName) {
        try {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            File path = getExternalStoragePublicDirectory(getString(R.string.sender_id));
            path.mkdir();
            File file = new File(path, fileName);

            FileOutputStream stream = new FileOutputStream(file);
            stream.write(bytes.toByteArray());
            stream.flush();
            stream.close();

            MediaScannerConnection.scanFile(this, new String[] {file.toString()}, null, null);

            return true;
        } catch (IOException e) {
            return false;
        }
    }


    private void uploadAvatar(){
        showProgressDialog();
        RequestBody avatarImage = RequestBody.create(MediaType.parse("image/*"), avatar);
        MultipartBody.Part bodyAvatarImage = MultipartBody.Part.createFormData("filePath", avatar.getName(), avatarImage);
        viewModel.setAvatarRequest(bodyAvatarImage);
    }

    private Uri mCropImageUri;
    @Override
    public void onRequestPermissionsResult(
            int requestCode, String permissions[], int[] grantResults) {


       if(isStoragePermissionGranted()){
           if (requestCode == CropImage.CAMERA_CAPTURE_PERMISSIONS_REQUEST_CODE) {
               if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                   CropImage.startPickImageActivity(this);
               } else {
                   Toast.makeText(this, "Cancelling, required permissions are not granted", Toast.LENGTH_LONG)
                           .show();
               }
           }

           if (requestCode == CropImage.PICK_IMAGE_PERMISSIONS_REQUEST_CODE) {
               if (mCropImageUri != null
                       && grantResults.length > 0
                       && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                   goToCropActivity(mCropImageUri.getPath());


               } else {
                   Toast.makeText(this, "Cancelling, required permissions are not granted", Toast.LENGTH_LONG)
                           .show();
               }
           }
       }


       if(requestCode == 999){
            viewModel.setRequest(userId);
        }
    }

    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            return true;
        }
    }

    private void startPermissionsActivity(boolean checkOnlyAudio) {
        PermissionsActivity.startActivity(this, checkOnlyAudio, Consts.PERMISSIONS);
    }

    private boolean isLoggedInChat( QBUser item) {
        if (!QBChatService.getInstance().isLoggedIn()) {
            Toaster.shortToast(R.string.dlg_signal_error);
            tryReLoginToChat(item);
            return false;
        }
        return true;
    }

    private void tryReLoginToChat( QBUser item) {
        CallService.start(this, item);
    }

    private void startCall(boolean isVideoCall, QBUser qbUser) {
//        if (allFriendQuickBloxAdapter.getSelectedItems().size() > Consts.MAX_OPPONENTS_COUNT) {
//            Toaster.longToast(String.format(getString(R.string.error_max_opponents_count),
//                    Consts.MAX_OPPONENTS_COUNT));
//            return;
//        }


        ArrayList<Integer> opponentsList = new ArrayList<>();
        opponentsList.add(qbUser.getId());
        QBRTCTypes.QBConferenceType conferenceType = isVideoCall
                ? QBRTCTypes.QBConferenceType.QB_CONFERENCE_TYPE_VIDEO
                : QBRTCTypes.QBConferenceType.QB_CONFERENCE_TYPE_AUDIO;

        QBRTCClient qbrtcClient = QBRTCClient.getInstance(getApplicationContext());

        QBRTCSession newQbRtcSession = qbrtcClient.createNewSessionWithOpponents(opponentsList, conferenceType);

//        "userID":String(Util.shared.currentUser.id),
//                "quickID": self.userCall.quickbloxId,
//                "image":(Util.shared.currentUser.avatar) ,
//                "name":Util.shared.currentUser.name]

        Map<String, String> userInfo = new HashMap<>();
        userInfo.put("userID", PreferUtils.getUserId(this)+"");
        userInfo.put("quickID", qbUser.getId()+"");
        userInfo.put("name", PreferUtils.getName(this));
        userInfo.put("image", PreferUtils.getAvatar(this));

        PreferUtils.setEmailOpponent(this,user.getName());
        PreferUtils.setAvatarOpponent(this,user.getAvatar());

        newQbRtcSession.startCall(userInfo);

        WebRtcSessionManager.getInstance(this).setCurrentSession(newQbRtcSession);

        PushNotificationSender.sendPushMessage(opponentsList, qbUser.getFullName());

        CallActivity.start(this, false);

    }

    private void sendImageBroadcast() {
        Intent intent = new  Intent();
        intent.setAction(ConstantsApp.BROARD_CHANGE_AVATAR);
        sendBroadcast(intent);
    }

    public static void start(Context context, int userId, ActivityOptionsCompat options) {
        Intent starter = new Intent(context, ProfileFavouriteActivity.class);
        starter.putExtra(Consts.EXTRA_IS_USER_ID, userId);
        context.startActivity(starter, options.toBundle());
    }

    private void callVideoOrAutio(boolean isCallVideo){

        if(user == null || (user.getQuickbloxId().equals("") || user.getQuickbloxId() == null)){
            Toaster.shortToast("Need the QuickBlox Id");
            return;
        }
//        Toaster.shortToast(user.getQuickbloxId().toString());
        QBUser qbUser = new QBUser();
        qbUser.setId(user.getQuickbloxId());
        qbUser.setEmail(user.getEmail());
        qbUser.setLogin(user.getId().toString());

        if (isLoggedInChat(qbUser)) {
            startCall(isCallVideo, qbUser);
        }

        if (checker.lacksPermissions(Consts.PERMISSIONS)) {
            startPermissionsActivity(!isCallVideo);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imageView13: {
                // call audio
                callVideoOrAutio(false);
                break;
            }

            case R.id.imageView14: {
                // call video
                callVideoOrAutio(true);
                break;
            }

            case R.id.imageView12: {
                // chat


                break;
            }
        }
    }


}
