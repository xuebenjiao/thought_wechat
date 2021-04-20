package com.xbj.network.common.api.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Time :2020/8/31
 * Author:xbj
 * Description : 首页角标实体类
 */
public class HomeErrorCornerExpressBean {
    /**
     * 快递公司编码
     */
    @SerializedName("expressCode")
    @Expose
    public String expressCode;

    /**
     * 快递公司名称
     */
    @SerializedName("expressName")
    @Expose
    public String expressName;
    /**
     * 改快递公司上传失败个数
     */
    @SerializedName("pushFailNum")
    @Expose
    public int pushFailNum;
}
