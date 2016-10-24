package com.zhongzilu.bit100.application.util;

/**
 * 过滤返回字段的命令接口
 * Created by zhongzilu on 2016-10-21.
 */
public interface Command {
	//获取文章详情时的过滤命令
	String ArticleDetailCommand = "url,title,content",

	AllPostsCommand = "url,status,title,date,categories,tags,author,comment_count,comment_status,custom_fields,thumbnail";

}
