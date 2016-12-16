package com.zhongzilu.bit100.application.data;

/**
 * 过滤返回数据中不必要的字段的命令接口
 * Created by zhongzilu on 2016-10-21.
 */
public interface Command {
	//获取文章详情时的过滤命令
	String ArticleDetailCommand = "url,title,content",

	//获取文章列表时使用的过滤命令
	AllPostsCommand = "url,status,title,date,categories,tags,author,comment_count,comment_status,custom_fields,thumbnail";

}
