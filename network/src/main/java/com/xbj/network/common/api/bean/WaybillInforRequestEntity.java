package com.xbj.network.common.api.bean;

import com.thoughtwork.base.constants.Constants;
import com.thoughtwork.base.utils.SPUtils;

/**
 * Time :2020/5/9
 * Author:xbj
 * Description :
 */
public class WaybillInforRequestEntity {
    private String expressCode ;
    private String userCode = SPUtils.getStringValue(Constants.JOB_NUMBER);
    private String waybillNo;
    public WaybillInforRequestEntity(){

    }
    public WaybillInforRequestEntity(String waybillNo,String expressCode){
        this.waybillNo = waybillNo;
        this.expressCode = expressCode;
    }
}
