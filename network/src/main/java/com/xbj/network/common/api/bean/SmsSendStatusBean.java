package com.xbj.network.common.api.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Time :2020/9/16
 * Author:xbj
 * Description : 短信发送状态解析实体
 */
public class SmsSendStatusBean {
      //发送时间
      @SerializedName("sendTime")
      @Expose
      public String sendTime;
      //发送状态
      @SerializedName("sendStatus")
      @Expose
      public int sendStatus;
      //总共发送的条数
      @SerializedName("smsTotal")
      @Expose
      public int smsTotal;
      //运单号
      @SerializedName("waybillNo")
      @Expose
      public String waybillNo;

}
