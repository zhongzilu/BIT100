/*
 * Copyright 2016. zhongzilu(钟子路)<zhongzilu520@163.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.zhongzilu.bit100.view.base;

import android.support.annotation.FloatRange;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.zhongzilu.bit100.R;
import com.zhongzilu.bit100.application.util.StatusBarUtils;

import java.lang.reflect.Method;


/**
 * 带有Toolbar的Activity封装
 * Created by 沈钦赐 on 16/1/25.
 */
public abstract class BaseToolbarActivity extends BaseActivity {

    /**
     * 给自定义Toolbar设置标题，在{@method setupCenterTitleToolbar}方法中，
     * 默认是加载定义在AndroidManifest中的Activity的label值，如果需要动态改变
     * 标题文字，就需要调用该方法
     */
    protected void setCenterTitleText(String title){
        if (title == null)
            throw new NullPointerException("title text is null");
        setTitle(title);
    }

    /**
     * 设置自定义Toolbar来代替原有的ActionBar,新建的Activity要调用该方法需要确保
     * Activity的布局文件中引用Toolbar的布局{@layout include_toolbar_center_title_layout}
     * 然后在{@mathod setContentView}方法之后调用
     */
    protected void setupCenterTitleToolbar(){
        View view = findViewById(R.id.common_center_title_toolbar);
        if (view == null) return;

        Toolbar toolbar = (Toolbar) view;
        initToolbar(toolbar);
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

    @Override
    protected void initStatusBar() {
        setupCenterTitleToolbar();
    }

    /**
     * 初始化actionbar
     *
     * @param toolbar the mToolbar
     */
    protected void initToolbar(Toolbar toolbar) {
        setSupportActionBar(toolbar);
        StatusBarUtils.from(this)
                //沉浸状态栏
                .setTransparentStatusbar(true)
                //设置toolbar,actionbar等view
                .setActionbarView(toolbar)
                .process();
        if (hasBackButton()) {//如果需要返回按钮
            setupActionBar();
        }
    }

    /**
     * 是否有左上角返回按钮
     *
     * @return 返回true则表示需要左上角返回按钮
     */
    protected boolean hasBackButton(){
        return true;
    }

    private boolean isHiddenAppBar = false;

    /**
     * 切换appBarLayout的显隐
     */
    protected void hideOrShowToolbar(AppBarLayout mAppBar) {
        if (mAppBar == null) return;
        mAppBar.animate()
                .translationY(isHiddenAppBar ? 0 : -mAppBar.getHeight())
                .setInterpolator(new DecelerateInterpolator(2))
                .start();
        isHiddenAppBar = !isHiddenAppBar;
    }

    /**
     * 设置appBar的透明度
     * Sets app bar alpha.
     *
     * @param alpha the alpha 0-1.0
     */
    protected void setAppBarAlpha(@FloatRange(from = 0.0, to = 1.0) float alpha) {
//        if (mAppBar != null) {
//            ViewUtils.setAlpha(mAppBar, alpha);
//        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // finish();
                onBackPressed();// 返回
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        setOverflowIconVisible(menu);
        return super.onCreateOptionsMenu(menu);
    }


    /**
     * 显示菜单图标
     * @param menu menu
     */
    private void setOverflowIconVisible(Menu menu) {
        try {
            Class clazz = Class.forName("android.support.v7.view.menu.MenuBuilder");
            Method m = clazz.getDeclaredMethod("setOptionalIconsVisible", boolean.class);
            m.setAccessible(true);
            m.invoke(menu, true);
        } catch (Exception e) {
            Log.d("OverflowIconVisible", e.getMessage());
        }
    }
}
