package com.zhongzilu.bit100.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.zhongzilu.bit100.R;
import com.zhongzilu.bit100.widget.CustomLoadingWebView;

/**
 * 自定义网页浏览器界面
 * Created by zhongzilu on 16-11-30.
 */
public class CustomWebViewActivity extends BaseActivity {

    //UI
    private CustomLoadingWebView mWebView;

    //Extra Tag
    public static final String EXTRA_URL = "load_url";
    public static final String EXTRA_TITLE = "title";

    //Extra value
    private String mLoadUrl;
    private String mLoadTitle = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_webview_layout);
        setupCenterTitleToolbar();
        setupActionBar();

        mWebView = (CustomLoadingWebView) findViewById(R.id.wv_custom_webView);

        if (getIntent().hasExtra(EXTRA_URL)){
            mLoadUrl = getIntent().getStringExtra(EXTRA_URL);
        }

        if (getIntent().hasExtra(EXTRA_TITLE)){
            mLoadTitle = getIntent().getStringExtra(EXTRA_TITLE);
            if (!TextUtils.isEmpty(mLoadTitle)){
                setCenterTitleText(mLoadTitle);
            }
        }

        if (!TextUtils.isEmpty(mLoadUrl)){
            mWebView.loadUrl(mLoadUrl);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (mWebView.canGoBack()){
            mWebView.goBack();
        } else {
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item2, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            case R.id.action_share:
                share();
                break;
        }
        return true;
    }

    private void share(){
        if (TextUtils.isEmpty(mLoadUrl)){
            Toast.makeText(this, "未加载任何网页", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent localIntent = new Intent(Intent.ACTION_SEND_MULTIPLE);
        localIntent.setType("text/plain");
        localIntent.putExtra(Intent.EXTRA_TEXT, mWebView.getWebTitle() + "\n"+ mLoadUrl + "\n"+
                "【来自"+getString(R.string.app_name)+"App】\n");
        localIntent.putExtra(Intent.EXTRA_SUBJECT, mLoadTitle);
        startActivity(Intent.createChooser(localIntent, getString(R.string.title_chooser_share)));
    }
}
