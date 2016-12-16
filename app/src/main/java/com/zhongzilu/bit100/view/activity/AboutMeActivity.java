package com.zhongzilu.bit100.view.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.zhongzilu.bit100.R;
import com.zhongzilu.bit100.application.util.SystemUtils;
import com.zhongzilu.bit100.view.base.BaseToolbarActivity;

import java.util.List;

/**
 * 关于软件
 * Created by zhongzilu on 16-11-29.
 */
public class AboutMeActivity extends BaseToolbarActivity
        implements View.OnClickListener{

    private TextView mWebsite,mGithub, mEmail;

    @Override
    public int getLayoutId() {
        return R.layout.activity_about_me_layout;
    }

    @Override
    public void onCreateAfter(Bundle savedInstanceState) {

        CardView mInfoCard = (CardView) findViewById(R.id.cv_myInfo_card);
        mWebsite = (TextView) findViewById(R.id.tv_myBlog);
        mGithub = (TextView) findViewById(R.id.tv_myGithub);
        mEmail = (TextView) findViewById(R.id.tv_myEmail);

        TextView mVersionCode = (TextView) findViewById(R.id.tv_version_code);
        mVersionCode.setText(getVersionCode());

        mInfoCard.setOnClickListener(this);
        mWebsite.setOnClickListener(this);
        mGithub.setOnClickListener(this);
        mEmail.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    private String getVersionCode(){
        return "Version " + SystemUtils.getAppVersionCode(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_myGithub:
                BaseWebActivity.loadUrl(this, mGithub.getText().toString(), null);
                break;
            case R.id.tv_myEmail:
                sendEmail();
                break;
            default:
                BaseWebActivity.loadUrl(this, mWebsite.getText().toString(), null);
                break;
        }
    }

    private void sendEmail() {
        Uri uri = Uri.parse("mailto:"+mEmail.getText().toString().trim());
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        PackageManager packageManager = getPackageManager();
        List<ResolveInfo> activities = packageManager.queryIntentActivities(intent, 0);
        if (activities.size() > 0){
            startActivity(intent);
        } else {
            Toast.makeText(this, R.string.toast_no_handle_application_text, Toast.LENGTH_SHORT).show();
        }
    }

}
