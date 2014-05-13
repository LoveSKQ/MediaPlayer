package com.rmj.mediaplayer.demo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import com.rmj.mediaplayer.R;
import com.rmj.mediaplayer.core.bean.MediaInfo;
import com.rmj.mediaplayer.core.constant.PlayerOperation;
import com.rmj.mediaplayer.core.manager.MediaManager;
import com.rmj.mediaplayer.core.service.MediaService;
import io.vov.vitamio.LibsChecker;

public class MyActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!LibsChecker.checkVitamioLibs(this)) return;
        setContentView(R.layout.main);
        startService(new Intent(MyActivity.this, MediaService.class));
        MediaInfo _info = new MediaInfo();
        _info.setUrl("mms://dianbo.hebradio.com/live1");
        MediaManager.getInstance().setCurrentMedia(_info);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}
