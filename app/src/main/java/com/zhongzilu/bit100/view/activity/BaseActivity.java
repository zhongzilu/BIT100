package com.zhongzilu.bit100.view.activity;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.TextView;

import com.zhongzilu.bit100.R;

/**
 * Activity的抽象基类
 * Created by zhongzilu on 2016-10-27.
 */
public abstract class BaseActivity extends AppCompatActivity {

    private TextView titleView;

    /**
     * 给自定义Toolbar设置标题，在{@method setupCenterTitleToolbar}方法中，
     * 默认是加载定义在AndroidManifest中的Activity的label值，如果需要动态改变
     * 标题文字，就需要调用该方法
     */
    protected void setCenterTitleText(String title){
        if (TextUtils.isEmpty(title))
            throw new NullPointerException("title text is null");

        if (titleView == null)
            titleView = (TextView) findViewById(R.id.tv_toolbar_center_title);
        titleView.setText(title);
    }

    /**
     * 设置自定义Toolbar来代替原有的ActionBar,新建的Activity要调用该方法需要确保
     * Activity的布局文件中引用Toolbar的布局{@layout include_toolbar_center_title_layout}
     * 然后在{@mathod setContentView}方法之后调用
     */
    protected void setupCenterTitleToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.common_center_title_toolbar);
        titleView = (TextView) findViewById(R.id.tv_toolbar_center_title);
        titleView.setText(getTitle());
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
    }

    /**
     * 设置Activity的ActionBar的返回按钮，该方法需要在{@method setupCenterTitleToolbar}
     * 方法调用之后调用
     */
    protected void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
}
