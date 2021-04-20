package com.xbj.network.common.api.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Time :2020/12/14
 * Author:xbj
 * Description :
 */
public class DeviceModelResponse {
    //快递柜箱子总个数
    @SerializedName("isLastPage")
    @Expose
    public boolean isLastPage;
    //快递柜信息
    @SerializedName("deviceModelResponseList")
    @Expose
    public ArrayList<DeviceModelListBean> deviceModelResponseList;
}
