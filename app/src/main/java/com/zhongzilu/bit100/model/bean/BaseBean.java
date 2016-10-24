package com.zhongzilu.bit100.model.bean;

/**
 * 所有的真正实体类都需要继承该抽象类，并且覆写getFilterCommand方法
 * 用于生成过滤命令，在请求返回的数据中将除了id，其他只包含getFilterCommand返回的字段值
 * Created by zhongzilu on 2016-10-20.
 */
public abstract class BaseBean {
    /**
     * 用于生成过滤命令，在请求返回的数据中只包含命令中的字段值
     * 命令格式：String1,String2,String3,...,StringN;
     * 例：
     * @return "title,date,url,status,content,categories,tags"
     */
    public abstract String getFilterCommand();
}
