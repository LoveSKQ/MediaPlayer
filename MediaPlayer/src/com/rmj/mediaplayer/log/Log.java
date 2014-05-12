package com.rmj.mediaplayer.log;

/**
 * Created by G11 on 2014/4/16.
 */
public class Log {
    public static void i(String tag,String msg) {
        android.util.Log.i(tag,msg);
    }

    public static void e(String tag,String msg) {
        android.util.Log.e(tag,msg);
    }
}
