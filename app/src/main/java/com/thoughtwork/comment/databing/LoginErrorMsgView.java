package com.thoughtwork.comment.databing;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.thoughtwork.base.customview.BaseCustomView;
import com.thoughtwork.base.utils.ScreenUtil;
import com.xbj.comment.R;
import com.xbj.comment.databinding.LoginErrorMsgLayoutBinding;
import com.zltd.utils.DeviceUtils;

/**
 * Time :2020/8/4
 * Author:xbj
 * Description :
 */
public class LoginErrorMsgView extends BaseCustomView<LoginErrorMsgLayoutBinding, LoginInforPageModel> {
    public LoginErrorMsgView(Context context) {
        super(context);
    }

    public LoginErrorMsgView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LoginErrorMsgView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public LoginErrorMsgView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected int setViewLayoutId() {
        return R.layout.login_error_msg_layout;
    }

    @Override
    protected void setDataToView(LoginInforPageModel data) {
        getDataBinding().setViewModel(data);
        boolean isNarrowScreen = ScreenUtil.getDisplayWidth() < ScreenUtil.dip2px( 375);
        boolean isMobile =  DeviceUtils.isMobilePhone ;//||  !Build.MODEL.toUpperCase().startsWith("KT55");
        boolean flag = !isMobile && isNarrowScreen;
        if( flag){
            getDataBinding().errorMsgLayout.setPadding(0,0,0,0);
        }
        if(data.isFirstItem){
            getDataBinding().errorMsgLayout.setPadding(flag ? 0 :ScreenUtil.dip2px(1) * 30, ScreenUtil.dip2px(1) * 30,( flag ? 0 :ScreenUtil.dip2px(1) * 30),0 );
        }
        getDataBinding().setPageEntity(this);
    }

    @Override
    protected void onRootClick(View view) {

    }
}
