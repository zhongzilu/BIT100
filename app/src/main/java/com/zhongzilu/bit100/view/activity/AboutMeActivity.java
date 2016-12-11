package com.zhongzilu.bit100.view.activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.zhongzilu.bit100.R;

import java.util.List;

/**
 * 关于软件
 * Created by zhongzilu on 16-11-29.
 */
public class AboutMeActivity extends BaseActivity
        implements View.OnClickListener{

    private TextView mWebsite,mGithub, mEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me_layout);
        setupCenterTitleToolbar();
        setupActionBar();

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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_myGithub:
                goWebView(mGithub.getText().toString());
                break;
            case R.id.tv_myEmail:
                sendEmail();
                break;
            default:
                goWebView(mWebsite.getText().toString());
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
            Toast.makeText(this, "没有发送邮件的应用", Toast.LENGTH_SHORT).show();
        }
    }

    private void goWebView(String url){
        Intent intent = new Intent(this, CustomWebViewActivity.class);
        intent.putExtra(CustomWebViewActivity.EXTRA_URL, url);
        startActivity(intent);
    }
}
