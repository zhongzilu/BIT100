package com.zhongzilu.bit100.application;

/**
 * 访问接口清单
 * Created by zhongzilu on 2016-10-20.
 */
public interface Contacts {

    // zhongzilu: 2016-10-20 服务端顶级域名
    String HOST = "http://www.bit100.com/",

    // zhongzilu: 2016-10-20 获取第一页文章
    GET_RECENT_POST = HOST + "?json=1",

    // zhongzilu: 2016-10-20 获取全部文章
    GET_ALL_POSTS = GET_RECENT_POST + "&count=-1",

    // zhongzilu: 2016-10-20 根据文章Id获取文章详情
    GET_POST_BY_ID = HOST + "?json=get_post",

    // zhongzilu: 2016-10-21 获取全部目录分类数据
    GET_ALL_CATEGORIES = HOST + "?json=get_category_index",

    // zhongzilu: 2016-10-27 根据目录Id来查询所有文章列表
    GET_ALL_POSTS_BY_CATEGORY_ID = HOST + "?json=get_category_posts";
}
