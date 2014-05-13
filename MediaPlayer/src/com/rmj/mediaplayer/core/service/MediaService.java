package com.rmj.mediaplayer.core.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import com.rmj.mediaplayer.core.constant.PlayerOperation;
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
        MediaManager.getInstance().initMediaPlayer(new MediaPlayer(getApplicationContext()));
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
                    case PlayerOperation.OPERATION_START:
                        MediaManager.getInstance().prepare();
                        break;
                    case PlayerOperation.OPERATION_PAUSE:
                        MediaManager.getInstance().pause();
                        mClientHandler.obtainMessage(PlayerOperation.STATUS_PAUSED).sendToTarget();
                        break;
                    case PlayerOperation.OPERATION_RESUME:
                        MediaManager.getInstance().resume();
                        mClientHandler.obtainMessage(PlayerOperation.STATUS_PLAYED).sendToTarget();
                        break;
                    case PlayerOperation.OPERATION_STOP:
                        MediaManager.getInstance().stop();
                        mClientHandler.obtainMessage(PlayerOperation.STATUS_STOPED).sendToTarget();
                        break;
                    case PlayerOperation.OPERATION_SEEKTO:
                        int _pos = msg.arg1;
                        MediaManager.getInstance().seekTo(_pos);
                        break;
                    case PlayerOperation.STATUS_PREPARED:
                        MediaManager.getInstance().resume();
                        //通知界面部分
                        mClientHandler.obtainMessage(PlayerOperation.STATUS_PREPARED).sendToTarget();
                        break;
                    case PlayerOperation.STATUS_BUFFERRING_START:
                        mClientHandler.obtainMessage(PlayerOperation.STATUS_BUFFERRING_START).sendToTarget();
                        break;
                    case PlayerOperation.STATUS_BUFFERRING_END:
                        mClientHandler.obtainMessage(PlayerOperation.STATUS_BUFFERRING_END).sendToTarget();
                        break;
                    case PlayerOperation.STATUS_ERROR:
                        mClientHandler.obtainMessage(PlayerOperation.STATUS_ERROR).sendToTarget();
                        break;
                    case PlayerOperation.STATUS_IDLE:
                        mClientHandler.obtainMessage(PlayerOperation.STATUS_IDLE).sendToTarget();
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
}
