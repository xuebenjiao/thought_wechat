package com.xbj.network.common.api.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * author : xbj
 * e-mail : xbjzhu@163.com
 * date   : 2019/10/22 17:32
 * desc   :
 */
public class BaseResponse<T> {
    @SerializedName("code")
    @Expose
    public int code;

    @SerializedName("msg")
    @Expose
    public String message;

    //便于区分同一接口不同请求
    @SerializedName("requestType")
    @Expose
    public String requestType;

    @SerializedName("data")
    @Expose
    public T data;
}
