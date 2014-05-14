package com.rmj.mediaplayer.core.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import com.rmj.mediaplayer.core.constant.PlayerOperation;
import com.rmj.mediaplayer.core.log.Log;
import com.rmj.mediaplayer.core.manager.MediaManager;
import io.vov.vitamio.MediaPlayer;

/**
 * Created by G11 on 2014/5/7.
 */
public class MediaService extends Service{
    public static Handler mHandler;
    public static Handler mClientHandler;
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        MediaManager.getInstance().setContext(getApplicationContext());
        MediaManager.getInstance().initMediaPlayer();
        initHandler();
    }

    @Override
    public void onDestroy() {
        MediaManager.getInstance().release();
    }

    public void setClientHandler(Handler handler) {
        mClientHandler = handler;
    }

    void initHandler() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    //TODO 整合play、pause、stop和resume的操作
                    case PlayerOperation.OPERATION_PLAY_PAUSE:
                        if (MediaManager.getInstance().isPrepared()) {
                            if (MediaManager.getInstance().isPlaying()) {
                                pause();
                            } else {
                                play();
                            }
                        } else {
                            prepare();
                        }
                        break;
                    case PlayerOperation.OPERATION_STOP:
                        stop();
                        break;
                    case PlayerOperation.OPERATION_SEEK_TO:
                        int _pos = msg.arg1;
                        seekTo(_pos);
                        break;
                    case PlayerOperation.STATUS_PREPARED:
                        play();
                        mClientHandler.obtainMessage(PlayerOperation.STATUS_PREPARED).sendToTarget();
                        break;
                    case PlayerOperation.STATUS_BUFFERING_START:
                        mClientHandler.obtainMessage(PlayerOperation.STATUS_BUFFERING_START).sendToTarget();
                        Log.i("Sunshine","Buffering start");
                        break;
                    case PlayerOperation.STATUS_BUFFERING_END:
                        mClientHandler.obtainMessage(PlayerOperation.STATUS_BUFFERING_END).sendToTarget();
                        Log.i("Sunshine","Buffering end");
                        break;
                    case PlayerOperation.STATUS_ERROR:
                        error();
                        break;
                    case PlayerOperation.STATUS_IDLE:
                        idle();
                        break;
                    case PlayerOperation.OPERATION_STOP_SERVICE:
                        stopSelf();
                        break;
                    default:
                        break;
                }
            }
        };
    }
    void play() {
        MediaManager.getInstance().resume();
        mClientHandler.obtainMessage(PlayerOperation.STATUS_PLAYED).sendToTarget();
    }

    void pause() {
        MediaManager.getInstance().pause();
        mClientHandler.obtainMessage(PlayerOperation.STATUS_PAUSED).sendToTarget();
    }

    void stop() {
        MediaManager.getInstance().stop();
        mClientHandler.obtainMessage(PlayerOperation.STATUS_STOPPED).sendToTarget();
    }

    void seekTo(int pos) {
        MediaManager.getInstance().seekTo(pos);
    }

    void prepare() {
        MediaManager.getInstance().prepare();
    }

    void error() {
        MediaManager.getInstance().reset();
        mClientHandler.obtainMessage(PlayerOperation.STATUS_ERROR).sendToTarget();
    }

    void idle() {
        mClientHandler.obtainMessage(PlayerOperation.STATUS_IDLE).sendToTarget();
    }
}
