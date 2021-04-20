package com.xbj.network.common.api.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Time :2020/8/31
 * Author:xbj
 * Description :
 */
public class HomeErrorCornerBean {
    /**
     * 失败总数
     */
    @SerializedName("pushFailNum")
    @Expose
    public int pushFailNum;
    /**
     * 各个快递公司上传失败个数
     */
    @SerializedName("expressPushFailList")
    @Expose
    public ArrayList<HomeErrorCornerExpressBean> expressPushFailList;
}
