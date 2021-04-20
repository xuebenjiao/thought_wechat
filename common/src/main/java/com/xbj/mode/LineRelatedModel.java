package com.xbj.mode;

import androidx.annotation.NonNull;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Time :2020/2/24
 * Author:xbj
 * Description :线路实体
 */
@Entity
public class LineRelatedModel {
    @Id(autoincrement = true)
    private Long id;
    //工号 区分不同的用户
    private String jobNumber;
    //网点编码
    private String orgCode;
    private String endOrgCode;
    private String endOrgName;
    private String lineName;//线路名称
    private String lineNo;//线路code
  @Generated(hash = 764917186)
public LineRelatedModel(Long id, String jobNumber, String orgCode,
        String endOrgCode, String endOrgName, String lineName, String lineNo) {
    this.id = id;
    this.jobNumber = jobNumber;
    this.orgCode = orgCode;
    this.endOrgCode = endOrgCode;
    this.endOrgName = endOrgName;
    this.lineName = lineName;
    this.lineNo = lineNo;
}
    @Generated(hash = 956546274)
    public LineRelatedModel() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getEndOrgCode() {
        return this.endOrgCode;
    }
    public void setEndOrgCode(String endOrgCode) {
        this.endOrgCode = endOrgCode;
    }
    public String getEndOrgName() {
        return this.endOrgName;
    }
    public void setEndOrgName(String endOrgName) {
        this.endOrgName = endOrgName;
    }
    public String getLineName() {
        return this.lineName;
    }
    public void setLineName(String lineName) {
        this.lineName = lineName;
    }
    public String getLineNo() {
        return this.lineNo;
    }
    public void setLineNo(String lineNo) {
        this.lineNo = lineNo;
    }

    @NonNull
    @Override
    public String toString() {
        return lineName;
    }
    public String getJobNumber() {
        return this.jobNumber;
    }
    public void setJobNumber(String jobNumber) {
        this.jobNumber = jobNumber;
    }
    public String getOrgCode() {
        return this.orgCode;
    }
    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }
}
