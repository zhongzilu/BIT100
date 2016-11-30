package com.zhongzilu.bit100.application.util;

import android.os.Message;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhongzilu.bit100.application.data.Contacts;
import com.zhongzilu.bit100.application.helper.CacheHelper;
import com.zhongzilu.bit100.model.bean.CardMoodModel;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by zhongzilu on 2016-11-07.
 */
public class RequestMood extends Thread {
    private static final String TAG = "RequestMood==>";
    private RequestMoodHandler mHandler;
    private List<CardMoodModel> mList = new ArrayList<>();
    private int mTotalNum = 52973;
    private int mNumPerPage = 10;

    public RequestMood addRequestMoodHandler(RequestMoodHandler paramRequestMoodHandler){
        this.mHandler = paramRequestMoodHandler;
        return this;
    }

    private String parseImgId(String paramString) {
        return paramString.substring(paramString.indexOf("-") + "-".length());
    }

    @Override
    public void run() {
        super.run();
        Document localDocument;
        try {
            localDocument = Jsoup.connect(Contacts.GET_MOOD_SIGN
                    + "?pn=" + new Random().nextInt(mTotalNum / mNumPerPage)
                    + "&ps=" + mNumPerPage).get();
            if (localDocument == null)
                return;

            Elements localElements1 = localDocument.getElementsByClass("pjoke");

            int size = localElements1.size() - 1;
            for (int i = 0; i < size; i++) {
                Elements moodElements = localElements1.get(i).getElementsByClass("post_user");
                Elements imageElements = localElements1.get(i).getElementsByClass("pjoke-image");
                if ((moodElements != null) && (imageElements != null)) {
                    String title = moodElements.get(0).getElementsByTag("a").text();
                    String time = moodElements.get(0).getElementsByTag("span").text();
                    String content = imageElements.get(0).attr("alt");
                    String imageId = parseImgId(imageElements.get(0).attr("id"));
                    String imageUrl = imageElements.get(0).attr("src");
                    LogUtil.d(TAG,
                            "title==>" + title
                            + "\ntime==>" + time
                            + "\ncontent==>" + content
                            + "\nImageId==>" + imageId
                            + "\nImageUrl==>" + imageUrl
                            + "\n=======================\n");

                    mList.add(new CardMoodModel(imageId, title, time, imageUrl, content));
                }
            }

            if (mHandler != null){
                CacheHelper.saveMoodList(new Gson().toJson(mList,
                        new TypeToken<List<CardMoodModel>>(){}.getType())); //保存为缓存
                Message localMessage = mHandler.obtainMessage(0, mList);
                mHandler.sendMessage(localMessage);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
