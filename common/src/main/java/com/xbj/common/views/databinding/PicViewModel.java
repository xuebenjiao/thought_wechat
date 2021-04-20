package com.xbj.common.views.databinding;

import androidx.annotation.DrawableRes;
import androidx.databinding.Bindable;

import com.thoughtwork.base.customview.BaseCustomViewModel;

/**
 * Time :2020/12/25
 * Author:xbj
 * Description :
 */
public class PicViewModel extends BaseCustomViewModel {
    //记录用户选择的下标，便于替换图片
    public int selectIndex;
    private String pictureUrl;
    private String pictureUrl2;
    private String pictureUrl3;

    public int drawable;
    @Bindable
    public String getPictureUrl() {
        return pictureUrl;
    }
    public void setPictureUrl(String pictureUrl) {
        if(!pictureUrl.equals(this.pictureUrl)){
            this.pictureUrl = pictureUrl;
            notifyPropertyChanged(com.xbj.common.BR.pictureUrl);
        }
    }
    @Bindable
    public String getPictureUrl2() {
        return pictureUrl2;
    }
    public void setPictureUrl2(String pictureUrl2) {
        if(!pictureUrl2.equals(this.pictureUrl2)){
            this.pictureUrl2 = pictureUrl2;
            notifyPropertyChanged(com.xbj.common.BR.pictureUrl2);
        }
    }
    @Bindable
    public String getPictureUrl3() {
        return pictureUrl3;
    }

    public void setPictureUrl3(String pictureUrl3) {

        if(!pictureUrl3.equals(this.pictureUrl3)){
            this.pictureUrl3 = pictureUrl3;
            notifyPropertyChanged(com.xbj.common.BR.pictureUrl3);
        }
    }

    @Override
    @DrawableRes
    public int getDrawable() {
        return drawable;
    }

    @Override
    public void setDrawable(@DrawableRes int drawable) {
        this.drawable = drawable;
    }
}
