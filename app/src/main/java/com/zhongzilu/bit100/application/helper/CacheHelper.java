package com.zhongzilu.bit100.application.helper;

import android.content.Context;
import com.zhongzilu.bit100.application.App;
import com.zhongzilu.bit100.model.response.LoginResponse;

/**
 * JSON数据缓存帮助类
 * Created by zhongzilu on 2016-11-02.
 */
public class CacheHelper {

    private static final String CACHE_FILE = "POSTS_CACHE",
            ITEM_POSTS_ALL = "posts_all",
            ITEM_POSTS_RECENT = "posts_recent",
            ITEM_ALL_CATEGORIES = "all_categories";

    private static final String MOOD_CACHE_FILE = "MOOD_CACHE",
            ITEM_MOOD_LIST = "mood_list";

    private static final String LOGIN_INFO_CACHE = "LOGIN_CACHE";

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

    public static void saveMoodList(final String json){
        new Thread(new Runnable() {
            @Override
            public void run() {
                App.getAppContext()
                        .getSharedPreferences(MOOD_CACHE_FILE, Context.MODE_PRIVATE)
                        .edit()
                        .putString(ITEM_MOOD_LIST, json)
                        .apply();
            }
        }).start();
    }

    public static String getMoodList(){
        return App.getAppContext()
                .getSharedPreferences(MOOD_CACHE_FILE, Context.MODE_PRIVATE)
                .getString(ITEM_MOOD_LIST, null);
    }

    public static void saveLoginInfo(final LoginResponse response){
        new Thread(new Runnable() {
            @Override
            public void run() {
                App.getAppContext()
                        .getSharedPreferences(LOGIN_INFO_CACHE, Context.MODE_PRIVATE)
                        .edit()
                        .putString("displayName", response.displayName)
                        .putString("avatar", response.avatar)
                        .putInt("id", response.id)
                        .putInt("blog_id", response.blog_id)
                        .putBoolean("logged", response.loggedIn)
                        .apply();
            }
        }).start();
    }

    public static void saveLoginPwd(final String pwd){
        new Thread(new Runnable() {
            @Override
            public void run() {
                App.getAppContext()
                        .getSharedPreferences(LOGIN_INFO_CACHE, Context.MODE_PRIVATE)
                        .edit()
                        .putString("pwd", pwd)
                        .apply();
            }
        }).start();
    }

    public static void saveLoginName(final String name){
        new Thread(new Runnable() {
            @Override
            public void run() {
                App.getAppContext()
                        .getSharedPreferences(LOGIN_INFO_CACHE, Context.MODE_PRIVATE)
                        .edit()
                        .putString("name", name)
                        .apply();
            }
        }).start();
    }

    public static String getDisplayName(){
        return App.getAppContext()
                .getSharedPreferences(LOGIN_INFO_CACHE, Context.MODE_PRIVATE)
                .getString("displayName", "");
    }

    public static String getUserAvatar(){
        return App.getAppContext()
                .getSharedPreferences(LOGIN_INFO_CACHE, Context.MODE_PRIVATE)
                .getString("avatar", null);
    }

    public static int getUserId(){
        return App.getAppContext()
                .getSharedPreferences(LOGIN_INFO_CACHE, Context.MODE_PRIVATE)
                .getInt("id", -1);
    }

    public static int getBlogId(){
        return App.getAppContext()
                .getSharedPreferences(LOGIN_INFO_CACHE, Context.MODE_PRIVATE)
                .getInt("blog_id", -1);
    }

    public static boolean isLogged(){
        return App.getAppContext()
                .getSharedPreferences(LOGIN_INFO_CACHE, Context.MODE_PRIVATE)
                .getBoolean("logged", false);
    }

    public static String getLoginPwd(){
        return App.getAppContext()
                .getSharedPreferences(LOGIN_INFO_CACHE, Context.MODE_PRIVATE)
                .getString("pwd", "");
    }

    public static String getLoginName(){
        return App.getAppContext()
                .getSharedPreferences(LOGIN_INFO_CACHE, Context.MODE_PRIVATE)
                .getString("name", "");
    }
}
