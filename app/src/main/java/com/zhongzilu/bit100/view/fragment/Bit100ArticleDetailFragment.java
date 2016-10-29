package com.zhongzilu.bit100.view.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.DownloadListener;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.widget.Toast;

import com.google.gson.Gson;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Response;
import com.zhongzilu.bit100.R;
import com.zhongzilu.bit100.application.util.LogUtil;
import com.zhongzilu.bit100.application.util.NetworkUtil;
import com.zhongzilu.bit100.model.bean.ArticleDetailBean;
import com.zhongzilu.bit100.model.response.AllPostsResponse;
import com.zhongzilu.bit100.view.activity.GalleryActivity;
import com.zhongzilu.bit100.widget.CustomLoadingWebView;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 文章详情Fragment
 * Created by zhongzilu on 2016-09-16.
 */
public class Bit100ArticleDetailFragment extends Fragment
        implements DownloadListener, CustomLoadingWebView.OnReLoadListener,
        NetworkUtil.NetworkCallback {

    private static final String TAG = "Bit100ArticleDetailFragment==>";

    //UI
    private View contentView;
    private CustomLoadingWebView mWebView;
    private NestedScrollView mScroll;

    //Network
    private String mContent;
    //Value
    private static ArticleDetailBean mBean;
    //Other
    private boolean isFirst = true;

    public static Bit100ArticleDetailFragment newInstance(ArticleDetailBean bean) {
        Bundle args = new Bundle();
        args.putParcelable("bean", bean);
        mBean = bean;
        Bit100ArticleDetailFragment fragment = new Bit100ArticleDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (contentView == null){
            contentView = inflater.inflate(R.layout.fragment_artical_detail_layout, container, false);
        }
        return contentView;
    }

    @SuppressLint("AddJavascriptInterface")
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setHasOptionsMenu(true);

        if (mWebView == null)
            mWebView = (CustomLoadingWebView) view.findViewById(R.id.wv_article_detail_webView);
        mScroll = (NestedScrollView) view.findViewById(R.id.nested_scroll_view);
        mWebView.addJavascriptInterface(this, "Android");
        mWebView.setOnReloadListener(this);

        initWebView();
    }

    private void initWebView() {

        //设置支持JavaScript等
        WebSettings mWebSettings = mWebView.getSettings();
        mWebSettings.setJavaScriptEnabled(true);
        mWebView.setHapticFeedbackEnabled(false);
        mWebSettings.setDomStorageEnabled(true);
        //支持内容重新布局
        mWebSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

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
            NetworkUtil.getRecentPost(this);
            isFirst = false;
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        LogUtil.d(TAG, "onHiddenChanged: " + hidden);
        if (hidden && isFirst){
            if (mBean == null)return;
            NetworkUtil.getPostById(mBean, this);
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
                shareArticle();
                break;
        }
        return true;
    }

    // zhongzilu: 16-10-21 分享文章地址
    private void shareArticle(){
        if (mBean == null)return;

        Snackbar.make(getView(), R.string.toast_invoking_share, Snackbar.LENGTH_SHORT).show();
        Intent localIntent = new Intent("android.intent.action.SEND");
        localIntent.setType("text/plain");
        localIntent.putExtra("android.intent.extra.TEXT", mBean.title +
                "【来自"+getString(R.string.app_name)+"App】\n" + mBean.url);
        localIntent.putExtra("android.intent.extra.SUBJECT", "这是分享内容");
        startActivity(Intent.createChooser(localIntent, getString(R.string.title_chooser_share)));
    }

    //

    /**
     * 新增 从网页上传递图片地址数组以及网页上点击图片的位置，
     * 然后传递给GalleryActivity，调用ViewPager显示图片
     * zhongzilu: 2016-10-25
     * @param imgSrc 网页上所有的图片地址路径数组
     * @param position 网页上点击的图片位置
     */
    @JavascriptInterface
    public void showImages(String[] imgSrc, int position){
        LogUtil.d(TAG, "showImages: imageSize==>" + imgSrc.length + "\tposition==>" + position);
        Intent intent = new Intent(getActivity(), GalleryActivity.class);
        intent.putExtra(GalleryActivity.EXTRA_IMAGES_LIST, imgSrc);
        intent.putExtra(GalleryActivity.EXTRA_CURRENT_IMAGE_POSITION, position);
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.fade_anim_in, R.anim.fade_anim_out);
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
        mWebView.loadUrl("javascript:show(" + mContent + ")");
        int width = mWebView.getWidth();
        int height = mWebView.getHeight();
        mScroll.measure(width, height);
    }

    @Override
    public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Toast.makeText(getActivity(), R.string.toast_can_not_deal_download_intent, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public OnResponseListener<JSONObject> callback() {

        return new OnResponseListener<JSONObject>() {
            @Override
            public void onStart(int what) {}

            @Override
            public void onSucceed(int what, Response<JSONObject> response) {

                try {
                    String status = response.get().getString("status");
                    if ("ok".equals(status)) {
                        switch (what){
                            case NetworkUtil.TAG_GET_RECENT_POST:
                                AllPostsResponse result = new Gson()
                                        .fromJson(response.get().toString(), AllPostsResponse.class);
                                //注意：
                                // 之所以要判断result.posts.length > 1，是因为获取首页的数据时，返回了置顶的
                                // 一篇文章，如果后台取消了置顶的文章，这里的Gson解析会出错
                                if (result.posts.length > 1){
                                    mBean = result.posts[1];
                                    mContent = formatContent(mBean.content);
                                } else {
                                    mBean = result.posts[0];
                                    mContent = formatContent(mBean.content);
                                }
                                break;

                            case NetworkUtil.TAG_GET_POST_BY_ID:
                                JSONObject obj = response.get().getJSONObject("post");
                                String content = obj.getString("content");
                                mContent = formatContent(content);
                                break;
                        }
                        mWebView.loadUrl("file:///android_asset/index.html");

                    } else {
                        Toast.makeText(getActivity(), response.get().getString("error"), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed(int what, Response<JSONObject> response) {
                Toast.makeText(getActivity(), response.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFinish(int what) {}
        };
    }

    private String formatContent(String content){
        return "\"" + content.replaceAll("\"", "'") + "\"";
    }
}
