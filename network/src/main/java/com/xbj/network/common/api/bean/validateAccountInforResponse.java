package com.xbj.network.common.api.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Time :2020/11/12
 * Author:xbj
 * Description :账号校验请求返回实体类
 */
public class validateAccountInforResponse {
    @SerializedName("data")
    @Expose
    public ValidateAccountInforBean data;
}
