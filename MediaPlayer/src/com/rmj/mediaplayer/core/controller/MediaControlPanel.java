package com.rmj.mediaplayer.core.controller;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import com.rmj.mediaplayer.core.constant.PlayerOperation;
import com.rmj.mediaplayer.core.service.MediaService;

/**
 * 界面部分只提供功能，界面布局、显示效果都由layout文件控制，重写initControllerView()方法自行定制
 * Created by G11 on 2014/5/6.
 */
public abstract class MediaControlPanel extends FrameLayout{
    protected Handler mHandler;//接收后台事件响应结果，相应改变界面
    protected ImageButton mPlayPauseButton;//播放&暂停按钮
    protected TextView mPlayPauseTextView;
    protected SeekBar mSeekBar;//播放进度条
    protected TextView mCurrentTime;
    protected TextView mTotalTime;
    protected Context mContext;
    protected View mPanelView;
    protected MediaPlayerControl mPlayerControl;

    public OnClickListener mPlayPauseListener;
    public SeekBar.OnSeekBarChangeListener mSeekBarChangeListener;

    public MediaControlPanel(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public MediaControlPanel(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    public MediaControlPanel(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        init();
    }

    public void init() {
        initListeners();
        initPanelView();
        initHandler();
        mSeekBar.setEnabled(false);
    }

    /**
     * 设置PanelView
     * @param layout Panel的布局文件
     */
    public void setPanelView(int layout) {
        //判断layout是否有效
        mPanelView = ((LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(layout,null);
        this.addView(mPanelView);
    }

    /**
     * 初始化Controller界面
     */
    public abstract void initPanelView();

    /**
     * 初始化接收事件响应的handler
     */
    public void initHandler(){
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case PlayerOperation.STATUS_PLAYED:
                        played();
                        break;
                    case PlayerOperation.STATUS_PAUSED:
                        paused();
                        break;
                    case PlayerOperation.STATUS_STOPPED:
                        stopped();
                        break;
                    case PlayerOperation.STATUS_PREPARED:
                        prepared();
                        break;
                    case PlayerOperation.STATUS_BUFFERING_START:
                        bufferingStart();
                        break;
                    case PlayerOperation.STATUS_BUFFERING_END:
                        bufferingEnd();
                        break;
                    case PlayerOperation.STATUS_ERROR:
                        error();
                        break;
                    case PlayerOperation.STATUS_IDLE:
                        break;
                    default:
                        break;

                }
            }
        };
        MediaService.mClientHandler = mHandler;
    }

    /**
     * 初始化基础事件监听器
     */
    public void initListeners(){
        mPlayPauseListener = new OnClickListener() {
            @Override
            public void onClick(View v) {
                Message _msg = MediaService.mHandler.obtainMessage(PlayerOperation.OPERATION_PLAY_PAUSE);
                _msg.sendToTarget();
                waiting();
            }
        };
        mSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (!fromUser) {
                    return;
                }
                //TODO 拖动播放进度操作
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //TODO 开始拖动动作
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //TODO 拖动结束
            }
        };
        initExtraListeners();
    }

    /**
     * 初始化用户自定义事件监听器
     */
    public abstract void initExtraListeners();
    protected abstract void played();
    protected abstract void paused();
    protected abstract void stopped();
    protected abstract void waiting();
    protected abstract void prepared();
    protected abstract void bufferingStart();
    protected abstract void bufferingEnd();
    protected abstract void error();


    /**
     * 显示控制界面
     */
    public void show(){}

    /**
     * 隐藏控制界面
     */
    public void hide(){}

    public void setPlayerControl(MediaPlayerControl control) {
        mPlayerControl = control;
    }

    /**
     * 控制执行接口，负责执行控制操作（区分开界面控制和后台控制逻辑）
     */
    public interface MediaPlayerControl {
        void    start();//开始播放，与stop对应，需要建立连接
        void    pause();//暂停播放，与resume对应
        void    resume();//恢复播放，与pause对应
        void    stop();//停止播放，与play对应，释放连接资源
        int     getDuration();//详见MediaPlayer.getDuration()（live内容特殊对待）
        int     getCurrentPosition();//获得当前播放位置
        void    seekTo(int pos);//跳转到位置pos
        boolean isPlaying();//判断是否正在播放
        int     getBufferPercentage();//获得已经缓冲的进度
    }
}
