package com.zhongzilu.bit100.application.helper;

import android.content.Context;
import com.zhongzilu.bit100.application.App;

/**
 * JSON数据缓存帮助类
 * Created by zhongzilu on 2016-11-02.
 */
public class CacheHelper {

    private static final String CACHE_FILE = "POSTS_CACHE",
    ITEM_POSTS_ALL = "posts_all",
    ITEM_POSTS_RECENT = "posts_recent",
    ITEM_ALL_CATEGORIES = "all_categories";

    public static void savePostsAll(final String json){
        new Thread(new Runnable() {
            @Override
            public void run() {
                App.getAppContext()
                        .getSharedPreferences(CACHE_FILE, Context.MODE_PRIVATE)
                        .edit()
                        .putString(ITEM_POSTS_ALL, json)
                        .apply();
            }
        }).start();
    }

    public static String getPostsAll(){
        return App.getAppContext()
                .getSharedPreferences(CACHE_FILE, Context.MODE_PRIVATE)
                .getString(ITEM_POSTS_ALL, null);
    }

    public static void saveRecentPosts(final String json){
        new Thread(new Runnable() {
            @Override
            public void run() {
                App.getAppContext()
                        .getSharedPreferences(CACHE_FILE, Context.MODE_PRIVATE)
                        .edit()
                        .putString(ITEM_POSTS_RECENT, json)
                        .apply();
            }
        }).start();
    }

    public static String getRecentPosts(){
        return App.getAppContext()
                .getSharedPreferences(CACHE_FILE, Context.MODE_PRIVATE)
                .getString(ITEM_POSTS_RECENT, null);
    }

    public static void saveAllCategories(final String json){
        new Thread(new Runnable() {
            @Override
            public void run() {
                App.getAppContext()
                        .getSharedPreferences(CACHE_FILE, Context.MODE_PRIVATE)
                        .edit()
                        .putString(ITEM_ALL_CATEGORIES, json)
                        .apply();
            }
        }).start();
    }

    public static String getAllCategories(){
        return App.getAppContext()
                .getSharedPreferences(CACHE_FILE, Context.MODE_PRIVATE)
                .getString(ITEM_ALL_CATEGORIES, null);
    }
}
