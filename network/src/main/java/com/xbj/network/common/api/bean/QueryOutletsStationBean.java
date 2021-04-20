package com.xbj.network.common.api.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Time :2021/2/24
 * Author:xbj
 * Description : 添加网点/驿站 请求实体
 */
public class QueryOutletsStationBean {
    //网点/驿站名称
    @SerializedName("id")
    @Expose
    public int id;
    //网点/驿站名称
    @SerializedName("orgName")
    @Expose
    public String orgName;

    //省市区名称与code
    @SerializedName("provinceCode")
    @Expose
    public String provinceCode;

    @SerializedName("provinceName")
    @Expose
    public String provinceName;

    @SerializedName("cityCode")
    @Expose
    public String cityCode;

    @SerializedName("cityName")
    @Expose
    public String cityName;

    @SerializedName("arrivedNum")
    @Expose
    public String areaCode;

    @SerializedName("areaName")
    @Expose
    public String areaName;

    @SerializedName("orgAddress")
    @Expose
    //详细地址
    public String orgAddress;

    @SerializedName("orgPhone")
    @Expose
    //电话
    public String orgPhone;

    @SerializedName("orgScore")
    @Expose
    //评分
    public Float orgScore;

    @SerializedName("orgShareType")
    @Expose
    //网点类型 1-》共享   2 -》非共享
    public int orgShareType;

    @SerializedName("businessTime")
    @Expose
    //营业时间
    public String businessTime;

    @SerializedName("expressBrand")
    @Expose
    //快递/驿站品牌
    public String expressBrand;

    @SerializedName("orgImages")
    @Expose
    //网点/驿站图片连接
    public ArrayList<String> orgImages;

    @SerializedName("employeeAmount")
    @Expose
    //业务员数量
    public int employeeAmount;

    @SerializedName("employeeYear")
    @Expose
    //从业年限
    public int employeeYear;


    @SerializedName("orgType")
    @Expose
    //网点/驿站标识  1-》网点；  2-》驿站
    public int orgType;

    //法人姓名
    @SerializedName("legalPersonName")
    @Expose
    public String  legalPersonName;


    //法人电话
    @SerializedName("legalPersonPhone")
    @Expose
    public String  legalPersonPhone;

    @SerializedName("businessImages")
    @Expose
    //营业执照图片
    public ArrayList<String> businessImages;
}
