package com.xbj.network.common.api.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Time :2020/4/9
 * Author:xbj
 * Description :
 */
public class SignCodBean {
    @SerializedName("iscod")
    @Expose
    public Boolean iscod;

    @SerializedName("waybillNo")
    @Expose
    public String waybillNo;
}
