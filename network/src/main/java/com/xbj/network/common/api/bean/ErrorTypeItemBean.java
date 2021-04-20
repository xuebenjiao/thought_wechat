package com.xbj.network.common.api.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Time :2020/5/22
 * Author:xbj
 * Description :
 */
public class ErrorTypeItemBean {

    /*"code":"01",
"name":"送无人，无法联系客户",
"status":"VALID",
"smallCatList":*/
    @SerializedName("code")
    @Expose
    public String    code ;

    @SerializedName("name")
    @Expose
    public String    name ;

    @SerializedName("status")
    @Expose
    public String    status ;

    @SerializedName("smallCatList")
    @Expose
    public ArrayList<SmllCat> smallCatList ;
    public class SmllCat{
             /*    "code":"0107",
                "name":"【客户原因】送去客户处无人，收件人电话错误(C)",
                "status":"VALID",
                "bigCode":"01"*/

        @SerializedName("code")
        @Expose
        public String   code ;

        @SerializedName("name")
        @Expose
        public String    name ;

        @SerializedName("status")
        @Expose
        public String   status ;

        @SerializedName("bigCode")
        @Expose
        public String          bigCode ;
    }
}
