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

package com.zhongzilu.bit100.presenter;

import android.support.annotation.NonNull;

/**
 * 回调方法抽象，每个界面的回调都不同，所以提取出来
 */
public interface IEditorActivityView extends IMvpView {
    int CALL_GET_FILES = 1;


    void onNameChange(@NonNull String name);

}