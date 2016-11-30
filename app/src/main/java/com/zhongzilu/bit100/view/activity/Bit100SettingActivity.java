package com.zhongzilu.bit100.view.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.zhongzilu.bit100.R;
import com.zhongzilu.bit100.application.helper.CacheCleanHelper;
import com.zhongzilu.bit100.application.util.SharePreferenceUtil;

/**
 * 设置界面
 * Created by zhongzilu on 2016-11-18.
 */
public class Bit100SettingActivity extends BaseActivity
        implements View.OnClickListener, CompoundButton.OnCheckedChangeListener{

    //UI
    private Switch mNoImageSwitch, mShowNotifySwitch,
            mLoadVideoOnWifiSwitch, mLoadHDVideoSwitch;
    private TextView mCatchInfo, mUpdateInfo;

    //Value
    private int mVersionCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_layout);
        setupCenterTitleToolbar();
        setupActionBar();

        mNoImageSwitch = (Switch) findViewById(R.id.preference_on_image_switch);
        mShowNotifySwitch = (Switch) findViewById(R.id.preference_show_notify_switch);
        mLoadVideoOnWifiSwitch = (Switch) findViewById(R.id.preference_load_video_on_wifi_switch);
        mLoadHDVideoSwitch = (Switch) findViewById(R.id.preference_HD_video_switch);
        mCatchInfo = (TextView) findViewById(R.id.preference_clear_catch_info_text);
        mUpdateInfo = (TextView) findViewById(R.id.preference_update_info_text);

        findViewById(R.id.preference_clear_catch_layout).setOnClickListener(this);
        findViewById(R.id.preference_update_layout).setOnClickListener(this);
        findViewById(R.id.preference_about_me_layout).setOnClickListener(this);
        findViewById(R.id.preference_feed_back_layout).setOnClickListener(this);

        mNoImageSwitch.setOnCheckedChangeListener(this);
        mShowNotifySwitch.setOnCheckedChangeListener(this);
        mLoadVideoOnWifiSwitch.setOnCheckedChangeListener(this);
        mLoadHDVideoSwitch.setOnCheckedChangeListener(this);

        bindData();
    }

    private void bindData(){
        getSettingInfo();
        checkCacheSize();
        loadLocalVersion();
    }

    private void getSettingInfo(){
        mLoadVideoOnWifiSwitch.setChecked(SharePreferenceUtil.getLoadVideoOnWifi());
        mLoadHDVideoSwitch.setChecked(SharePreferenceUtil.getLoadHD());
        mNoImageSwitch.setChecked(SharePreferenceUtil.getNoImage());
        mShowNotifySwitch.setChecked(SharePreferenceUtil.getShowNotify());
    }

    private void checkCacheSize(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                final String size = CacheCleanHelper.getTotalCacheSize(Bit100SettingActivity.this);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mCatchInfo.setText(size);
                    }
                });
            }
        }).start();

    }

    /**加载本应用的版本信息*/
    private void loadLocalVersion(){
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), 0);
            mUpdateInfo.setText(new StringBuilder("V " + info.versionName));
            mVersionCode = info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.preference_clear_catch_layout:
                clearCatch();
                break;

            case R.id.preference_update_layout:
                checkUpdateVersion();
                break;

            case R.id.preference_about_me_layout:
                toActivity(AboutMeActivity.class);
                break;

            case R.id.preference_feed_back_layout:
                toActivity(FeedbackActivity.class);
                break;
        }
    }

    private void toActivity(Class<?> clazz){
        startActivity(new Intent(this, clazz));
    }

    /**检查版本更新*/
    private void checkUpdateVersion() {

    }

    /**清除缓存*/
    private void clearCatch() {
        new AlertDialog.Builder(this)
                .setTitle("清除缓存")
                .setCancelable(true)
                .setMessage("确认清除缓存吗？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        CacheCleanHelper.cleanAllCache(Bit100SettingActivity.this);
                        mCatchInfo.setText("");
                        Toast.makeText(Bit100SettingActivity.this, "清除成功", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("取消", null)
                .create()
                .show();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(buttonView == mLoadVideoOnWifiSwitch){
            SharePreferenceUtil.setLoadVideoOnWifi(isChecked);
            return;
        }

        if (buttonView == mLoadHDVideoSwitch){
            SharePreferenceUtil.setLoadHDVideo(isChecked);
            return;
        }

        if (buttonView == mNoImageSwitch){
            SharePreferenceUtil.setNoImage(isChecked);
            return;
        }

        if (buttonView == mShowNotifySwitch){
            SharePreferenceUtil.setShowNotify(isChecked);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
