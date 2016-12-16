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

package com.zhongzilu.bit100.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.zhongzilu.bit100.R;
import com.zhongzilu.bit100.application.util.Check;
import com.zhongzilu.bit100.application.util.SystemUtils;
import com.zhongzilu.bit100.view.base.BaseToolbarActivity;
import com.zhongzilu.bit100.widget.MarkdownPreviewView;

import java.io.InputStream;

import butterknife.Bind;


/**
 * 通用MarkdownEditor查看器
 * Created by 沈钦赐 on 16/6/30.
 */
public class CommonMarkdownActivity extends BaseToolbarActivity
        implements MarkdownPreviewView.OnLoadingFinishListener {
    private static final String CONTENT = "CONTENT";
    private static final String TITLE = "TITLE";
    @Bind(R.id.markdownView)
    protected MarkdownPreviewView mMarkdownPreviewView;
    private String mTitle;
    private String mContent;

    @Override
    public int getLayoutId() {
        return R.layout.activity_common_markdown;
    }

    @Override
    public void onCreateAfter(Bundle savedInstanceState) {
        mTitle = getIntent().getStringExtra(TITLE);
        mContent = getIntent().getStringExtra(CONTENT);
        Check.CheckNull(mTitle, "标题不能为空");
        Check.CheckNull(mContent, "内容不能为空");
        mMarkdownPreviewView.setOnLoadingFinishListener(this);
    }

    @Override
    public void initData() {
        setCenterTitleText(getIntent().getStringExtra(TITLE));
    }

    boolean flag = true;

    @Override
    public void onLoadingFinish() {
        if (flag) {
            flag = false;
            mMarkdownPreviewView.parseMarkdown(mContent, true);
        }
    }

    public static void startMarkdownActivity(Context context, String title, String content) {
        Intent intent = new Intent(context, CommonMarkdownActivity.class);
        intent.putExtra(CONTENT, content);
        intent.putExtra(TITLE, title);
        context.startActivity(intent);
    }

    public static void startHelper(Context context) {
        InputStream inputStream = context.getResources().openRawResource(R.raw.syntax_hepler);
        startMarkdownActivity(context, context.getString(R.string.action_helper), new String(SystemUtils.readInputStream(inputStream)));
    }
}
