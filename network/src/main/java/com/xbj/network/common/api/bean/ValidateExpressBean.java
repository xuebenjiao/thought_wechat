package com.xbj.network.common.api.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Time :2020/4/9
 * Author:xbj
 * Description :
 */
public class ValidateExpressBean {

/*
CURRENT_PLATFORM_NOT_LOGIN(-1, "当前平台用户未登录或登录失效"),

NORMAL(200, "正常单"),
INTERCEPT(604, "通缉件"),
NOT_SUPPORT(403, "%s-%s操作暂不支持"),
ARRIVED(627, "已做过到件"),
DISPATCHED(609, "已做过派件"),
SIGNED(612, "已做过签收"),
PUSH_ACTION_ERROR(-2, "pushAction指定有误")
"code":200,
"expressCode":"yuantong",
"expressName":"圆通速递",
"message":"正常单",
"waybillNo":"YT2046946340740"*/

    @SerializedName("code")
    @Expose
    public int code;

    @SerializedName("expressCode")
    @Expose
    public String  expressCode;

    @SerializedName("expressName")
    @Expose
    public String  expressName;

    @SerializedName("message")
    @Expose
    public String  message;

    @SerializedName("waybillNo")
    @Expose
    public String  waybillNo;
    /**
     !1 - 非到付件，1 - 到付件
     此状态为1的单子需要给用户弹窗提醒
     */
    @SerializedName("codStatus")
    @Expose
    public int  codStatus;

    /**
     !提示信息
     */
    @SerializedName("codInfo")
    @Expose
    public String  codInfo;
    /**
     * 代收/到付金额
     */
    @SerializedName("codCharge")
    @Expose
    public String  codCharge;

    /**
     !提示信息 1为弹框提示
     */
    @SerializedName("tipStatus")
    @Expose
    public int  tipStatus;
    /**
     * 提示信息
     */
    @SerializedName("tipMsg")
    @Expose
    public String  tipMsg;
    /**
     * 生鲜信息  freshStatus = 0 普通运单 freshStatus = 1 生鲜单
     */
    @SerializedName("freshStatus")
    @Expose
    public int  freshStatus;
    /**
     * 生鲜信息
     */
    @SerializedName("freshMsg")
    @Expose
    public String  freshMsg;

    /**
     * 预付单标识 1 为预付单
     */
    @SerializedName("prePayStatus")
    @Expose
    public int  prePayStatus;

    /**
     * 预付单信息
     */
    @SerializedName("prePayMsg")
    @Expose
    public String  prePayMsg;



}
