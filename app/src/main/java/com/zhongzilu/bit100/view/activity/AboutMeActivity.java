package com.zhongzilu.bit100.view.activity;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.zhongzilu.bit100.R;

/**
 * 关于软件
 * Created by zhongzilu on 16-11-29.
 */
public class AboutMeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me_layout);
        setupCenterTitleToolbar();
        setupActionBar();

        TextView mVersionCode = (TextView) findViewById(R.id.tv_version_code);
        mVersionCode.setText(getVersionCode());
    }



    private String getVersionCode(){
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), 0);
            return "Version " + info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }
}
