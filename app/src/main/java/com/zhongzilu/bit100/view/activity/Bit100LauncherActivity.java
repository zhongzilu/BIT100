package com.zhongzilu.bit100.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

/**
 * App启动时的启动页
 *
 * Created by zhongzilu on 2016-07-28.
 */
public class Bit100LauncherActivity extends AppCompatActivity {

    //**延迟发送消息的延迟时间*/
    private long mDelay =1500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                goMainActivity();
            }
        }, mDelay);
    }

    private void goMainActivity(){
        startActivity(new Intent(Bit100LauncherActivity.this, Bit100MainActivity.class));
        finish();
    }

}
