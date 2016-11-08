package com.zhongzilu.bit100.application.util;

import com.zhongzilu.bit100.model.bean.CardMoodModel;

import java.util.List;

/**
 * Created by zhongzilu on 2016-11-07.
 */
public interface ResponseHandlerInterface {
    void onResponse(List<CardMoodModel> paramList);
}
