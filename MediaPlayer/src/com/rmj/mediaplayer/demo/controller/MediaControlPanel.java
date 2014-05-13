package com.rmj.mediaplayer.demo.controller;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import com.rmj.mediaplayer.R;
import com.rmj.mediaplayer.core.controller.PlayerControl;
import com.rmj.mediaplayer.core.service.MediaService;

/**
 * Created by G11 on 2014/5/12.
 */
public class MediaControlPanel extends com.rmj.mediaplayer.core.controller.MediaControlPanel {

    ImageButton mHotlineBtn;
    ImageButton mShareBtn;
    OnClickListener mHotlineListener;
    OnClickListener mShareListener;

    public MediaControlPanel(Context context) {
        super(context);
        initPanelView();
    }

    public MediaControlPanel(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPanelView();
    }

    public MediaControlPanel(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void initPanelView() {
        setPlayerControl(new PlayerControl());
        setPanelView(R.layout.controlpanel_audio);
        mPlayPauseButton = (ImageButton) mPanelView.findViewById(R.id.audio_btn_play);
        mSeekBar = (SeekBar) mPanelView.findViewById(R.id.audio_player_seekbar);
        mCurrentTime = (TextView) mPanelView.findViewById(R.id.audio_player_time_current);
        mTotalTime = (TextView) mPanelView.findViewById(R.id.audio_player_time_total);
        mHotlineBtn = (ImageButton) mPanelView.findViewById(R.id.audio_btn_hotline);
        mShareBtn = (ImageButton) mPanelView.findViewById(R.id.audio_btn_share);
        MediaService.mClientHandler = mHandler;
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
    protected void played() {

    }

    @Override
    protected void paused() {

    }

    @Override
    protected void stoped() {

    }

    @Override
    protected void waiting() {

    }

    @Override
    protected void prepared() {

    }

    @Override
    protected void bufferingStart() {

    }

    @Override
    protected void bufferingEnd() {

    }

    @Override
    protected void error() {

    }

}
