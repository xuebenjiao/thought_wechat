package com.xbj.common.entity.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Time :2021/3/15
 * Author:xbj
 * Description :
 */
public class PublishResponBean {
    @SerializedName("taskTypeId")
    @Expose
    public String taskTypeId;

    @SerializedName("taskTypeName")
    @Expose
    public String taskTypeName;
    @SerializedName("cityList")
    @Expose
    public List<CytyBean> cityList;


    public   class CytyBean implements Serializable {
        @SerializedName("cityCode")
        @Expose
        public String  cityCode;

        @SerializedName("cityName")
        @Expose
        public String  cityName;

        @SerializedName("orgList")
        @Expose
        public List<OrgBean> orgList;
    }
    public  class OrgBean implements Serializable{
        @SerializedName("orgId")
        @Expose
        public String  orgId;

        @SerializedName("orgName")
        @Expose
        public String  orgName;
    }

    @Override
    public String toString() {
        return taskTypeName ;
    }
}
