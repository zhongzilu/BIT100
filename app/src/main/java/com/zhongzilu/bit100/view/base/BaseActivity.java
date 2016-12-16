/*
 * Copyright 2016. SHENQINCI(沈钦赐)<946736079@qq.com>
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

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.zhongzilu.bit100.application.AppManager;
import com.zhongzilu.bit100.application.event.RxEvent;
import com.zhongzilu.bit100.application.event.RxEventBus;
import com.zhongzilu.bit100.application.util.StatusBarUtils;

import butterknife.ButterKnife;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * 原始Activity封装
 * Created by 沈钦赐 on 16/21/25.
 */
public abstract class BaseActivity extends AppCompatActivity
        implements BaseViewInterface, WaitDialogInterface, EventInterface {

    protected Context mContext;

    /**
     * On create.
     *
     * @param savedInstanceState the saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if ( BuildConfig.DEBUG) {//严苛模式
//            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build());
//            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyLog().build());
//        }
        if (isNeedLogin()) {//如果子类返回true,代表当前界面需要登录才能进去
            onLogin();
            finish();
        }
        registerEvent();

        if (getLayoutId() != 0) {// 设置布局,如果子类有返回布局的话
            setContentView(getLayoutId());
            ButterKnife.bind(this);
        } else {
            //没有提供ViewId
//            throw new IllegalStateException(this.getClass().getSimpleName() + "没有提供正确的LayoutId");
        }
        init();
        initStatusBar();
        //留给子类重写
        onCreateAfter(savedInstanceState);
    }

    private boolean isFirstFocused = true;

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (isFirstFocused && hasFocus) {
            isFirstFocused = false;
            initData();//此时界面渲染完毕,可以用来初始化数据等
        }
    }


    //用于接收事件
    private Subscription mSubscribe;

    @Override
    public void registerEvent() {
        //订阅
        mSubscribe = RxEventBus.getInstance().toObserverable()
                .filter(o -> o instanceof RxEvent)//只接受RxEvent
                .map(o -> (RxEvent) o)
                .filter(r -> hasNeedEvent(r.type))//只接受type = 1和type = 2的东西
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onEventMainThread);
    }

    @Override
    public void unregisterEvent() {
        if (mSubscribe != null) {
            mSubscribe.unsubscribe();
        }
    }

    /**
     * 根据需要重写，如果返回True，这代表该type的Event你是要接受的 会回调
     * Has need event boolean.
     *
     * @param type the type
     * @return the boolean
     */
    @Override
    public boolean hasNeedEvent(int type) {
        return type == RxEvent.TYPE_FINISH;
    }

    @Override
    public void onEventMainThread(RxEvent e) {
        if (e.type == RxEvent.TYPE_FINISH && e.o.length > 0) {
            //xxxx执行响应的操作
        }
    }


    private void init() {
        AppManager.getAppManager().addActivity(this);
        mContext = getApplicationContext();
    }

    protected void initStatusBar() {
        StatusBarUtils.from(this)
                .setTransparentStatusbar(true)
                .process();
    }

    /**
     * 当前界面是否需要登录才能进去,默认不需要登录
     *
     * @return 返回true代表当前界面需要登录才能进入
     */
    protected boolean isNeedLogin() {
        return false;
    }

    /**
     * On login.登陆逻辑留给子类,
     */
    protected void onLogin() {}


    @Override
    public void hideWaitDialog() {
        if (mWaitingDialog != null && mWaitingDialog.isShowing())
            mWaitingDialog.dismiss();
    }

    private ProgressDialog mWaitingDialog;

    @Override
    public ProgressDialog showWaitDialog(String message, boolean canBack) {
        if (mWaitingDialog == null)
            mWaitingDialog = ProgressDialog.show(mContext, null, message, false, canBack);
        else if (mWaitingDialog.isShowing()) mWaitingDialog.dismiss();

        return mWaitingDialog;
    }

    @Override
    protected void onResume() {
        super.onResume();
        //友盟统计
//        MobclickAgent.onPageStart(this.getClass().getSimpleName());
//        MobclickAgent.onResume(getApplicationContext());
    }

    @Override
    protected void onPause() {
        super.onPause();
        //友盟统计
//        MobclickAgent.onPageEnd(this.getClass().getSimpleName());
//        MobclickAgent.onPause(getApplicationContext());

    }

    @Override
    protected void onDestroy() {
        //注销EventBus
        unregisterEvent();
        //移除任务栈
        AppManager.getAppManager().removeActivity(this);
        ButterKnife.unbind(this);//解绑定
        super.onDestroy();
    }
}
