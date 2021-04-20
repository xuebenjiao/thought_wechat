package com.thoughtwork.comment.databing;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.thoughtwork.base.customview.BaseCustomView;
import com.thoughtwork.base.utils.ScreenUtil;
import com.xbj.comment.R;
import com.xbj.comment.databinding.SmsloginNoteFailureBinding;
import com.zltd.utils.DeviceUtils;

import org.simple.eventbus.EventBus;

/**
 * Time :2020/3/4
 * Author:xbj
 * Description :
 */
public class SmsLoginFailureView extends BaseCustomView<SmsloginNoteFailureBinding, LoginInforPageModel> {
    public SmsLoginFailureView(Context context) {
        super(context);
    }

    public SmsLoginFailureView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SmsLoginFailureView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public SmsLoginFailureView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected int setViewLayoutId() {
        return R.layout.smslogin_note_failure;
    }

    @Override
    protected void setDataToView(LoginInforPageModel data) {
        getDataBinding().setViewModel(data);
        getDataBinding().setPageEntity(this);
        //当屏幕宽度小于UI设计基准值时除去padding,以免工业机等窄屏界面显示不全
        boolean isNarrowScreen = ScreenUtil.getDisplayWidth() < ScreenUtil.dip2px( 375);
        boolean isMobile =  DeviceUtils.isMobilePhone ;//||  !Build.MODEL.toUpperCase().startsWith("KT55");
        if( !isMobile && isNarrowScreen ){
           getDataBinding().loginInforLayout.setPadding(0,0,0,0);
       }
        if(data.isDoubleValiteFlag()){
            getDataBinding().validateBtn.setText("去验证");
        }
        else{
            getDataBinding().validateBtn.setText("修改配置");
        }
    }

    @Override
    protected void onRootClick(View view) {

    }
    //验证
    public void btnClick(View view){
        EventBus.getDefault().post(getViewModel(), "sms_double_validate");
    }
}
