package com.thoughtwork.comment.pageentity;

import android.text.TextUtils;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.thoughtwork.base.constants.Constants;
import com.thoughtwork.base.utils.SPUtils;
import com.xbj.comment.BR;

/**
 * Time :2021/2/18
 * Author:xbj
 * Description :首页、网点、驿站顶部参数实体
 */
public class HomePageEntity extends BaseObservable {
    private boolean showMapFlag ;
    private  boolean showMapListFlag ;
    //避免用户杀死App,重新进来显示的是全国
    public String locationCity = TextUtils.isEmpty(SPUtils.getStringValue(Constants.CITY_NAME))?
            (TextUtils.isEmpty(SPUtils.getStringValue(Constants.CURRENT_LOCATION_CITY))?"全国":
                    SPUtils.getStringValue(Constants.CURRENT_LOCATION_CITY)):SPUtils.getStringValue(Constants.CITY_NAME);
    public String searchContent;
    public String titleName = "快递纵横";
    //右侧btn是否可点击的标识 true 可点击  false 不可点击
    public boolean showTopTitle = true;
    @Bindable
    public boolean isShowTopTitle() {
        return showTopTitle;
    }
    public void setShowTopTitle(boolean showTopTitle) {

        if (showTopTitle != this.showTopTitle) {
            this.showTopTitle = showTopTitle;
            notifyPropertyChanged(BR.showTopTitle);
        }
    }
    @Bindable
    public String getLocationCity() {
        return locationCity;
    }

    public void setLocationCity(String locationCity) {
        if (!locationCity.equals(this.locationCity)) {
            this.locationCity = locationCity;
            notifyPropertyChanged(BR.locationCity);
        }
    }

    @Bindable
    public String getSearchContent() {
        return searchContent;
    }

    public void setSearchContent(String searchContent) {
        if (!searchContent.equals(this.searchContent)) {
            this.searchContent = searchContent;
            notifyPropertyChanged(BR.searchContent);
        }
    }
    @Bindable
    public boolean isShowMapListFlag() {
        return showMapListFlag;
    }

    public void setShowMapListFlag(boolean showMapListFlag) {
        if(showMapListFlag != this.showMapListFlag) {
            this.showMapListFlag = showMapListFlag;
            notifyPropertyChanged(BR.showMapListFlag);
        }
    }
    @Bindable
    public boolean isShowMapFlag() {
        return showMapFlag;
    }

    public void setShowMapFlag(boolean showMapFlag) {

        if(showMapFlag != this.showMapFlag) {
            this.showMapFlag = showMapFlag;
            notifyPropertyChanged(BR.showMapFlag);
        }
    }
}
