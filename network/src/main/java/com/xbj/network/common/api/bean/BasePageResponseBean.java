package com.xbj.network.common.api.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Time :2021/3/17
 * Author:xbj
 * Description :分页查询解析实体基类
 */
public class BasePageResponseBean<T> {
    @SerializedName("pages")
    @Expose
    public  int pages;

    @SerializedName("pageNo")
    @Expose
    public  int pageNo;

    @SerializedName("resultData")
    @Expose
    public ArrayList<T> resultData;
}
