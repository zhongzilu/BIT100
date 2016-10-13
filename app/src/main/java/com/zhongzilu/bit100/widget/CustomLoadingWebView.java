package com.zhongzilu.bit100.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
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

    public CustomLoadingWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
//        progressbar = new ProgressBar(context, null,
//                android.R.attr.progressBarStyleHorizontal);
//        progressbar.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
//                5, 0, 0));
//
//        Drawable drawable = context.getResources().getDrawable(R.drawable.progress_bar_states);
//        progressbar.setProgressDrawable(drawable);

        loadingLayout = LayoutInflater.from(context).inflate(R.layout.include_loading_data_layout, this, false);
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
    }

    public class WebChromeClient extends android.webkit.WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress == 100) {
                loadingLayout.setVisibility(GONE);
            }
//            else {
//                if (progressbar.getVisibility() == GONE)
//                    progressbar.setVisibility(VISIBLE);
//                progressbar.setProgress(newProgress);
//            }
            super.onProgressChanged(view, newProgress);
        }

    }

    public class WebViewClient extends android.webkit.WebViewClient{
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            if (listener != null){
                listener.onReloadEnd();
            }
        }
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        LayoutParams lp = (LayoutParams) loadingLayout.getLayoutParams();
        lp.x = l;
        lp.y = t;
        loadingLayout.setLayoutParams(lp);
        super.onScrollChanged(l, t, oldl, oldt);
    }

    public interface OnReLoadListener{
        void onReloadEnd();
    }

    public void setOnReloadListener(OnReLoadListener listener){
        this.listener = listener;
    }
}