package com.xbj.network.common.api.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Time :2020/7/15
 * Author:xbj
 * Description : 指派员信息信息实体
 */
public class DeliveryPersonBean {
    //派件员编码
    @SerializedName("empCode")
    @Expose
    public String empCode;

    //派件员姓名
    @SerializedName("empName")
    @Expose
    public String empName;

    //派件员拼接快递绑定的快递公司
    @SerializedName("expressListStr")
    @Expose
    public String expressListStr;

    @Override
    public String toString() {
        return empName +"+"+ empCode;
    }
}
