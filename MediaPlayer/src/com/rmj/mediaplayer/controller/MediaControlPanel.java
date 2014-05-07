package com.rmj.mediaplayer.controller;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.SeekBar;

/**
 * 界面部分只提供功能，界面布局、显示效果都由layout文件控制，重写initControllerView()方法自行定制
 * Created by G11 on 2014/5/6.
 */
public class MediaControlPanel extends FrameLayout{
    Handler mHandler;//接收后台事件响应结果，相应改变界面
    Button mPlayPauseButton;//播放&暂停按钮
    SeekBar mSeekBar;//播放进度条
    Context mContext;
    View mPanelView;//

    public MediaControlPanel(Context context) {
        super(context);
    }

    public MediaControlPanel(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MediaControlPanel(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * 设置PanelView
     * @param layout Panel的布局文件
     */
    public void setPanelView(int layout) {
        //判断layout是否有效
        mPanelView = ((LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(layout,null);
    }

    /**
     * 初始化Controller界面
     */
    public void initPanelView(){}

    /**
     * 初始化接收事件响应的handler
     */
    public void initHandler(){}

    /**
     * 初始化基础事件监听器
     */
    public void initListeners(){}

    /**
     * 初始化用户自定义事件监听器
     */
    public void initExtraListeners(){}

    /**
     * 显示控制界面
     */
    public void show(){}

    /**
     * 隐藏控制界面
     */
    public void hide(){}

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
