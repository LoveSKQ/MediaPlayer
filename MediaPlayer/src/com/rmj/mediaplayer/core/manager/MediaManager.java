package com.rmj.mediaplayer.core.manager;

import android.content.Context;
import com.rmj.mediaplayer.core.bean.MediaInfo;
import com.rmj.mediaplayer.core.constant.PlayerOperation;
import com.rmj.mediaplayer.core.log.Log;
import com.rmj.mediaplayer.core.service.MediaService;
import io.vov.vitamio.MediaPlayer;

import java.io.IOException;

/**
 * Created by G11 on 2014/5/6.
 */
public class MediaManager {
    private MediaPlayer mMediaPlayer;
    private MediaInfo mCurrentMediaInfo;
    private Context mContext = null;
    private static MediaManager mInstance = new MediaManager();
    private int mStatus = PlayerOperation.STATUS_IDLE;

    public static MediaManager getInstance() {
        return mInstance;
    }

    private MediaManager() {
    }

    public void setCurrentMedia(MediaInfo info) {
        mCurrentMediaInfo = info;
    }

    public void release() {
        mMediaPlayer.release();
        mMediaPlayer = null;
    }

    public void setContext(Context context) {
        mContext = context;
    }

    public void initMediaPlayer() {
        if (mMediaPlayer == null) {
            mMediaPlayer = new MediaPlayer(mContext);
        }else {
            mMediaPlayer.reset();
        }
        mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mStatus = PlayerOperation.STATUS_PREPARED;
                MediaService.mHandler.obtainMessage(PlayerOperation.STATUS_PREPARED).sendToTarget();
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
                    MediaService.mHandler.obtainMessage(PlayerOperation.STATUS_BUFFERING_START).sendToTarget();
                }
                else if (what == MediaPlayer.MEDIA_INFO_BUFFERING_END) {
                    //缓冲结束后执行的操作
                    MediaService.mHandler.obtainMessage(PlayerOperation.STATUS_BUFFERING_END).sendToTarget();
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
                MediaService.mHandler.obtainMessage(PlayerOperation.STATUS_ERROR);
                return true;
            }
        });

    }

    public boolean prepare() {
        boolean _result = false;
        try {
            mMediaPlayer.reset();
            mMediaPlayer.setDataSource(mCurrentMediaInfo.getUrl());
            mMediaPlayer.setBufferSize(512);
            mMediaPlayer.prepareAsync();
            _result = true;
        } catch (IOException e) {
            Log.e("MediaManager",e.toString());
            mStatus = PlayerOperation.STATUS_ERROR;
        }
        return _result;
    }

    /**
     * 开始播放，与stop对应，需要建立连接(拆分成prepare和start两部分)
     */
    public void start() {
        mMediaPlayer.start();
        mStatus = PlayerOperation.STATUS_PLAYED;
    }

    /**
     * 暂停播放，与resume对应
     */
    public void pause(){
        mMediaPlayer.pause();
        mStatus = PlayerOperation.STATUS_PAUSED;
    }

    /**
     * 恢复播放，与pause对应
     */
    public void resume(){
        mMediaPlayer.start();
        mStatus = PlayerOperation.STATUS_PLAYED;
    }

    /**
     * 停止播放，与play对应，释放连接资源
     */
    public void stop(){
        mMediaPlayer.stop();
        mStatus = PlayerOperation.STATUS_STOPPED;
        reset();
    }

    /**
     * 重置MediaPlayer
     */
    public void reset() {
        initMediaPlayer();
        mStatus = PlayerOperation.STATUS_IDLE;
    }
    /**
     * 详见MediaPlayer.getDuration()（live内容特殊对待）
     * @return
     */
    public int getDuration(){
        return (int)mMediaPlayer.getDuration();
    }

    /**
     * 获得当前播放位置
     * @return
     */
    public int getCurrentPosition(){
        return (int)mMediaPlayer.getCurrentPosition();
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
     * 判断是否正在缓冲
     */
    public boolean isBuffering() {
        return mMediaPlayer.isBuffering();
    }

    /**
     * 获得已经缓冲的进度
     * @return
     */
    public int getBufferPercentage(){
        return mMediaPlayer.getBufferProgress();
    }

    public boolean isPrepared() {
        boolean _prepared = false;
        if (mMediaPlayer != null && (mStatus == PlayerOperation.STATUS_PLAYED || mStatus == PlayerOperation.STATUS_PAUSED || mStatus == PlayerOperation.STATUS_PREPARED)) {
            _prepared = true;
        }
        return _prepared;
    }

}
