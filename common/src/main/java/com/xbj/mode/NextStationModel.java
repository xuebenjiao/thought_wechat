package com.xbj.mode;

import androidx.annotation.NonNull;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Time :2020/2/25
 * Author:xbj
 * Description : 下一站存储对象
 */
@Entity
public class NextStationModel {
    @Id(autoincrement = true)
    private Long id;
    //工号 区分不通过的用户
    private String jobNumber;
    private String orgCode;//网点编码
    private String nextOrgName;
    private String nextOrgCode;//关联线路/线路频次实体中的endOrgCode
    @Generated(hash = 901718317)
    public NextStationModel(Long id, String jobNumber, String orgCode,
            String nextOrgName, String nextOrgCode) {
        this.id = id;
        this.jobNumber = jobNumber;
        this.orgCode = orgCode;
        this.nextOrgName = nextOrgName;
        this.nextOrgCode = nextOrgCode;
    }
    @Generated(hash = 2140576941)
    public NextStationModel() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getOrgCode() {
        return this.orgCode;
    }
    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }
    public String getNextOrgName() {
        return this.nextOrgName;
    }
    public void setNextOrgName(String nextOrgName) {
        this.nextOrgName = nextOrgName;
    }
    public String getNextOrgCode() {
        return this.nextOrgCode;
    }
    public void setNextOrgCode(String nextOrgCode) {
        this.nextOrgCode = nextOrgCode;
    }

    @NonNull
    @Override
    public String toString() {
        return this.nextOrgName;
    }
    public String getJobNumber() {
        return this.jobNumber;
    }
    public void setJobNumber(String jobNumber) {
        this.jobNumber = jobNumber;
    }
}
