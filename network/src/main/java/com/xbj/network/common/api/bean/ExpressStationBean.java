package com.xbj.network.common.api.bean;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Time :2020/11/17
 * Author:xbj
 * Description : 驿站列表解析实体
 */
public class ExpressStationBean {
    @SerializedName("poststationCode")
    @Expose
    public String stationCode;
    @SerializedName("poststationName")
    @Expose
    public String stationName;

    public boolean isSelect;

    @NonNull
    @Override
    public String toString() {
        return stationName;
    }
}
