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

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.zhongzilu.bit100.application.event.RxEvent;
import com.zhongzilu.bit100.application.event.RxEventBus;

import butterknife.ButterKnife;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * fragment基类
 * Created by 沈钦赐 on 2016/1/25.
 */
public abstract class BaseFragment extends BaseStatedFragment
        implements BaseViewInterface, EventInterface {
    protected View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(getLayoutId(), container, false);
        }
        ButterKnife.bind(this, rootView);
        registerEvent();
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        // MobclickAgent.onPageStart(this.getClass().getSimpleName());
        if (isFirstFocused) {
            isFirstFocused = false;
            initData();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        // MobclickAgent.onPageEnd(this.getClass().getSimpleName());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(rootView);
        //注销EventBus
        unregisterEvent();
        rootView = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        if (BuildConfig.DEBUG) {//Debug的时候检查内存泄露
//            RefWatcher refWatcher = App.getRefWatcher(mContext);
//            if (refWatcher != null) {
//                refWatcher.watch(this);
//            }
//        }
    }

    @Override
    protected void onFirstLaunched() {
        super.onFirstLaunched();
        //包含菜单到所在Activity
        setHasOptionsMenu(hasMenu());
        onCreateAfter(null);
    }


    private boolean isFirstFocused = true;


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
     * 返回键，预留给所在activity调用
     * On back pressed boolean.
     *
     * @return the boolean
     */
    public boolean onBackPressed() {
        return false;
    }

    /**
     * 需要重写hasMenu() 返回True，才会创建菜单
     *
     * @param menu     the menu
     * @param inflater the inflater
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    /**
     * 需要重写hasMenu() 返回True，才会回调
     * On options item selected boolean.
     *
     * @param item the item
     * @return the boolean
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
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


    public boolean hasMenu() {
        return false;
    }


}
