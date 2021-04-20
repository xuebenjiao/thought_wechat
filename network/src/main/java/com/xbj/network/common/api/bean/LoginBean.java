package com.xbj.network.common.api.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * author : xbj
 * e-mail : xbjzhu@163.com
 * date   : 2019/10/23 16:45
 * desc   :
 */
public class LoginBean {
    @SerializedName("token")
    @Expose
    public String token;

    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("phone")
    @Expose
    public String phone;

    @SerializedName("headUrl")
    @Expose
    public String headUrl;

    @SerializedName("type")

    //1 是管理者
    @Expose
    public int type;

    @SerializedName("id")
    @Expose
    public String id;




   /* @SerializedName("jobNumber")
    @Expose
    public String jobNumber;

    @SerializedName("userPass")
    @Expose
    public String userPass;

    @SerializedName("userPhone")
    @Expose
    public String userPhone;

    @SerializedName("userToken")
    @Expose
    public String userToken;

    @SerializedName("originalPwd")
    @Expose
    public String originalPwd; //1 表示原始密码  0 不是原始密
    // 码
    @SerializedName("userName")
    @Expose
    public String userName; //1 用户名


    @SerializedName("orgName")
    @Expose
    public String orgName; //网点信息

    @SerializedName("roleType")
    @Expose
    public String roleType; //网点信息

    @SerializedName("roleName")
    @Expose
    public String roleName; //网点信息

    @SerializedName("expressLoginList")
    @Expose
    public List<ExpressLoginBean> expressLoginList;

    *//*2 是二级网点 3 是三级*//*
    @SerializedName("orgLevel")
    @Expose
    public String orgLevel;

    *//*2 是二级网点 3 是三级*//*
    @SerializedName("isAssign")
    @Expose
    public int isAssign;

    *//*是否显示快递柜*//*
    @SerializedName("isShowDiyi")
    @Expose
    public boolean isShowDiyi ;


    *//*2 是二级网点 3 是三级*//*
    @SerializedName("relList")
    @Expose
    public ArrayList<ExpressCmpCode> relList;

    public class ExpressCmpCode{
        *//*快递公司对应的orgCode
     * *//*
        @SerializedName("expressOrgCode")
        @Expose
        public String expressOrgCode;

        @SerializedName("platCode")
        @Expose
        public String platCode;

        @SerializedName("expressCompanyCode")
        @Expose
        public String expressCompanyCode;
    }*/
}
