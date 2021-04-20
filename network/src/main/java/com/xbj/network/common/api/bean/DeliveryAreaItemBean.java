package com.xbj.network.common.api.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Time :2020/6/8
 * Author:xbj
 * Description : 派件区域实体
 */
public class DeliveryAreaItemBean {

    //快递公司编码
    @SerializedName("expressCode")
    @Expose
    public String expressCode;


    //快递公司名称
    @SerializedName("expressName")
    @Expose
    public String expressName;


    //三段码
    @SerializedName("thirdNumber")
    @Expose
    public String thirdNumber;
}
