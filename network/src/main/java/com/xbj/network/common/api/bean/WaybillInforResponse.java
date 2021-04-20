package com.xbj.network.common.api.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Time :2020/5/9
 * Author:xbj
 * Description :
 */
public class WaybillInforResponse {
    @SerializedName("recipientAddress")
    @Expose
    public String   recipientAddress;

    @SerializedName("recipientMan")
    @Expose
    public String        recipientMan;

    @SerializedName("recipientMobile")
    @Expose
    public String  recipientMobile;

    // 1为改地址
    @SerializedName("changeAddrStatus")
    @Expose
    public int  changeAddrStatus;

    //具体修改的地址
    @SerializedName("changeAddrMsg")
    @Expose
    public String  changeAddrMsg;

    //1为精准电联
    @SerializedName("vipStatus")
    @Expose
    public int  vipStatus;

    //1为精准电联
    @SerializedName("waybillNo")
    @Expose
    public String  waybillNo;
}
