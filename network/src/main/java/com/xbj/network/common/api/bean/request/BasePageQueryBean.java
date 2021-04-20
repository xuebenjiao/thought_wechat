package com.xbj.network.common.api.bean.request;

/**
 * Time :2021/3/17
 * Author:xbj
 * Description :分页查询参数基类
 */
public class BasePageQueryBean {
//是否是最后一页
    public boolean isLastPage;
    public int id;
    //每次查询个数
    public int limit = 20;
    //是否分页 false->不分页，true->分页
    public boolean pageFlag = true;
    public int pageNo = 1 ;
}
