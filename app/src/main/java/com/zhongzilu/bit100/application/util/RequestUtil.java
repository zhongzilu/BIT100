package com.zhongzilu.bit100.application.util;

import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.RequestQueue;
import com.zhongzilu.bit100.application.data.Contacts;
import com.zhongzilu.bit100.model.bean.ArticleDetailBean;
import com.zhongzilu.bit100.model.bean.CategoriesBean;

/**
 * 网络请求工具类，除了修改{@method sendRequest(what, request, listener)}
 * 的请求方法，还需要修改{@interface callback}的返回类型
 * Created by zhongzilu on 2016-10-20.
 */
public class RequestUtil {
    private static final String TAG = "RequestUtil==>";
    private static final String API_KEY = "android";
    private static final String API_SECRET = "7763079ba6abf342a99ab5a1dfa87ba8";

    private static RequestQueue requestQueue = NoHttp.newRequestQueue();


    public static final int TAG_GET_ALL_POSTS = 1110,
            TAG_GET_RECENT_POST = 1111,
            TAG_GET_POST_BY_ID = 1112,
            TAG_GET_ALL_CATEGORIES = 1113,
            TAG_GET_POSTS_BY_CATEGORIES = 1114,
            TAG_GET_SEARCH_RESULT = 1115;

    /**
     * 获取第一页的文章数据的请求对象
     * @return 请求对象
     */
    public static Request getRecentPostRequest(){
        return NoHttp.createJsonObjectRequest(Contacts.GET_RECENT_POST, RequestMethod.GET);
    }

    /**
     * 发送获取第一页的文章数据的请求
     * @param listener
     */
    public static void getRecentPost(RequestCallback listener){
        Request request = getRecentPostRequest();
        addIncludeCommand(request, Command.ArticleDetailCommand);
        sendRequest(TAG_GET_RECENT_POST, request, listener);
    }

    /**
     * 获取全部文章列表数据的请求对象
     * @return 请求对象
     */
    public static Request getAllPostRequest(){
        return NoHttp.createStringRequest(Contacts.GET_ALL_POSTS, RequestMethod.GET);
    }

    /**
     * 发送获取全部文章列表数据的请求
     * @param listener
     */
    public static void getAllPosts(RequestCallback listener){
        Request request = getAllPostRequest();
        addIncludeCommand(request, Command.AllPostsCommand);
        sendRequest(TAG_GET_ALL_POSTS, request, listener);
    }

    /**
     * 获取根据文章ID取文章详情的请求对象
     * @return 请求对象
     */
    public static Request getPostByIdRequest(){
        return NoHttp.createJsonObjectRequest(Contacts.GET_POST_BY_ID, RequestMethod.GET);
    }

    /**
     * 发送根据文章的ID来取文章的详情内容的请求
     * @param bean 文章详情实体类
     * @param listener 网络监听器，所有的网络请求结果将由它来处理
     */
    public static void getPostById(ArticleDetailBean bean, RequestCallback listener){
        Request request = getPostByIdRequest();
        request.add("post_id", bean.id);
        addIncludeCommand(request, bean.getFilterCommand());
        sendRequest(TAG_GET_POST_BY_ID, request, listener);
    }

    /**
     * 获取请求全部目录分类的请求对象
     * zhongzilu: 2016-10-21
     * @return 请求对象
     */
    public static Request getAllCategoriesRequest(){
        return NoHttp.createStringRequest(Contacts.GET_ALL_CATEGORIES, RequestMethod.GET);
    }

    /**
     * 发送请求全部目录分类数据
     * zhongzilu: 2016-10-21
     * @param listener
     */
    public static void getAllCategories(RequestCallback listener){
        sendRequest(TAG_GET_ALL_CATEGORIES, getAllCategoriesRequest(), listener);
    }

    /**
     * 获取根据目录ID请求所有的文章列表数据的请求对象
     * @return 请求对象
     */
    public static Request getAllPostsByCategoryIdRequest(){
        return NoHttp.createStringRequest(Contacts.GET_ALL_POSTS_BY_CATEGORY_ID, RequestMethod.GET);
    }

    /**
     * 发送请求某个目录ID下的所有文章列表数据
     * @param listener
     */
    public static void getAllPostsByCategoryId(CategoriesBean bean, RequestCallback listener){
        Request request = getAllPostsByCategoryIdRequest();
        request.add("category_id", bean.id);
        request.add("count", -1);
        addIncludeCommand(request, Command.AllPostsCommand);
        sendRequest(TAG_GET_POSTS_BY_CATEGORIES, request, listener);
    }

    /**
     * 搜索关键字,获取文章列表
     * @param search 搜索关键字
     * @param listener
     */
    public static void getPostsBySearch(String search, RequestCallback listener){
        Request request = NoHttp.createStringRequest(Contacts.GET_SEARCH_RESULT, RequestMethod.GET);
        request.add("search", search);
        request.add("count", -1);
        addIncludeCommand(request, Command.AllPostsCommand);
        sendRequest(TAG_GET_SEARCH_RESULT, request, listener);
    }

    /**
     * 登陆账户接口
     * @param name 登陆用户名
     * @param pwd 登陆密码
     * @param listener
     */
    public static void postLogin(String name, String pwd, RequestCallback listener){
        Request request = NoHttp.createJsonObjectRequest(Contacts.POST_LOGIN, RequestMethod.POST);
        request.add("log", name);
        request.add("pwd", pwd);
        sendRequest(request, listener);
    }

    /**
     * 以上是封装的请求类的方法，新增的网络请求，请添加到上面
     * ======================================================================
     * 以下是最基础的封装方法，如非必须，请不要修改
     */

    /**
     * 给请求对象添加过滤命令
     * @param request 请求对象
     * @param command
     *          过滤命令，通过使用过滤命令来去除掉返回数据中不必须的字段
     *          过滤命令大致有 Command.ArticleDetailCommand、Command.AllPostsCommand
     *          详细命令请查看{@class Command.class}
     */
    public static void addIncludeCommand(Request request, String command){
        request.add("include", command);
    }

    /**
     * 带参的发送请求方法，默认请求标示码为0
     * @param request 请求对象
     * @param listener 网络监听器
     */
    public static void sendRequest(Request request, RequestCallback listener){
        sendRequest(0, request, listener);
    }

    /**
     * 带参的发送请求方法
     * @param what 请求标示码
     * @param request 请求对象
     * @param listener 网络监听器
     */
    public static void sendRequest(int what, Request request, RequestCallback listener){
        if (request == null)
            throw new NullPointerException("Sorry! Request must is not null");
        request.addHeader("Client", "BIT100");
        requestQueue.add(what, request, listener.callback());
    }

    public static void stopRequest(){
        if (requestQueue != null){
            requestQueue.stop();
        }
    }

    public static void startRequest(){
        if (requestQueue != null){
            requestQueue.start();
        }
    }

    public static void cancelAllRequest(){
        if (requestQueue != null){
            requestQueue.cancelAll();
        }
    }

    /**
     * 自定义的网络监听器回调接口，如果要更换网络请求框架，除了修改
     * {@method sendRequest(what, request, listener)}的请求方法，
     * 还需要修改{@interface callback}的返回类型
     */
    public interface RequestCallback {
        OnResponseListener callback();
    }

}
