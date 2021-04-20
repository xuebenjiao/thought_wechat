package com.xbj.mode;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Time :2020/4/27
 * Author:xbj
 * Description :
 */
@Entity
public class CustomeSignTypeModel {
    @Id(autoincrement = true)//设置自增长
    private   Long id;
    //自定义签收大类
    private String titleName;
    //自定义签收大类code
    private String titleCode;

//是否用户自定义
    private boolean  isCustomeType;
    //工号 区分不通过的用户
    private String jobNumber;
    @Generated(hash = 720984394)
    public CustomeSignTypeModel(Long id, String titleName, String titleCode,
            boolean isCustomeType, String jobNumber) {
        this.id = id;
        this.titleName = titleName;
        this.titleCode = titleCode;
        this.isCustomeType = isCustomeType;
        this.jobNumber = jobNumber;
    }
    @Generated(hash = 755328622)
    public CustomeSignTypeModel() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTitleName() {
        return this.titleName;
    }
    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }
    public String getTitleCode() {
        return this.titleCode;
    }
    public void setTitleCode(String titleCode) {
        this.titleCode = titleCode;
    }
    public String getJobNumber() {
        return this.jobNumber;
    }
    public void setJobNumber(String jobNumber) {
        this.jobNumber = jobNumber;
    }
    public boolean getIsCustomeType() {
        return this.isCustomeType;
    }
    public void setIsCustomeType(boolean isCustomeType) {
        this.isCustomeType = isCustomeType;
    }
}
