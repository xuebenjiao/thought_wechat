package com.xbj.network.common.api.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Time :2020/11/10
 * Author:xbj
 * Description : 发往下一站接口响应实体
 */
public class ExpressNextStationEntity {
    @SerializedName("resultData")
    @Expose
    public ArrayList<ExpressNextStationBean> resultData;
}
