package com.xbj.network.common.api.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Time :2021/2/24
 * Author:xbj
 * Description :
 */
public class StationNameBean {
    @SerializedName("stationCode")
    @Expose
    public String stationCode;
    @SerializedName("stationName")
    @Expose
    public String stationName;
}
