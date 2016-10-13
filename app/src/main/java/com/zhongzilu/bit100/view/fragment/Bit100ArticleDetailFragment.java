package com.zhongzilu.bit100.view.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.DownloadListener;
import android.webkit.WebSettings;
import android.widget.Toast;

import com.zhongzilu.bit100.R;
import com.zhongzilu.bit100.application.util.LogUtil;
import com.zhongzilu.bit100.widget.CustomLoadingWebView;

/**
 * 文章详情Fragment
 * Created by zhongzilu on 2016-09-16.
 */
public class Bit100ArticleDetailFragment extends Fragment
        implements DownloadListener, CustomLoadingWebView.OnReLoadListener {

    private static final String TAG = "Bit100ArticleDetailFragment==>";

    private View contentView;
    private CustomLoadingWebView mWebView;
    private WebSettings mWebSettings;
    private boolean isFirst = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (contentView == null){
            contentView = inflater.inflate(R.layout.fragment_artical_detail_layout, container, false);
        }
        return contentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setHasOptionsMenu(true);

        if (mWebView == null)
            mWebView = (CustomLoadingWebView) view.findViewById(R.id.wv_article_detail_webView);

        initWebView();
    }

    private void initWebView() {
        //设置支持JavaScript等
        mWebSettings = mWebView.getSettings();
        mWebSettings.setJavaScriptEnabled(true);
        mWebView.setHapticFeedbackEnabled(false);
        //支持内容重新布局
        mWebSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mWebSettings.setNeedInitialFocus(true);

//        mWebView.setInitialScale(0); // 改变这个值可以设定初始大小

        //重要,用于与页面交互!
//        mWebView.addJavascriptInterface(new Object() {
//            @SuppressWarnings("unused")
//            public void oneClick(final String locX, final String locY) {//此处的参数可传入作为js参数
//                mHandler.post(new Runnable() {
//                    public void run() {
//                        mWebView.loadUrl("javascript:shows(" + locX + "," + locY + ")");
//                    }
//                });
//            }
//        }, "demo");//此名称在页面中被调用,方法如下:
        //<body onClick="window.demo.clickOnAndroid(event.pageX,event.pageY)">
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        LogUtil.d(TAG, "setUserVisibleHint: " + isVisibleToUser);
        if (isVisibleToUser && isFirst){
            mWebView.loadUrl("file:///android_asset/index.html");
            isFirst = false;
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        LogUtil.d(TAG, "onHiddenChanged: " + hidden);
        if (hidden && isFirst){
            mWebView.loadUrl("file:///android_asset/index.html");
            isFirst = false;
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_item2, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_share:
                Toast.makeText(getActivity(), "点击分享", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    @Override
    public void onPause() {
        super.onPause();
        mWebView.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        mWebView.onResume();
    }

    @Override
    public void onReloadEnd() {

    }

    @Override
    public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Toast.makeText(getActivity(), "请安装浏览器或下载软件", Toast.LENGTH_LONG).show();
        }
    }
}
