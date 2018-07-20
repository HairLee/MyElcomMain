package com.elcom.hailpt.view.ui.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.elcom.hailpt.R;
import com.elcom.hailpt.core.base.BaseActivity;
import com.elcom.hailpt.core.listener.ChangeMobilePhoneListener;
import com.elcom.hailpt.databinding.ActivityProfileFavouriteBinding;
import com.elcom.hailpt.model.api.RestData;
import com.elcom.hailpt.model.api.request.ChangeMobileReq;
import com.elcom.hailpt.model.api.request.MarkUserReq;
import com.elcom.hailpt.model.api.response.User;
import com.elcom.hailpt.services.CallService;
import com.elcom.hailpt.util.ConstantsApp;
import com.elcom.hailpt.util.Consts;
import com.elcom.hailpt.util.PermissionsChecker;
import com.elcom.hailpt.util.PreferUtils;
import com.elcom.hailpt.util.PushNotificationSender;
import com.elcom.hailpt.util.Toaster;
import com.elcom.hailpt.util.WebRtcSessionManager;
import com.elcom.hailpt.view.dialog.ChangMobileDialog;
import com.elcom.hailpt.view.ui.CallActivity;
import com.elcom.hailpt.view.ui.PermissionsActivity;
import com.elcom.hailpt.viewmodel.ProfileFavouriteViewModel;
import com.google.gson.JsonElement;
import com.quickblox.chat.QBChatService;
import com.quickblox.users.model.QBUser;
import com.quickblox.videochat.webrtc.QBRTCClient;
import com.quickblox.videochat.webrtc.QBRTCSession;
import com.quickblox.videochat.webrtc.QBRTCTypes;

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

public class ProfileFavouriteActivity extends BaseActivity<ProfileFavouriteViewModel,ActivityProfileFavouriteBinding> implements View.OnClickListener {

    private int userId = 0;
    private ActivityProfileFavouriteBinding binding;
    private ProfileFavouriteViewModel viewModel;
    private PermissionsChecker checker;
    private User user;
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
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_profile_favourite;
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
                        binding.textView11.setText("Đang hoạt động");
                    } else if (userRestData.data.getStatus() == 2){
                        binding.textView11.setText("Không hoạt động");
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

//        showProgressDialog();
        viewModel.setRequest(userId);
        viewModel.uploadAvatar().observe(this, user -> {
            if (user != null){
                hideProgressDialog();
                PreferUtils.setAvatar(this,user.data.getAvatar());
                sendImageBroadcast();
            }
        });


        viewModel.getChangeMobileResponse().observe(this, new Observer<RestData<JsonElement>>() {
            @Override
            public void onChanged(@Nullable RestData<JsonElement> jsonElementRestData) {
                Toaster.shortToast("Change OK");
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
                // Change mobile
                ChangMobileDialog changMobileDialog = new ChangMobileDialog(this, mobile -> {
                ChangeMobileReq changeMobileReq = new ChangeMobileReq();
                changeMobileReq.setHotline("");
                changeMobileReq.setMobile(mobile);
                viewModel.setChangeMobileRequest(changeMobileReq);
            });

            changMobileDialog.show();
        });

        binding.textView17.setOnClickListener(v -> {
                // Change hotline
                ChangMobileDialog changMobileDialog = new ChangMobileDialog(this, mobile -> {
                ChangeMobileReq changeMobileReq = new ChangeMobileReq();
                changeMobileReq.setHotline(mobile);
                changeMobileReq.setMobile("");
                viewModel.setChangeMobileRequest(changeMobileReq);
            });

            changMobileDialog.show();

        });
    }

    private void hideCallLayoutWithMySelfAccount(){
        if (userId == PreferUtils.getUserId(this)){
            binding.imageView12.setVisibility(View.GONE);
            binding.imageView13.setVisibility(View.GONE);
            binding.imageView14.setVisibility(View.GONE);
            binding.view4.setVisibility(View.GONE);
            binding.imageView11.setVisibility(View.GONE);
        }

    }

    private static final String IMAGE_DIRECTORY = "/demonuts";
    private int GALLERY = 1, CAMERA = 2;
    private void showPictureDialog(){
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select photo from gallery",
                "Capture photo from camera" };
        pictureDialog.setItems(pictureDialogItems,
                (dialog, which) -> {
                    switch (which) {
                        case 0:
                            ActivityCompat.requestPermissions(ProfileFavouriteActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                            break;
                        case 1:
                            ActivityCompat.requestPermissions(ProfileFavouriteActivity.this, new String[]{Manifest.permission.CAMERA}, 2);
                            break;
                    }
                });
        pictureDialog.show();
    }

    public void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, GALLERY);
    }

    private void takePhotoFromCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), contentURI);
                    String path = saveImage(bitmap);
                    binding.profileImage.setImageBitmap(bitmap);
                    saveImage(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            binding.profileImage.setImageBitmap(thumbnail);
            saveImage(thumbnail);
        }
    }

    private void uploadAvatar(){
        showProgressDialog();
        RequestBody avatarImage = RequestBody.create(MediaType.parse("image/*"), avatar);
        MultipartBody.Part bodyAvatarImage = MultipartBody.Part.createFormData("filePath", avatar.getName(), avatarImage);
        viewModel.setAvatarRequest(bodyAvatarImage);
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

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        if (grantResults.length > 0 &&  grantResults[0] == PackageManager.PERMISSION_GRANTED){
            switch (requestCode) {

                case 1: {
                    choosePhotoFromGallary();
                    break;
                }

                case 2: {
                    takePhotoFromCamera();
                    break;
                }


            }
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
        Intent intent = new  Intent(ConstantsApp.BROARD_CHANGE_AVATAR);
        intent.putExtra(ConstantsApp.BROARD_CHANGE_AVATAR, avatar); // not user
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
