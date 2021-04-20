package com.xbj.network.common.api.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Time :2020/5/9
 * Author:xbj
 * Description :
 */
public class WaybillInforBean {
    @SerializedName("recipientAddress")
    @Expose
    public String   recipientAddress;

    @SerializedName("recipientMan")
    @Expose
    public String        recipientMan;


    @SerializedName("recipientMobile")
    @Expose
    public String  recipientMobile;
}
