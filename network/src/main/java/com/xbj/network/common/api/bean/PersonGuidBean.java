package com.xbj.network.common.api.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Time :2020/5/15
 * Author:xbj
 * Description :
 */
public class PersonGuidBean {
/*     "data": {
        "aboutUs": "<p style=\"text-align: justify;\">你好呀99</p>",
                "serviceAgreement": "<p>3333333333333333333333333333</p>",
                "privacyPolicy": "<p style=\"text-align: justify;\">的</p>"
    }*/
    @SerializedName("aboutUs")
    @Expose
    public String aboutUs;

    @SerializedName("serviceAgreement")
    @Expose
    public String serviceAgreement;

    @SerializedName("privacyPolicy")
    @Expose
    public String privacyPolicy;
}
