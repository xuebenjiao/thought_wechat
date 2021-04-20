package com.xbj.network.common.api.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Time :2020/2/25
 * Author:xbj
 * Description :
 */
public class UserInfoResponse extends BaseResponse {

    @SerializedName("data")
    @Expose
    public UserInfoEntity  data;
}
