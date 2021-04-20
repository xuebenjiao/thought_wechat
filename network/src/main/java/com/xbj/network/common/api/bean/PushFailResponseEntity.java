package com.xbj.network.common.api.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Time :2020/9/2
 * Author:xbj
 * Description :
 */
public class PushFailResponseEntity {
    /**
     * 到件
     */
    @SerializedName("arrivePushFail")
    @Expose
    public HomeErrorCornerBean arrivePushFail;
    /**
     * 派件
     */
    @SerializedName("deliveryPushFail")
    @Expose
    public HomeErrorCornerBean deliveryPushFail;
    /**
     * 签收
     */
    @SerializedName("signPushFail")
    @Expose
    public HomeErrorCornerBean signPushFail;
    /**
     * 到派合一
     */
    @SerializedName("arriveDeliveryPushFail")
    @Expose
    public HomeErrorCornerBean arriveDeliveryPushFail;

    /**
     * 指派
     */
    @SerializedName("assignPushFail")
    @Expose
    public HomeErrorCornerBean assignPushFail;

    /**
     * 代收入库
     */
    @SerializedName("courierinPushFail")
    @Expose
    public HomeErrorCornerBean courierinPushFail;

}
