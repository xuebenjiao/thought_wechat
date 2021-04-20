package com.xbj.network.common.api.bean.request;

/**
 * Time :2021/2/24
 * Author:xbj
 * Description :
 */
public class PageQueryBean extends BasePageQueryBean{

    //省编码
    public String provinceCode;
    //城市编码
    public String cityCode;
    //搜索内容  名字是服务端定的
    public String orgName ;
    public String cityName ;
    public int orgStatus;
    // 1-》网点  2-》驿站，3-》全部
    public int orgType = 3;

    public PageQueryBean() {
    }

    public PageQueryBean(int orgType) {
        this.orgType = orgType;
    }

}
