package com.xbj.network.common.api.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ScanRecordBean {
    @SerializedName("expressCompCode")
    @Expose
    public String expressCompCode;

    @SerializedName("expressCompName")
    @Expose
    public String expressCompName;

    @SerializedName("nextExpressOrgCode")
    @Expose
    public String nextExpressOrgCode;

    @SerializedName("nextExpressOrgName")
    @Expose
    public String nextExpressOrgName;

    @SerializedName("wayBillNo")
    @Expose
    public String wayBillNo;


    @SerializedName("scanTime")
    @Expose
    public String scanTime;


    @SerializedName("recipientName")
    @Expose
    public String recipientName;



    @SerializedName("recipientPhone")
    @Expose
    public String recipientPhone;



    @SerializedName("recipientAddress")
    @Expose
    public String recipientAddress;

    @SerializedName("codCharge")
    @Expose
    public String codCharge;

    @SerializedName("codStatus")
    @Expose
    public int codStatus;
    @SerializedName("codInfo")
    @Expose
    public String codInfo;


    /**
     * 生鲜信息  freshStatus = 0 普通运单 freshStatus = 1 生鲜单
     */
    @SerializedName("freshStatus")
    @Expose
    public int  freshStatus;

    // 1为改地址
    @SerializedName("changeAddStatus")
    @Expose
    public int  changeAddStatus;
    //具体修改的地址
    @SerializedName("changeAddInfo")
    @Expose
    public String  changeAddInfo;
    //1为精准电联
    @SerializedName("vipStatus")
    @Expose
    public int  vipStatus;

    //指派类型区分 到件  派件
    @SerializedName("optTypeDesc")
    @Expose
    public String  optTypeDesc;


    /**
     * 模块区分
     */
    //ApiModelProperty(value = "4:发往")
    @SerializedName("optType")
    @Expose
    public String optType;

    /**
     * 预付单
     */
    @SerializedName("prePayStatus")
    @Expose
    public int prePayStatus;


    /**
     * 驿站名称
     */
    @SerializedName("courierName")
    @Expose
    public String courierName;

    /**
     * 驿站编码
     */
    @SerializedName("courierCode")
    @Expose
    public String courierCode;





}
