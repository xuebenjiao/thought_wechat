package com.xbj.mode;

public class ScanRecordModel {
    public String searchContent;
    public String startTime;
    public String  endTime ;
    //快递公司编号
    public  String expressCmpCode;
    //下一站编号
    public  String nextExpressOrgCode ;
    public String mCurrentTabName;
    public ScanRecordModel(){

    }
    public ScanRecordModel(String searchContent,String startTime,String endTime,String expressCmpCode,String nextExpressOrgCode,String currentTabName){
        this.searchContent = searchContent;
        this.startTime = startTime;
        this.endTime = endTime;
        this.expressCmpCode = expressCmpCode;
        this.nextExpressOrgCode  = nextExpressOrgCode;
        this.mCurrentTabName = currentTabName;

    }
}
