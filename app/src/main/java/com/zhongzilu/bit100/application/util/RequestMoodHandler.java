package com.zhongzilu.bit100.application.util;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.zhongzilu.bit100.application.data.ResponseHandlerInterface;
import com.zhongzilu.bit100.model.bean.CardMoodModel;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by zhongzilu on 2016-11-07.
 */
public abstract class RequestMoodHandler implements ResponseHandlerInterface {
    private Handler handler;

    public RequestMoodHandler() {
        if (Looper.myLooper() != null)
            handler = new ResponderHandler(this);
    }

    protected void handleMessage(Message paramMessage) {
        Object obj = paramMessage.obj;
        List<CardMoodModel> localList = null;
        if (obj instanceof List) {
           localList = (List<CardMoodModel>) paramMessage.obj;
        }

        if (localList == null)
            return;
        onResponse(localList);
    }

    protected Message obtainMessage(int paramInt, Object paramObject) {
        Message localMessage;
        if (handler != null) {
            localMessage = handler.obtainMessage(paramInt, paramObject);
            return localMessage;
        }
        localMessage = Message.obtain();
        localMessage.what = paramInt;
        localMessage.obj = paramObject;
        return localMessage;
    }

    public abstract void onResponse(List<CardMoodModel> paramList);

    protected void sendMessage(Message paramMessage){
        if (handler != null) {
            if (!Thread.currentThread().isInterrupted())
                handler.sendMessage(paramMessage);
            return;
        }
        handleMessage(paramMessage);
    }

    static class ResponderHandler extends Handler {
        private final WeakReference<RequestMoodHandler> mResponder;

        ResponderHandler(RequestMoodHandler paramRequestMoodHandler) {
            this.mResponder = new WeakReference(paramRequestMoodHandler);
        }

        public void handleMessage(Message paramMessage) {
            RequestMoodHandler localRequestMoodHandler = (RequestMoodHandler) mResponder.get();
            if (localRequestMoodHandler != null)
                localRequestMoodHandler.handleMessage(paramMessage);
        }
    }
}
