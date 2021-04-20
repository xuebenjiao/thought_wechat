package com.xbj.network.common.api.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Time :2019/11/14
 * Author:xbj
 * Description :
 */
public class UploadDataBean extends BaseResponse {
    @SerializedName("data")
    @Expose
    public int data;
}
