package com.zhongzilu.bit100;

import com.zhongzilu.bit100.application.Contacts;
import com.zhongzilu.bit100.application.util.RequestMood;
import com.zhongzilu.bit100.application.util.RequestMoodHandler;
import com.zhongzilu.bit100.model.bean.CardMoodModel;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;
import java.util.Random;

/**
 * Created by zhongzilu on 2016-11-07.
 */
public class JsoupTest {
    private static final String TAG = "JsoupTest==>\n";

    public static void main(String[] args){
        testParseHtml();
//        testRequestClass();
    }
    private static String parseImgId(String paramString) {
        return paramString.substring(paramString.indexOf("-") + "-".length());
    }

    private static void testRequestClass(){
        RequestMood requestMood = new RequestMood();
        requestMood.addRequestMoodHandler(new RequestMoodHandler() {
            @Override
            public void onResponse(List<CardMoodModel> paramList) {
                System.out.print(paramList.toString());
            }
        });
        requestMood.start();
    }

    private static void testParseHtml(){
        int mTotalNum = 52973;
        int mNumPerPage = 10;
        Document localDocument;
        try {
            int page = new Random().nextInt(mTotalNum / mNumPerPage);
            String url = Contacts.GET_MOOD_SIGN
                    + "?pn=" + page
                    + "&ps=" + mNumPerPage;
            System.out.print(url + "\n");
            localDocument = Jsoup.connect(url).get();
            if (localDocument == null)
                return;

            Elements localElements1 = localDocument.getElementsByClass("pjoke");

            int size = localElements1.size() - 1;
            for (int i = 0; i < size; i++) {
                Elements localElements2 = localElements1.get(i).getElementsByClass("post_user");
                Elements localElements3 = localElements1.get(i).getElementsByClass("pjoke-image");
                if ((localElements2 != null) && (localElements3 != null)) {
                    String str1 = localElements2.get(0).getElementsByTag("a").text();
                    String str2 = localElements2.get(0).getElementsByTag("span").text();
                    String str3 = localElements3.get(0).attr("alt");
                    String str4 = parseImgId(localElements3.get(0).attr("id"));
                    String str5 = localElements3.get(0).attr("src");
                    System.out.print(
                            "title==>" + str1
                                    + "\ntime==>" + str2
                                    + "\ncontent==>" + str3
                                    + "\nImageId==>" + str4
                                    + "\nImageUrl==>" + str5
                                    + "\n=======================\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
