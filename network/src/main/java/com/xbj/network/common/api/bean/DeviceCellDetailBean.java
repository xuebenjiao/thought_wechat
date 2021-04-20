package com.xbj.network.common.api.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Time :2020/12/14
 * Author:xbj
 * Description : 快递柜大小中箱子实体
 */
public class DeviceCellDetailBean {
    /*"cellType":1,
"cellCount":14*/
    //快递柜箱子类型
    @SerializedName("cellType")
    @Expose
    public int cellType;

    //快递柜箱子类型数量
    @SerializedName("cellCount")
    @Expose
    public int cellCount;

}
