package com.xbj.network.common.api.bean.request;

/**
 * Time :2021/1/15
 * Author:xbj
 * Description :
 */
public class SmsSendInfoRequestBean {
    /**
     * content : string
     * isDefaultTemplate : 0
     * logisticsCode : string
     * mobile : string
     * msgId : string
     * recipientName : string
     * sendId : 0
     * sendResultDesc : string
     * sendStatus : 0
     * sendTime : 2020-08-19T12:10:23.143Z
     * templateMsg : string
     * type : string
     * waybillNo : string
     */

    private String content;
    private int isDefaultTemplate;
    private String logisticsCode;
    private String mobile;
    private String msgId;
    private String recipientName;
    private int sendId;
    private String sendResultDesc;
    private int sendStatus;
    private String sendTime;
    private String templateMsg;
    private String type;
    private String waybillNo;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getIsDefaultTemplate() {
        return isDefaultTemplate;
    }

    public void setIsDefaultTemplate(int isDefaultTemplate) {
        this.isDefaultTemplate = isDefaultTemplate;
    }

    public String getLogisticsCode() {
        return logisticsCode;
    }

    public void setLogisticsCode(String logisticsCode) {
        this.logisticsCode = logisticsCode;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public int getSendId() {
        return sendId;
    }

    public void setSendId(int sendId) {
        this.sendId = sendId;
    }

    public String getSendResultDesc() {
        return sendResultDesc;
    }

    public void setSendResultDesc(String sendResultDesc) {
        this.sendResultDesc = sendResultDesc;
    }

    public int getSendStatus() {
        return sendStatus;
    }

    public void setSendStatus(int sendStatus) {
        this.sendStatus = sendStatus;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getTemplateMsg() {
        return templateMsg;
    }

    public void setTemplateMsg(String templateMsg) {
        this.templateMsg = templateMsg;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getWaybillNo() {
        return waybillNo;
    }

    public void setWaybillNo(String waybillNo) {
        this.waybillNo = waybillNo;
    }
}
