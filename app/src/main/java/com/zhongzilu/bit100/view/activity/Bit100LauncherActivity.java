package com.zhongzilu.bit100.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

/**
 * App启动时的启动页
 * Created by zhongzilu on 2016-07-28.
 */
public class Bit100LauncherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        long mDelay = 1500;
        new Handler().postDelayed(this::goMainActivity, mDelay);
    }

    private void goNext(){
        Intent[] intents = new Intent[]{
                new Intent(this, Bit100MainActivity.class),
                new Intent(this, Bit100LoginActivity.class)
        };
        startActivities(intents);
        finish();
    }

    private void goMainActivity(){
        startActivity(new Intent(Bit100LauncherActivity.this, Bit100MainActivity.class));
        finish();
    }

    private void goLoginActivity(){
        startActivity(new Intent(Bit100LauncherActivity.this, Bit100LoginActivity.class));
        finish();
    }

}
