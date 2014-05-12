package com.rmj.mediaplayer.controller;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import com.rmj.mediaplayer.R;

/**
 * Created by G11 on 2014/5/12.
 */
public class AudioControlPanel extends MediaControlPanel{

    ImageButton mHotlineBtn;
    ImageButton mShareBtn;
    OnClickListener mHotlineListener;
    OnClickListener mShareListener;

    public AudioControlPanel(Context context) {
        super(context);
    }

    public AudioControlPanel(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AudioControlPanel(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void initPanelView() {
        setPlayerControl(new PlayerControl());
        setPanelView(R.layout.controlpanel_audio);
        mPlayPauseButton = (ImageButton) findViewById(R.id.audio_btn_play);
        mSeekBar = (SeekBar) findViewById(R.id.audio_player_seekbar);
        mHotlineBtn = (ImageButton) findViewById(R.id.audio_btn_hotline);
        mShareBtn = (ImageButton) findViewById(R.id.audio_btn_share);
        initListeners();
        initExtraListeners();
        mPlayPauseButton.setOnClickListener(mPlayPauseListener);
        mSeekBar.setOnSeekBarChangeListener(mSeekBarChangeListener);
        mHotlineBtn.setOnClickListener(mHotlineListener);
        mShareBtn.setOnClickListener(mShareListener);
    }

    @Override
    public void initExtraListeners() {
        mHotlineListener = new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        };
        mShareListener = new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        };
    }

    @Override
    void played() {

    }

    @Override
    void paused() {

    }

    @Override
    void stoped() {

    }

    @Override
    void waiting() {

    }

    @Override
    void prepared() {

    }

}
