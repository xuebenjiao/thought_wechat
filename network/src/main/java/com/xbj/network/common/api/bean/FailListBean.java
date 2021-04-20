package com.xbj.network.common.api.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Time :2020/5/7
 * Author:xbj
 * Description :
 */
public class FailListBean {
    /**
     * 自增unsigned ID
     */
    //ApiModelProperty(value = "自增unsigned ID")
    @SerializedName("id")
    @Expose
    public Long id;
    /**
     * 运单编号
     */
    //ApiModelProperty(value = "运单编号")
    @SerializedName("waybillNo")
    @Expose
    public String waybillNo;



    //ApiModelProperty(value = "运单编号")
    @SerializedName("wayBillNo")
    @Expose
    public String wayBillNo;
    /**
     * 网点编号
     */
    //ApiModelProperty(value = "网点编号")
    @SerializedName("orgCode")
    @Expose
    public String orgCode;
    /**
     * 快递公司编号
     */
    //ApiModelProperty(value = "快递公司编号")
    @SerializedName("expressCompCode")
    @Expose
    public String expressCompCode;
    /**
     * 业务员编号
     */
    //ApiModelProperty(value = "业务员编号")
    @SerializedName("empCode")
    @Expose
    public String empCode;
    /**
     * 操作人姓名（冗余字段）
     */
    //ApiModelProperty(value = "操作人姓名（冗余字段）")
    @SerializedName("empName")
    @Expose
    public String empName;
    /**
     * 扫描时间
     */
    //ApiModelProperty(value = "扫描时间")
    @SerializedName("scanTime")
    @Expose
    public String scanTime;
    /**
     * 推送状态（-1-未扫描，0-未推送，1-推送成功，2-推送失败，3-推送中）
     */
    //ApiModelProperty(value = "推送状态（-1-未扫描，0-未推送，1-推送成功，2-推送失败，3-推送中）")
    @SerializedName("pushStatus")
    @Expose
    public Integer pushStatus;
    /**
     * 推送时间
     */
    //ApiModelProperty(value = "推送时间")
    @SerializedName("pushTime")
    @Expose
    public String pushTime;
    /**
     * 推送方式（1-自动，2-手动）
     */
    //ApiModelProperty(value = "推送方式（1-自动，2-手动）")
    @SerializedName("pushStyle")
    @Expose
    public Integer pushStyle;
    /**
     * 数据来源（1-导入，2-扫描）
     */
    //ApiModelProperty(value = "数据来源（1-导入，2-扫描）")
    @SerializedName("dataFrom")
    @Expose
    public Integer dataFrom;
    /**
     * 推送错误信息
     */
    //ApiModelProperty(value = "推送错误信息")
    @SerializedName("pushErrorMessage")
    @Expose
    public String pushErrorMessage;
    /**
     * 状态（0-正常，-1-删除）
     */
    //ApiModelProperty(value = "状态（0-正常，-1-删除）")
    @SerializedName("dataFlag")
    @Expose
    public Integer dataFlag;
    /**
     * 快递公司下一站
     */
    //ApiModelProperty(value = "快递公司下一站")
    @SerializedName("nextExpressOrgCode")
    @Expose
    public String nextExpressOrgCode;
    /**
     * 4:发往
     */
    //ApiModelProperty(value = "4:发往")
    @SerializedName("optType")
    @Expose
    public String optType;
    /**
     * 线路号
     */
    //ApiModelProperty(value = "线路号")
    @SerializedName("lineNo")
    @Expose
    public String lineNo;
    /**
     * 频次号
     */
    //ApiModelProperty(value = "频次号")
    @SerializedName("frequencyNo")
    @Expose
    public String frequencyNo;
    /**
     * 组织机构类型
     */
    //ApiModelProperty(value = "组织机构类型")
    @SerializedName("opOrgType")
    @Expose
    public String opOrgType;
    /**
     * 进出港标识
     */
    //ApiModelProperty(value = "进出港标识")
    @SerializedName("inOutFlag")
    @Expose
    public String inOutFlag;
    /**
     * 运单类型
     */
    //ApiModelProperty(value = "运单类型")
    @SerializedName("expType")
    @Expose
    public String expType;
    /**
     * 快递内容类型
     */
    //ApiModelProperty(value = "快递内容类型")
    @SerializedName("expressContentCode")
    @Expose
    public String expressContentCode;
    /**
     * 发往快递公司记录json格式（net.yto.common.model.request.appserver.OptItemDto）
     */
    //ApiModelProperty(value = "发往快递公司记录json格式（net.yto.common.model.request.appserver.OptItemDto）")
    @SerializedName("jsonExpressList")
    @Expose
    public String jsonExpressList;
    /**
     * 推送响应编码
     */
    //ApiModelProperty(value = "推送响应编码")
    @SerializedName("responseCode")
    @Expose
    public String responseCode;
    /**
     * 推送响应描述
     */
    //ApiModelProperty(value = "推送响应描述")
    @SerializedName("responseMessage")
    @Expose
    public String responseMessage;
    /**
     * 车签号
     */
    //ApiModelProperty(value = "车签号")
    @SerializedName("carSignedCode")
    @Expose
    public String carSignedCode;
    /**
     * 三段码
     */
    //ApiModelProperty(value = "三段码")
    @SerializedName("partThreeCode")
    @Expose
    public String partThreeCode;
    /**
     * 受理状态
     */
    //ApiModelProperty(value = "受理状态")
    @SerializedName("processStatus")
    @Expose
    public Integer processStatus;
    /**
     * 签收人
     */
    //ApiModelProperty(value = "签收人")
    @SerializedName("signBy")
    @Expose
    public String signBy;
    /**
     * 收件人姓名
     */
    //ApiModelProperty(value = "收件人姓名")
    @SerializedName("recipientName")
    @Expose
    public String recipientName;
    /**
     * 收件人联系方式
     */
    //ApiModelProperty(value = "收件人联系方式")
    @SerializedName("recipientPhone")
    @Expose
    public String recipientPhone;
    /**
     * 收件人地址
     */
    //ApiModelProperty(value = "收件人地址")
    @SerializedName("recipientAddress")
    @Expose
    public String recipientAddress;
    /**
     * 寄件人姓名
     */
    //ApiModelProperty(value = "寄件人姓名")
    @SerializedName("sendName")
    @Expose
    public String sendName;
    /**
     * 寄件人联系方式
     */
    //ApiModelProperty(value = "寄件人联系方式")
    @SerializedName("sendPhone")
    @Expose
    public String sendPhone;
    /**
     * 寄件人地址
     */
    //ApiModelProperty(value = "寄件人地址")
    @SerializedName("sendAddress")
    @Expose
    public String sendAddress;
    /**
     * 付款方式 1 已付 2 代收  3 到付
     */
    //ApiModelProperty(value = "付款方式 1 已付 2 代收  3 到付")
    @SerializedName("payMent")
    @Expose
    public int payMent;
    /**
     * 展示金额
     */
    //ApiModelProperty(value = "展示金额")
    @SerializedName("showAmount")
    @Expose
    public String showAmount;
    /**
     * 展示金额
     */
    //ApiModelProperty(value = "展示金额")
    @SerializedName("codInfo")
    @Expose
    public String codInfo;


    /**
     * 展示金额
     */
    //ApiModelProperty(value = "展示金额")
    @SerializedName("codStatus")
    @Expose
    public int codStatus;




    @SerializedName("nextExpressOrgName")
    @Expose
    public String nextExpressOrgName;


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

    //驿站编码
    @SerializedName("courierCode")
    @Expose
    public String  courierCode;

    //驿站名称
    @SerializedName("courierName")
    @Expose
    public String  courierName;

    /**
     * 预付单
     */
    @SerializedName("prePayStatus")
    @Expose
    public int prePayStatus;

}
