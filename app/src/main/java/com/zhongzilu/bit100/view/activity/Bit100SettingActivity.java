package com.zhongzilu.bit100.view.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.zhongzilu.bit100.R;
import com.zhongzilu.bit100.application.helper.CacheCleanHelper;
import com.zhongzilu.bit100.application.util.SharePreferenceUtil;
import com.zhongzilu.bit100.view.base.BaseToolbarActivity;

/**
 * 设置界面
 * Created by zhongzilu on 2016-11-18.
 */
public class Bit100SettingActivity extends BaseToolbarActivity
        implements View.OnClickListener, CompoundButton.OnCheckedChangeListener{

    //UI
    private Switch mNoImageSwitch, mShowNotifySwitch,
            mLoadVideoOnWifiSwitch, mLoadHDVideoSwitch;
    private TextView mCatchInfo, mUpdateInfo;

    //Value
    private int mVersionCode;

    @Override
    public int getLayoutId() {
        return R.layout.activity_setting_layout;
    }

    @Override
    public void onCreateAfter(Bundle savedInstanceState) {
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
    }

    @Override
    public void initData() {
        getSettingInfo();
        checkCacheSize();
        loadLocalVersion();
    }

    private void getSettingInfo(){
        mLoadVideoOnWifiSwitch.setChecked(SharePreferenceUtil.isLoadVideoOnWifi());
        mLoadHDVideoSwitch.setChecked(SharePreferenceUtil.isLoadHD());
        mNoImageSwitch.setChecked(SharePreferenceUtil.isLoadImage());
        mShowNotifySwitch.setChecked(SharePreferenceUtil.isShowNotify());
    }

    private void checkCacheSize(){
        new Thread(() -> {
            final String size = CacheCleanHelper.getTotalCacheSize(Bit100SettingActivity.this);
            runOnUiThread(() -> mCatchInfo.setText(size));
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
        final ProgressDialog mWaitingDialog = ProgressDialog.show(this, null, getString(R.string.dialog_checking_update_text), true, true);
        new Handler().postDelayed(() -> {
            mWaitingDialog.dismiss();
            Toast.makeText(Bit100SettingActivity.this, R.string.toast_latest_version_text, Toast.LENGTH_SHORT).show();
        }, 2000);
    }

    /**清除缓存*/
    private void clearCatch() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.setting_clear_cache_option)
                .setCancelable(true)
                .setMessage(R.string.confirm_clear_cache_message)
                .setPositiveButton(R.string.dialog_confirm_button_text, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        CacheCleanHelper.cleanAllCache(Bit100SettingActivity.this);
                        mCatchInfo.setText("");
                        Toast.makeText(Bit100SettingActivity.this, R.string.toast_clear_cache_success_text, Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton(R.string.dialog_cancel_button_text, null)
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
            SharePreferenceUtil.setLoadImage(isChecked);
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
