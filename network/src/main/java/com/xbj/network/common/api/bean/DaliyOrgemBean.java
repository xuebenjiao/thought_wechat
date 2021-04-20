package com.xbj.network.common.api.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DaliyOrgemBean {
        /*arrivedNum 到件量 deliveryNum 派件量 signNum 签收量*/
        @SerializedName("arrivedNum")
        @Expose
        public String arrivedNum;

        @SerializedName("deliveryNum")
        @Expose
        public String deliveryNum;

        @SerializedName("signNum")
        @Expose
        public String signNum;


        @SerializedName("needDeliveryNum")
        @Expose
        public String needDeliveryNum;

        @SerializedName("errorPakcageNum")
        @Expose
        public String errorPakcageNum;
}
