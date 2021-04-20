package com.thoughtwork.base.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Time :2019/11/25
 * Author:xbj
 * Description :
 */
public class UpdateBean {

        @SerializedName("appName")
        @Expose
        public String appName;

        @SerializedName("appVersion")
        @Expose
        public String appVersion;

        @SerializedName("serverFlag")
        @Expose
        public int  serverFlag;
        /**
         * 1 标识强制更新
         */
        @SerializedName("lastForce")
        @Expose
        public int lastForce;

        @SerializedName("upgradeUrl")
        @Expose
        public String upgradeUrl;


        @SerializedName("upgradeInfo")
        @Expose
        public String upgradeInfo;

        @SerializedName("createTime")
        @Expose
        public String createTime;

        @SerializedName("updateType")
        @Expose
        public String updateType;

        @SerializedName("versionNum")
        @Expose
        public int versionNum;
}
