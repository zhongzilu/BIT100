package com.zhongzilu.bit100.application.util;

import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.RequestQueue;
import com.zhongzilu.bit100.application.Contacts;
import com.zhongzilu.bit100.model.bean.ArticleDetailBean;

/**
 * 网络请求工具类，如果要更换网络请求框架，只需要更改
 * Created by zhongzilu on 2016-10-20.
 */
public class NetworkUtil {

    public static final int TAG_GET_RECENT_POST = 1111,
            TAG_GET_POST_BY_ID = 1112,
            TAG_GET_ALL_CATEGORIES = 1113;

    private static RequestQueue requestQueue = NoHttp.newRequestQueue();

    public static void sendRequest(Request request, NetworkCallback listener){
        sendRequest(0, request, listener);
    }

    public static void sendRequest(int what, Request request, NetworkCallback listener){
        if (request == null)
            throw new NullPointerException("Sorry! Request must is not null");
        request.addHeader("Client", "BIT100");
        requestQueue.add(what, request, listener.callback());
    }

    public static Request getRecentPostRequest(){
        return NoHttp.createJsonObjectRequest(Contacts.GET_RECENT_POST, RequestMethod.GET);
    }

    public static void getRecentPost(NetworkCallback listener){
        Request request = getRecentPostRequest();
        request.add("include", Command.ArticleDetailCommand);
        sendRequest(TAG_GET_RECENT_POST, request, listener);
    }

    public static Request getAllPostRequest(){
        return NoHttp.createStringRequest(Contacts.GET_ALL_POSTS, RequestMethod.GET);
    }

    public static void getAllPosts(NetworkCallback listener){
        Request request = getAllPostRequest();
        request.add("include", Command.AllPostsCommand);
        sendRequest(request, listener);
    }

    public static Request getPostByIdRequest(){
        return NoHttp.createJsonObjectRequest(Contacts.GET_POST_BY_ID, RequestMethod.GET);
    }

    public static void getPostById(ArticleDetailBean bean, NetworkCallback listener){
        Request request = getPostByIdRequest();
        request.add("post_id", bean.id);
        request.add("include", bean.getFilterCommand());
        sendRequest(TAG_GET_POST_BY_ID, request, listener);
    }

    // zhongzilu: 2016-10-21 获取请求全部目录分类
    public static Request getAllCategoriesRequest(){
        return NoHttp.createStringRequest(Contacts.GET_ALL_CATEGORIES, RequestMethod.GET);
    }

    // zhongzilu: 2016-10-21 发送请求全部目录分类
    public static void getAllCategories(NetworkCallback listener){
        sendRequest(TAG_GET_ALL_CATEGORIES, getAllCategoriesRequest(), listener);
    }

    public interface NetworkCallback {
        OnResponseListener callback();
    }
}
