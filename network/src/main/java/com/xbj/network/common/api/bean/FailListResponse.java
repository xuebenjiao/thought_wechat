package com.xbj.network.common.api.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Time :2020/5/7
 * Author:xbj
 * Description :
 */
public class FailListResponse extends BaseResponse {
    @SerializedName("data")
    @Expose
    public ArrayList<FailListBean> data;
}
