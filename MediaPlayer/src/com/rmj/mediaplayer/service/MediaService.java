package com.rmj.mediaplayer.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import com.rmj.mediaplayer.constant.PlayerOperation;
import com.rmj.mediaplayer.manager.MediaManager;

/**
 * Created by G11 on 2014/5/7.
 */
public class MediaService extends Service{
    public static Handler mHandler;
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initHandler();
    }

    void initHandler() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case PlayerOperation.OPERATION_START:
                        MediaManager.getInstance().start();
                        break;
                    case PlayerOperation.OPERATION_PAUSE:
                        MediaManager.getInstance().pause();
                        break;
                    case PlayerOperation.OPERATION_RESUME:
                        MediaManager.getInstance().resume();
                        break;
                    case PlayerOperation.OPERATION_STOP:
                        MediaManager.getInstance().stop();
                        break;
                    case PlayerOperation.OPERATION_SEEKTO:
                        int _pos = msg.arg1;
                        MediaManager.getInstance().seekTo(_pos);
                        break;
                    case PlayerOperation.STATUS_PREPARED:
                        MediaManager.getInstance().start();
                        //通知界面部分
                        break;
                    default:
                        break;
                }
            }
        };
    }
}
