package com.zhongzilu.bit100.widget;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.zhongzilu.bit100.R;

/**
 * 自定义WebView控件
 * Created by zhongzilu on 2016-09-18.
 */
public class CustomLoadingWebView extends WebView {

    private ProgressBar progressbar;
    private View loadingLayout;
    private OnReLoadListener listener;
    private OnScrollChangedCallback mOnScrollChangedCallback;
    private OnReceivedTitleCallback mTitleCallback;
    private final static int FILECHOOSER_RESULTCODE = 101;
    private final static int FILECHOOSER_RESULTCODE_FOR_ANDROID_5 = 102;
    private ValueCallback<Uri> mUploadMessage;
    public ValueCallback<Uri[]> mUploadMessageForAndroid5;
    private OnOpenFileChooserListener mOpenFileChooserListener;

    public CustomLoadingWebView(Context context, AttributeSet attrs) {
        super(context, attrs);

        loadingLayout = LayoutInflater.from(context).inflate(R.layout.include_loading_data_layout, this, false);
        progressbar = (ProgressBar) loadingLayout.findViewById(R.id.progress_load_url);
        loadingLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT, 0, 0));
        addView(loadingLayout);
        setWebViewClient(new WebViewClient());
        setWebChromeClient(new WebChromeClient());
        //是否可以缩放
        getSettings().setSupportZoom(true);
        //是否创建缩放工具
        getSettings().setBuiltInZoomControls(false);
        // 缩放至屏幕的大小
        getSettings().setLoadWithOverviewMode(true);
        //支持自动加载图片
        getSettings().setLoadsImagesAutomatically(true);
        //将图片调整到适合webview的大小
        getSettings().setUseWideViewPort(true);
        //没有网络的时候加载缓存
        getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        getSettings().setNeedInitialFocus(true);
    }

    public class WebChromeClient extends android.webkit.WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            progressbar.setProgress(newProgress);
            super.onProgressChanged(view, newProgress);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            if (mTitleCallback != null){
                mTitleCallback.onReceivedWebTitle(title);
            }
        }

        //扩展支持alert事件
        @Override
        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
            builder.setTitle("注意").setMessage(message).setPositiveButton("确定", null);
            builder.setCancelable(false);
            AlertDialog dialog = builder.create();
            dialog.show();
            result.confirm();
            return true;
        }

        //扩展浏览器上传文件
        //3.0++版本
        public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
            if (mOpenFileChooserListener != null)
                mOpenFileChooserListener.openFileChooserImpl(uploadMsg, acceptType, "");
        }

        //3.0--版本
        public void openFileChooser(ValueCallback<Uri> uploadMsg) {
            if (mOpenFileChooserListener != null)
                mOpenFileChooserListener.openFileChooserImpl(uploadMsg, "", "");
        }

        // Android 4.1++
        public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
            if (mOpenFileChooserListener != null)
                mOpenFileChooserListener.openFileChooserImpl(uploadMsg, acceptType, "");
        }

        // For Android > 5.0
        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> uploadMsg, FileChooserParams fileChooserParams) {
            String acceptType = null;
            if (Build.VERSION.SDK_INT >= 21) {
                String[] acceptTypes = fileChooserParams.getAcceptTypes();
                if (acceptTypes != null && acceptTypes.length > 0) {
                    acceptType = acceptTypes[0];
                }
            }
            if (mOpenFileChooserListener != null)
                mOpenFileChooserListener.openFileChooserImplForAndroid5(uploadMsg, acceptType);
            return true;
        }
    }


    public class WebViewClient extends android.webkit.WebViewClient{

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            loadingLayout.setVisibility(VISIBLE);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            loadingLayout.setVisibility(GONE);
            if (listener != null){
                listener.onReloadEnd();
            }
        }
    }

    public interface OnReLoadListener{
        void onReloadEnd();
    }

    public void setOnReloadListener(OnReLoadListener listener){
        this.listener = listener;
    }

    @Override
    protected void onScrollChanged(final int l, final int t, final int oldl,
                                   final int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (mOnScrollChangedCallback != null) {
            mOnScrollChangedCallback.onScroll(l - oldl, t - oldt, l, t);
        }
    }

    public void setOnScrollChangedCallback(
            final OnScrollChangedCallback onScrollChangedCallback) {
        mOnScrollChangedCallback = onScrollChangedCallback;
    }


    public void setOnReceivedTitleCallback(OnReceivedTitleCallback callback){
        this.mTitleCallback = callback;
    }

    public void setOnOpenFileChooserListener(OnOpenFileChooserListener listener){
        this.mOpenFileChooserListener = listener;
    }

    public interface OnScrollChangedCallback {
        void onScroll(int dx, int dy, int x, int y);
    }

    public interface OnReceivedTitleCallback{
        void onReceivedWebTitle(String title);
    }

    public interface OnOpenFileChooserListener{
        void openFileChooserImpl(ValueCallback<Uri> uploadMsg, String acceptType, String capture);
        void openFileChooserImplForAndroid5(ValueCallback<Uri[]> uploadMsg, String acceptType);
    }
}