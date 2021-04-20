package com.xbj.mode;

import androidx.annotation.NonNull;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Time :2020/2/25
 * Author:xbj
 * Description : 线路频次实体
 */
@Entity
public class LineFrequencyModel {
    @Id(autoincrement = true)
    private Long id;
    //endOrgCode对应的是线路中的lineCode
    private String endOrgCode;
    private String endOrgName;
    //网点编码
    private String orgCode;
    private String lineFrequencyName;//线路频次名称
    private String lineFrequencyNo;//线路频次code
    //工号 区分不通过的用户
    private String jobNumber;
    @Generated(hash = 189494140)
    public LineFrequencyModel(Long id, String endOrgCode, String endOrgName,
            String orgCode, String lineFrequencyName, String lineFrequencyNo,
            String jobNumber) {
        this.id = id;
        this.endOrgCode = endOrgCode;
        this.endOrgName = endOrgName;
        this.orgCode = orgCode;
        this.lineFrequencyName = lineFrequencyName;
        this.lineFrequencyNo = lineFrequencyNo;
        this.jobNumber = jobNumber;
    }
    @Generated(hash = 684465534)
    public LineFrequencyModel() {
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
    public String getLineFrequencyName() {
        return this.lineFrequencyName;
    }
    public void setLineFrequencyName(String lineFrequencyName) {
        this.lineFrequencyName = lineFrequencyName;
    }
    public String getLineFrequencyNo() {
        return this.lineFrequencyNo;
    }
    public void setLineFrequencyNo(String lineFrequencyNo) {
        this.lineFrequencyNo = lineFrequencyNo;
    }

    @NonNull
    @Override
    public String toString() {
        return lineFrequencyName;
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
