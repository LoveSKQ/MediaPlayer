package com.rmj.mediaplayer.core.controller;

import android.os.Message;
import com.rmj.mediaplayer.core.constant.PlayerOperation;
import com.rmj.mediaplayer.core.manager.MediaManager;
import com.rmj.mediaplayer.core.service.MediaService;

/**
 * Created by G11 on 2014/5/12.
 */
public class PlayerControl implements MediaControlPanel.MediaPlayerControl {

    public PlayerControl() {

    }

    @Override
    public void start() {
        Message _msg = MediaService.mHandler.obtainMessage(PlayerOperation.OPERATION_PLAY_PAUSE);
        _msg.sendToTarget();
    }

    @Override
    public void pause() {
        Message _msg = MediaService.mHandler.obtainMessage(PlayerOperation.OPERATION_PAUSE);
        _msg.sendToTarget();
    }

    @Override
    public void resume() {
        Message _msg = MediaService.mHandler.obtainMessage(PlayerOperation.OPERATION_RESUME);
        _msg.sendToTarget();
    }

    @Override
    public void stop() {
        Message _msg = MediaService.mHandler.obtainMessage(PlayerOperation.OPERATION_STOP);
        _msg.sendToTarget();
    }

    @Override
    public int getDuration() {
        return MediaManager.getInstance().getDuration();
    }

    @Override
    public int getCurrentPosition() {
        return MediaManager.getInstance().getCurrentPosition();
    }

    @Override
    public void seekTo(int pos) {
        Message _msg = MediaService.mHandler.obtainMessage(PlayerOperation.OPERATION_PLAY_PAUSE);
        _msg.sendToTarget();
    }

    @Override
    public boolean isPlaying() {
        return MediaManager.getInstance().isPlaying();
    }

    @Override
    public int getBufferPercentage() {
        return MediaManager.getInstance().getBufferPercentage();
    }
}
