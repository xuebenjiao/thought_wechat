package com.xbj.network.common.api.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Time :2020/12/14
 * Author:xbj
 * Description : 快递柜
 */
public class DeviceModelListBean {
    /*"cellDetailResponseList":[],
"distance":0.2132,
"deviceId":24594,
"customerSN":"542352046885",
"detailAddress":"田林路398号",
"longitude":121.404552300207,
"latitude":31.172492843158,
"cellCount":21,
"canCellCount":21*/

    //快递柜大中小箱子集合
    @SerializedName("cellDetailResponseList")
    @Expose
    public ArrayList<DeviceCellDetailBean> cellDetailResponseList;


    //快递柜距离
    @SerializedName("distance")
    @Expose
    public float distance;

    //快递柜id
    @SerializedName("deviceId")
    @Expose
    public int deviceId;

    //快递柜序列号
    @SerializedName("customerSN")
    @Expose
    public String customerSN;

    //快递柜地址详情
    @SerializedName("detailAddress")
    @Expose
    public String detailAddress;


    //快递柜经度
    @SerializedName("longitude")
    @Expose
    public double longitude;

    //快递柜纬度
    @SerializedName("latitude")
    @Expose
    public double latitude;

    //快递柜箱子总个数
    @SerializedName("cellCount")
    @Expose
    public int cellCount;

    //快递柜箱子总个数
    @SerializedName("canCellCount")
    @Expose
    public int canCellCount;


}
