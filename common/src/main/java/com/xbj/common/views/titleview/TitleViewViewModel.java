package com.xbj.common.views.titleview;


import com.thoughtwork.base.customview.BaseCustomViewModel;


/**
 * date :2019/10/29.
 * author:xbj
 * description:
 */
public class TitleViewViewModel extends BaseCustomViewModel {
    public  boolean isShowTitle = true;
    public String title;
    public String content;
    public String code;
    public TitleViewViewModel(){

    }

    public TitleViewViewModel(String title, String content) {
        this.title = "运单号："+title;
        this.content = content;
    }
    public TitleViewViewModel( String content) {
        this.content = content;
    }
    public TitleViewViewModel( String title,String content,String code) {
        this.title = title;
        this.content = content;
        this.code = code;
    }

    public TitleViewViewModel(boolean isShowSubTitle, String title, String content, String code) {
        this.isShowTitle = isShowSubTitle;
        this.title = title;
        this.content = content;
        this.code = code;
    }
}