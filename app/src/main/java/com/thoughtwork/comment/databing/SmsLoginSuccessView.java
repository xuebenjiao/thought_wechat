package com.thoughtwork.comment.databing;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.thoughtwork.base.customview.BaseCustomView;
import com.thoughtwork.base.utils.ScreenUtil;
import com.xbj.comment.R;
import com.xbj.comment.databinding.SmsloginNoteSuccessBinding;
import com.zltd.utils.DeviceUtils;

/**
 * Time :2020/3/4
 * Author:xbj
 * Description :
 */
public class SmsLoginSuccessView extends BaseCustomView<SmsloginNoteSuccessBinding, LoginInforPageModel> {
    public SmsLoginSuccessView(Context context) {
        super(context);
    }

    public SmsLoginSuccessView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SmsLoginSuccessView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public SmsLoginSuccessView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected int setViewLayoutId() {
        return R.layout.smslogin_note_success;
    }

    @Override
    protected void setDataToView(LoginInforPageModel data) {
        getDataBinding().setViewModel(data);
        //当屏幕宽度小于UI设计基准值时除去padding,以免工业机等窄屏界面显示不全
        boolean isNarrowScreen = ScreenUtil.getDisplayWidth() < ScreenUtil.dip2px( 375);
        boolean isMobile =  DeviceUtils.isMobilePhone ;//||  !Build.MODEL.toUpperCase().startsWith("KT55");
        if( !isMobile && isNarrowScreen ){
            getDataBinding().loginInforLayout.setPadding(0,0,0,0);
        }
    }

    @Override
    protected void onRootClick(View view) {
    }
}
