package com.xbj.network.common.api.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Time :2020/6/29
 * Author:xbj
 * Description :
 */
public class SignDeliveriedTypeBean {

    /**
     * dictId : 146
     * dictName : SIGNED_TYPE
     * dictKey : TYPE_6
     * dictValue : 其他操作
     * parentDictId : 0
     * dictDesc : 1
     * createTime : null
     */
    @SerializedName("dictId")
    @Expose
    public int dictId;
    @SerializedName("dictName")
    @Expose
    public String dictName;
    @SerializedName("dictKey")
    @Expose
    public String dictKey;
    @SerializedName("dictValue")
    @Expose
    public String dictValue;
    @SerializedName("parentDictId")
    @Expose
    public int parentDictId;
    @SerializedName("dictDesc")
    @Expose
    public String dictDesc;
    @SerializedName("createTime")
    @Expose
    public String createTime;
}
