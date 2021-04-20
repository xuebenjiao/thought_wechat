package com.xbj.network.common.api.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Time :2020/5/15
 * Author:xbj
 * Description :
 */
public class  WaitDeliveryListBean {
    @SerializedName("pageNo")
    @Expose
    public int pageNo;
    @SerializedName("total")
    @Expose
    public int total;
    @SerializedName("pages")
    @Expose
    public int pages;
    @SerializedName("resultData")
    @Expose
    public List<ResultDataBean> resultData;

    public  class ResultDataBean {
        /**
         * 自增unsigned ID
         */
        // "自增unsigned ID")
        @SerializedName("id")
        @Expose
        public long id;
        /**
         * order 表中关联ID
         */
        // "order 表中关联ID")

        @SerializedName("orderId")
        @Expose
        public long orderId;
        /**
         * 运单编号
         */
        // "运单编号")
        @SerializedName("waybillNo")
        @Expose
        public String waybillNo;
        /**
         * 网点编号
         */
        // "网点编号")
        @SerializedName("orgCode")
        @Expose
        public String orgCode;
        /**
         * 操作业务员编号
         */
        // "操作业务员编号")
        @SerializedName("optEmpCode")
        @Expose
        public String optEmpCode;
        /**
         * 快递公司code
         */
        // "快递公司code")
        @SerializedName("expressCompCode")
        @Expose
        public String expressCompCode;
        /**
         * 创建时间
         */
        // "创建时间")
        @SerializedName("createTime")
        @Expose
        public String createTime;
        /**
         * 推送时间
         */
        // "推送时间")
        @SerializedName("deliveryTime")
        @Expose
        public String deliveryTime;
        /**
         * APP请求时间
         */
        @SerializedName("uploadTime")
        @Expose
        public String uploadTime;
        /**
         * 收件人姓名
         */
        @SerializedName("recipientName")
        @Expose
        public String recipientName;
        /**
         * 收件人联系方式
         */
        @SerializedName("recipientPhone")
        @Expose
        public String recipientPhone;
        /**
         * 收件人地址
         */
        @SerializedName("recipientAddress")
        @Expose
        public String recipientAddress;
        /**
         * 寄件人姓名
         */
        @SerializedName("sendName")
        @Expose
        public String sendName;
        /**
         * 寄件人联系方式
         */
        @SerializedName("sendPhone")
        @Expose
        public String sendPhone;
        /**
         * 寄件人地址
         */
        @SerializedName("sendAddress")
        @Expose
        public String sendAddress;
        /**
         * 付款方式 1 已付 2 代收  3 到付
         */
        @SerializedName("payMent")
        @Expose
        public int payMent;
        /**
         * 付款金额单位分
         */
        @SerializedName("payAmount")
        @Expose
        public long payAmount;
        /**
         * 展示金额
         */
        @SerializedName("showAmount")
        @Expose
        public String showAmount;
        /**
         * 签收时间
         */
        @SerializedName("signTime")
        @Expose
        public String signTime;
        /**
         * 签收方式 1 本人 2 家人 ...
         */
        @SerializedName("signMent")
        @Expose
        public int signMent;
        /**
         * -1 其它系统派送删除 0 待派 1 签收...
         */
        @SerializedName("delieverStatus")
        @Expose
        public int delieverStatus;
        /**
         * 是否到派合一 0  否 1 是
         */
        @SerializedName("isUnion")
        @Expose
        public Integer isUnion;
        /**
         * 最后一次操作时间
         */
        @SerializedName("updateTime")
        @Expose
        public String updateTime;


        /**
         * !1 - 非到付件，1 - 到付件
         * 此状态为1的单子需要给用户弹窗提醒
         */
        @SerializedName("codStatus")
        @Expose
        public int codStatus;
        /**
         * !提示信息
         */
        @SerializedName("codInfo")
        @Expose
        public String codInfo;
        /**
         * 代收/到付金额
         */
        @SerializedName("codCharge")
        @Expose
        public String codCharge;

        @SerializedName("waitDeliveDate")
        @Expose
        public String waitDeliveDate;



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
         * 预付单
         */
        @SerializedName("prePayStatus")
        @Expose
        public int prePayStatus;


        /**
         * 短信发送状态 0 未发送 1发送中 2成功 3 失败
         */
        @SerializedName("smsStatus")
        @Expose
        public int  smsStatus;
    }

}
