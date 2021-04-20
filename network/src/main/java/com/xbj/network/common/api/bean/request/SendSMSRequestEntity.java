package com.xbj.network.common.api.bean.request;

import java.util.List;

public class SendSMSRequestEntity {
    /**
     * empCode : string
     * empName : string
     * empPhone : string
     * orgCode : string
     * orgName : string
     * smsSendInfoRequestList : [{"content":"string","isDefaultTemplate":0,"logisticsCode":"string","mobile":"string","msgId":"string","recipientName":"string","sendId":0,"sendResultDesc":"string","sendStatus":0,"sendTime":"2020-08-19T12:10:23.143Z","templateMsg":"string","type":"string","waybillNo":"string"}]
     */

    private String empCode;
    private String empName;
    private String empPhone;
    private String orgCode;
    private String orgName;
    private int optType;
    private List<SmsSendInfoRequestBean> smsSendInfoRequestList;

    public String getEmpCode() {
        return empCode;
    }

    public void setEmpCode(String empCode) {
        this.empCode = empCode;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getEmpPhone() {
        return empPhone;
    }

    public void setEmpPhone(String empPhone) {
        this.empPhone = empPhone;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public List<SmsSendInfoRequestBean> getSmsSendInfoRequestList() {
        return smsSendInfoRequestList;
    }

    public void setSmsSendInfoRequestList(List<SmsSendInfoRequestBean> smsSendInfoRequestList) {
        this.smsSendInfoRequestList = smsSendInfoRequestList;
    }

    public int getOptType() {
        return optType;
    }

    public void setOptType(int optType) {
        this.optType = optType;
    }
}
