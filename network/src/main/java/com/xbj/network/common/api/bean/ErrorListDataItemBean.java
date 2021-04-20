package com.xbj.network.common.api.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Time :2020/5/22
 * Author:xbj
 * Description :问题件列表实体
 */
public class ErrorListDataItemBean {
    /**
     * 自增unsigned ID
     */
    //(value = "自增unsigned ID")

    @SerializedName("id")
    @Expose
    public  Long id;
    /**
     * 运单编号
     */
    //(value = "运单编号")
    @SerializedName("waybillNo")
    @Expose
    public  String waybillNo;
    /**
     * 网点编号
     */
    //(value = "网点编号")
    @SerializedName("orgCode")
    @Expose
    public  String orgCode;

    //(value = "网点名称")
    @SerializedName("orgName")
    @Expose
    public  String orgName;
    /**
     * 操作业务员编号
     */
    //(value = "操作业务员编号")
    @SerializedName("optEmpCode")
    @Expose
    public  String optEmpCode;

    //(value = "操作业务员姓名")
    @SerializedName("optEmpName")
    @Expose
    public  String optEmpName;

    /**
     * 快递公司code
     */
    //(value = "快递公司code")
    @SerializedName("expressCompCode")
    @Expose
    public  String expressCompCode;

    //(value = "快递公司name")
    @SerializedName("expressCompName")
    @Expose
    public  String expressCompName;
    /**
     * 创建时间
     */
    //(value = "创建时间")
    @SerializedName("createTime")
    @Expose
    public  String createTime;

    /**
     * 收件人姓名
     */
    //(value = "收件人姓名")
    @SerializedName("recipientName")
    @Expose
    public  String recipientName;

    /**
     * 收件人联系方式
     */
    //(value = "收件人联系方式")
    @SerializedName("recipientPhone")
    @Expose
    public  String recipientPhone;

    /**
     * 收件人地址
     */
    //(value = "收件人地址")
    @SerializedName("recipientAddress")
    @Expose
    public  String recipientAddress;

    /**
     * 寄件人姓名
     */
    //(value = "寄件人姓名")
    @SerializedName("sendName")
    @Expose
    public  String sendName;

    /**
     * 寄件人联系方式
     */
    //(value = "寄件人联系方式")
    @SerializedName("sendPhone")
    @Expose
    public  String sendPhone;

    /**
     * 寄件人地址
     */
    //(value = "寄件人地址")
    @SerializedName("sendAddress")
    @Expose
    public  String sendAddress;

    /**
     * 付款方式 1 已付 2 代收  3 到付
     */
    //(value = "付款方式 1 已付 2 代收  3 到付")
    @SerializedName("payMent")
    @Expose
    public  int payMent;
    /**
     * 付款金额单位分
     */
    //(value = "付款金额单位分")
    @SerializedName("payAmount")
    @Expose
    public  Long payAmount;

    /**
     * 展示金额
     */
    //(value = "展示金额")
    @SerializedName("showAmount")
    @Expose
    public  String showAmount;
    /**
     * 问题件原因描述
     */
    //(value = "问题件原因描述")
    @SerializedName("errorMessage")
    @Expose
    public  String errorMessage;

    /**
     * 问题件状态 0 上报 1 已处理
     */
    //(value = "问题件状态 0 上报 1 已处理")
    @SerializedName("errorStatus")
    @Expose
    public  Integer errorStatus;

    @SerializedName("pushStatus")
    @Expose
    public  Integer pushStatus;

    @SerializedName("reportPic")
    @Expose
    public  String reportPic;

    @SerializedName("errorType")
    @Expose
    public  String errorType;

    @SerializedName("errorTypeDesc")
    @Expose
    public  String errorTypeDesc;

    //异常小类
    @SerializedName("serrorType")
    @Expose
    public  String serrorType;

    @SerializedName("serrorTypeDesc")
    @Expose
    public  String serrorTypeDesc;



    @SerializedName("reportCode")
    @Expose
    public  String reportCode;

    @SerializedName("reportDesc")
    @Expose
    public  String reportDesc;

    @SerializedName("updateTime")
    @Expose
    //JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    public  String updateTime;



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


    /**
     * 预付单
     */
    @SerializedName("prePayStatus")
    @Expose
    public int prePayStatus;
}
