package com.xbj.network.common.api.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Time :2020/2/24
 * Author:xbj
 * Description :
 */
public class LineRelatedDataEntity {
    @SerializedName("lineRelatedList")
    @Expose
    public ArrayList<LineRelatedEntity> lineRelatedList;

    @SerializedName("nextOrgCodeRspList")
    @Expose
    public ArrayList<NextOrgCodeEntity> nextOrgCodeRspList;


}
