package com.xbj.common.entity.pageentity;

import androidx.databinding.Bindable;

import com.thoughtwork.base.constants.Constants;
import com.thoughtwork.base.utils.DateUtil;
import com.thoughtwork.base.utils.SPUtils;

/**
 * Time :2020/9/2
 * Author:xbj
 * Description :
 */
public class BasePageEntity extends SearchAreaPageEntity {
    //开始日期
    public String startTime = DateUtil.getFutureOrLastDate(0)+" 00:00:00";
    //截至日期
    public String endTime = DateUtil.getFutureOrLastDate(0)+" 23:59:59";

    //运单类型 1、COD到付，2、精准电联，3、生鲜 4、普通订单 5、改址
    //本地数据库中freshStatus 为1是生鲜单  vipStatus 1 为精准电联  codStatus 为1 cod
    public  Integer waybillType;
    //0、未发送 1、发送中，2、发送成功，3、发送失败
    public  Integer smsStatus;
    //快递公司编码
    public String expressCode;
    //驿站编码
    public String poststationCode;
    //是否全选
    public boolean isSelectAllFlag = false;
    //是否显示失败个数
    public boolean  errorNoteFlag = false;

    //失败列表数据
    public String errorNumNote;
    //车签号
    public String carSignNum =  SPUtils.getStringValue(Constants.CAR_SIGN + SPUtils.getStringValue(Constants.JOB_NUMBER));


/*    //搜索按钮是否可点击
    public boolean searchClickFlag = false;
    //搜索内容
    public String searchContent;*/


    //当前显示的tab名称
    private String mCurrentTabName;
   /* @Bindable
    public String getSearchContent() {
        return searchContent;
    }

    public void setSearchContent(String searchContent) {
        if(!searchContent.equals(this.searchContent)){
            this.searchContent = searchContent;
            if (TextUtils.isEmpty(searchContent)){
                setSearchClickFlag(false);
            }
            else if(!isSearchClickFlag() ){
                setSearchClickFlag(true);
            }
            notifyPropertyChanged(com.yto.common.BR.searchContent);
        }
    }
    @Bindable
    public boolean isSearchClickFlag() {
        return searchClickFlag;
    }

    public void setSearchClickFlag(boolean searchClickFlag) {
        if(this.searchClickFlag != searchClickFlag){
            this.searchClickFlag = searchClickFlag;
            notifyPropertyChanged(com.yto.common.BR.searchClickFlag);
        }

    }*/
    @Bindable
    public String getCarSignNum() {
        return carSignNum;
    }

    public void setCarSignNum(String carSignNum) {
        if (!carSignNum.equalsIgnoreCase(this.carSignNum)) {
            this.carSignNum = carSignNum;
            notifyPropertyChanged(com.xbj.common.BR.carSignNum);
            SPUtils.saveStringValue(Constants.CAR_SIGN + SPUtils.getStringValue(Constants.JOB_NUMBER), carSignNum);
        }
    }
    @Bindable
    public String getErrorNumNote() {
        return errorNumNote;
    }

    public void setErrorNumNote(String errorNumNote) {
        if (!errorNumNote.equalsIgnoreCase(this.errorNumNote)) {
            this.errorNumNote = errorNumNote;
            notifyPropertyChanged(com.xbj.common.BR.errorNumNote);
        }
    }
    @Bindable
    public boolean isErrorNoteFlag() {
        return errorNoteFlag;
    }

    public void setErrorNoteFlag(boolean errorNoteFlag) {
        if (errorNoteFlag !=  this.errorNoteFlag) {
            this.errorNoteFlag = errorNoteFlag;
            notifyPropertyChanged(com.xbj.common.BR.errorNoteFlag);
        }
    }

    @Bindable
    public boolean isSelectAllFlag() {
        return isSelectAllFlag;
    }
    public void setSelectAllFlag(boolean selectAllFlag) {
        if(this.isSelectAllFlag != selectAllFlag){
            this.isSelectAllFlag = selectAllFlag;
            notifyPropertyChanged(com.xbj.common.BR.selectAllFlag);
        }
    }
    @Bindable
    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        if(!startTime.equals(this.startTime)){
            this.startTime = startTime;
            notifyPropertyChanged(com.xbj.common.BR.startTime);
        }

    }
    @Bindable
    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        if(!endTime.equals(this.endTime)){
            this.endTime = endTime;
            notifyPropertyChanged(com.xbj.common.BR.endTime);
        }
    }

    @Bindable
    public String getmCurrentTabName() {
        return mCurrentTabName;
    }

    public void setmCurrentTabName(String mCurrentTabName) {
        if(!mCurrentTabName.equals(this.mCurrentTabName)){
            this.mCurrentTabName = mCurrentTabName;
            notifyPropertyChanged(com.xbj.common.BR.mCurrentTabName);
        }

    }

}
