package com.xbj.network.common.api.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Time :2020/5/15
 * Author:xbj
 * Description :
 */
public class WaitDeliveryResponse extends BaseResponse {
    @SerializedName("data")
    @Expose
    public ArrayList<WaitDeliveryListBean> data;
}