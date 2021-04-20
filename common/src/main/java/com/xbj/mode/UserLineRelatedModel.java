package com.xbj.mode;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Time :2020/3/6
 * Author:xbj
 * Description : 发往界面 用户选择的线路数据
 */
@Entity
public class UserLineRelatedModel {
    @Id(autoincrement = true)//设置自增长
    private   Long id;
    //工号 区分不通过的用户
    private String jobNumber;
    //用户选择的线路信息
    private String selectLineStr;

    //快递公司编码
    private String   expressCompanyType;

    //
    private String nextOrgCode;//关联线路/线路频次实体中的endOrgCode
    @Generated(hash = 894565426)
    public UserLineRelatedModel(Long id, String jobNumber, String selectLineStr,
            String expressCompanyType, String nextOrgCode) {
        this.id = id;
        this.jobNumber = jobNumber;
        this.selectLineStr = selectLineStr;
        this.expressCompanyType = expressCompanyType;
        this.nextOrgCode = nextOrgCode;
    }
    @Generated(hash = 728065749)
    public UserLineRelatedModel() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getJobNumber() {
        return this.jobNumber;
    }
    public void setJobNumber(String jobNumber) {
        this.jobNumber = jobNumber;
    }
    public String getSelectLineStr() {
        return this.selectLineStr;
    }
    public void setSelectLineStr(String selectLineStr) {
        this.selectLineStr = selectLineStr;
    }
    public String getNextOrgCode() {
        return this.nextOrgCode;
    }
    public void setNextOrgCode(String nextOrgCode) {
        this.nextOrgCode = nextOrgCode;
    }

    public String getExpressCompanyType() {
        return expressCompanyType;
    }

    public void setExpressCompanyType(String expressCompanyType) {
        this.expressCompanyType = expressCompanyType;
    }
}
