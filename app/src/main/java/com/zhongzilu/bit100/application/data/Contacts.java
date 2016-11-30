package com.zhongzilu.bit100.application.data;

/**
 * 访问接口清单
 * Created by zhongzilu on 2016-10-20.
 */
public interface Contacts {

    // zhongzilu: 2016-10-20 服务端顶级域名
    String HOST = "http://www.bit100.com/",
    // zhongzilu: 2016-11-28 获取视频资源的网站
    HOST_VIDEO = "http://i.animetaste.net/api/",

    //================获取文章博客数据===================//
    // zhongzilu: 2016-10-20 获取第一页文章
    GET_RECENT_POST = HOST + "?json=1",

    // zhongzilu: 2016-10-20 获取全部文章
    GET_ALL_POSTS = GET_RECENT_POST + "&count=-1",

    // zhongzilu: 2016-10-20 根据文章Id获取文章详情
    GET_POST_BY_ID = HOST + "?json=get_post",

    // zhongzilu: 2016-10-21 获取全部文章目录分类数据
    GET_ALL_CATEGORIES = HOST + "?json=get_category_index",

    // zhongzilu: 2016-10-27 根据目录Id来查询所有文章列表
    GET_ALL_POSTS_BY_CATEGORY_ID = HOST + "?json=get_category_posts",


    //============获取心情签名数据======================//
    // zhongzilu: 2016-11-07 获取心情签名
    GET_MOOD_SIGN = "http://common.nineton.cn/CommonProject/sign/m/index/normal",


    //===================新增获取视频数据============================//
    // zhongzilu: 2016-11-28 初始化时获取的数据接口
    GET_INIT_REQUEST_URL = HOST_VIDEO + "setup/?api_key=%s&timestamp=%s&anime=%d&feature=%d&advert=%d&access_token=%s",

    // zhongzilu: 2016-11-28 获取视频的数据接口
    GET_ANIMATION_REQUEST_URL = HOST_VIDEO + "animelist_v4/?api_key=%s&timestamp=%s&page=%d&access_token=%s",

    // zhongzilu: 2016-11-28 获取随机视频数据的接口
    GET_ANIMATION_RANDOM_URL = HOST_VIDEO + "animelist_v4/?api_key=%s&timestamp=%d&order=random&limit=%d&access_token=%s",

    // zhongzilu: 2016-11-28 获取视频详情的数据接口
    GET_ANIMATION_DETAIL_URL = HOST_VIDEO + "animelist_v4/?api_key=%s&timestamp=%d&vid=%d&access_token=%s",

    // zhongzilu: 2016-11-28 获取视频分类目录的接口
    GET_CATEGORY_REQUEST_URL = HOST_VIDEO + "animelist_v4/?api_key=%s&timestamp=%d&page=%d&category=%d&limit=%d&access_token=%s",

    // zhongzilu: 2016-11-28 获取全部推荐视频数据
    GET_RECOMMEND_ALL_REQUEST = HOST_VIDEO + "animelist_v4/?api_key=%s&timestamp=%d&feature=1&limit=%d&access_token=%s",

    // zhongzilu: 2016-11-28 获取推荐视频分类数据
    GET_RECOMMEND_CATEGORY_REQUEST = HOST_VIDEO + "animelist_v4/?api_key=%s&timestamp=%d&category=%d&feature=1&limit=%d&access_token=%s";

    //===================获取视频数据结束==========================//

}
