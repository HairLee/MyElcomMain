package com.elcom.myelcom.view.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.elcom.myelcom.R;
import com.elcom.myelcom.util.CollectionsUtils;
import com.elcom.myelcom.util.PreferUtils;
import com.elcom.myelcom.view.ui.CallActivity;

import com.quickblox.users.model.QBUser;
import com.quickblox.videochat.webrtc.AppRTCAudioManager;

import java.util.ArrayList;

/**
 * Created by tereha on 25.05.16.
 */
public class AudioConversationFragment extends BaseConversationFragment implements CallActivity.OnChangeAudioDevice {
    private static final String TAG = AudioConversationFragment.class.getSimpleName();

    private ToggleButton audioSwitchToggleButton;
    private TextView alsoOnCallText;
    private TextView firstOpponentNameTextView;
    private TextView otherOpponentsTextView;

    private boolean headsetPlugged;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        conversationFragmentCallbackListener.addOnChangeAudioDeviceCallback(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void configureOutgoingScreen() {
        outgoingOpponentsRelativeLayout.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.white));
        allOpponentsTextView.setTextColor(ContextCompat.getColor(getActivity(), R.color.text_color_outgoing_opponents_names_audio_call));
        ringingTextView.setTextColor(ContextCompat.getColor(getActivity(), R.color.text_color_call_type));
    }

    @Override
    protected void configureToolbar() {
        toolbar.setVisibility(View.GONE);
        toolbar.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.white));
        toolbar.setTitleTextColor(ContextCompat.getColor(getActivity(), R.color.toolbar_title_color));
        toolbar.setSubtitleTextColor(ContextCompat.getColor(getActivity(), R.color.toolbar_subtitle_color));
    }

    @Override
    protected void configureActionBar() {
        actionBar.setTitle(currentUser.getTags().get(0));
        actionBar.setSubtitle(String.format(getString(R.string.subtitle_text_logged_in_as), currentUser.getFullName()));
    }

    @Override
    protected void initViews(View view) {
        super.initViews(view);
        timerChronometer = (Chronometer) view.findViewById(R.id.chronometer_timer_audio_call);

        ImageView firstOpponentAvatarImageView = (ImageView) view.findViewById(R.id.image_caller_avatar);

        Glide.with(this).load(PreferUtils.getAvatarOpponent(getContext())).into(firstOpponentAvatarImageView);


        alsoOnCallText = (TextView) view.findViewById(R.id.text_also_on_call);
        setVisibilityAlsoOnCallTextView();

        firstOpponentNameTextView = (TextView) view.findViewById(R.id.text_caller_name);
        firstOpponentNameTextView.setText(PreferUtils.getEmailOpponent(getContext()));

        otherOpponentsTextView = (TextView) view.findViewById(R.id.text_other_inc_users);
        otherOpponentsTextView.setText(getOtherOpponentsNames());

        audioSwitchToggleButton = (ToggleButton) view.findViewById(R.id.toggle_speaker);
        audioSwitchToggleButton.setVisibility(View.VISIBLE);

        actionButtonsEnabled(false);
    }

    private void setVisibilityAlsoOnCallTextView() {
        if (opponents.size() < 2) {
            alsoOnCallText.setVisibility(View.INVISIBLE);
        }
    }

    private String getOtherOpponentsNames() {
        ArrayList<QBUser> otherOpponents = new ArrayList<>();
        otherOpponents.addAll(opponents);
        otherOpponents.remove(0);

        return CollectionsUtils.makeStringFromUsersFullNames(otherOpponents);
    }

    @Override
    public void onStop() {
        super.onStop();
        conversationFragmentCallbackListener.removeOnChangeAudioDeviceCallback(this);
    }

    @Override
    protected void initButtonsListener() {
        super.initButtonsListener();

        audioSwitchToggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                conversationFragmentCallbackListener.onSwitchAudio();
            }
        });
    }

    @Override
    protected void actionButtonsEnabled(boolean inability) {
        super.actionButtonsEnabled(inability);
        audioSwitchToggleButton.setActivated(inability);
    }

    @Override
    int getFragmentLayout() {
        return R.layout.fragment_audio_conversation;
    }

    @Override
    public void onOpponentsListUpdated(ArrayList<QBUser> newUsers) {
        super.onOpponentsListUpdated(newUsers);
        firstOpponentNameTextView.setText(opponents.get(0).getFullName());
        otherOpponentsTextView.setText(getOtherOpponentsNames());
    }

    @Override
    public void audioDeviceChanged(AppRTCAudioManager.AudioDevice newAudioDevice) {
        audioSwitchToggleButton.setChecked(newAudioDevice != AppRTCAudioManager.AudioDevice.SPEAKER_PHONE);
    }
}
