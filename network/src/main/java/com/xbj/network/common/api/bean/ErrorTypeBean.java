package com.xbj.network.common.api.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Time :2020/5/22
 * Author:xbj
 * Description :
 */
public class ErrorTypeBean {
    @SerializedName("bigCat")
    @Expose
    public ArrayList<ErrorTypeItemBean> bigCat;
}
