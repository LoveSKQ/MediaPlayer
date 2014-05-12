package com.rmj.mediaplayer.manager;

import android.media.MediaPlayer;
import com.rmj.mediaplayer.log.Log;
import java.io.IOException;

/**
 * Created by G11 on 2014/5/6.
 */
public class MediaManager {
    private MediaPlayer mMediaPlayer;

    private static MediaManager mInstance = new MediaManager();

    public static MediaManager getInstance() {
        return mInstance;
    }

    private MediaManager() {
    }


    public void initMediaPlayer() {
        if (mMediaPlayer != null) {
            mMediaPlayer = new MediaPlayer();
        }else {
            mMediaPlayer.reset();
        }
        mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {

            }
        });
        mMediaPlayer.setOnSeekCompleteListener(new MediaPlayer.OnSeekCompleteListener() {
            @Override
            public void onSeekComplete(MediaPlayer mp) {

            }
        });
        mMediaPlayer.setOnInfoListener(new MediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(MediaPlayer mp, int what, int extra) {
                if (what == MediaPlayer.MEDIA_INFO_BUFFERING_START) {
                    //开始缓冲执行的操作
                }
                else if (what == MediaPlayer.MEDIA_INFO_BUFFERING_END) {
                    //缓冲结束后执行的操作
                }
                return true;
            }
        });
        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {

            }
        });
        mMediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                //播放放生错误，界面操作
                return true;
            }
        });

    }

    public boolean prepare(String url) {
        boolean _result = false;
        try {
            mMediaPlayer.reset();
            mMediaPlayer.setDataSource(url);
            mMediaPlayer.prepareAsync();
            _result = true;
        } catch (IOException e) {
            Log.e("MediaManager",e.toString());
        }
        return _result;
    }

    /**
     * 开始播放，与stop对应，需要建立连接
     */
    public void start(String url) {
        if (prepare(url)) {
            mMediaPlayer.start();
        }
    }

    /**
     * 暂停播放，与resume对应
     */
    public void pause(){
        mMediaPlayer.pause();
    }

    /**
     * 恢复播放，与pause对应
     */
    public void resume(){
        mMediaPlayer.start();
    }

    /**
     * 停止播放，与play对应，释放连接资源
     */
    public void stop(){
        mMediaPlayer.stop();
        mMediaPlayer.reset();
    }

    /**
     * 详见MediaPlayer.getDuration()（live内容特殊对待）
     * @return
     */
    public int getDuration(){
        return mMediaPlayer.getDuration();
    }

    /**
     * 获得当前播放位置
     * @return
     */
    public int getCurrentPosition(){
        return mMediaPlayer.getCurrentPosition();
    }

    /**
     * 跳转到位置pos
     * @param pos
     */
    public void seekTo(int pos){
        mMediaPlayer.seekTo(pos);
    }

    /**
     * 判断是否正在播放
     * @return
     */
    public boolean isPlaying(){
        return mMediaPlayer.isPlaying();
    }

    /**
     * 获得已经缓冲的进度
     * @return
     */
    public int getBufferPercentage(){
        return 0;
    }

}
