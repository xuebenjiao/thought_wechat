package com.thoughtwork.comment.acitivity;

import android.text.TextUtils;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.thoughtwork.comment.pageentity.ForgotPwdEntity;
import com.thoughtwork.comment.presenter.RegisterPageClickHandler;
import com.thoughtwork.comment.viewmodel.LoginViewModel;
import com.thoughtwork.base.activity.MvvmActivity;
import com.thoughtwork.base.constants.Constants;
import com.thoughtwork.base.utils.LiveDataBus;
import com.xbj.comment.R;
import com.xbj.comment.databinding.ActivityForgotPwdBinding;
import com.xbj.common.CommonHandler;
import com.xbj.network.common.api.bean.CaptchaCodeBean;

/**
 * Time :2021/2/22
 * Author:xbj
 * Description :忘记密码界面
 */
public class ForgotPwdActivity extends MvvmActivity<ActivityForgotPwdBinding, LoginViewModel> {
    private String mLoginPhone;
    private LoginViewModel loginViewModel;
    private ForgotPwdEntity pageEntity;
    @Override
    public int getLayoutId() {
        return R.layout.activity_forgot_pwd;
    }

    @Override
    protected LoginViewModel getViewModel()
    {
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        return loginViewModel;
    }



    @Override
    protected void onRetryBtnClick() {

    }

    @Override
    protected void initParameters() {
        pageEntity = new ForgotPwdEntity(4);
        if(!TextUtils.isEmpty(mLoginPhone)){
            pageEntity.setRegisterPhone(mLoginPhone);
        }
        RegisterPageClickHandler registerPageClickHandler =   new RegisterPageClickHandler(pageEntity,loginViewModel);
        viewDatabinding.setClickHandler(registerPageClickHandler);
        viewDatabinding.setEntity(new CommonTitleModel("","立即注册",true,true));
        viewDatabinding.setCommonHandler(new CommonHandler(registerPageClickHandler));
        viewDatabinding.setPageEntity(pageEntity);
        setLiveDataObserve();
    }
    /**
     * 设置livedata监听
     */
    private  void setLiveDataObserve(){
        LiveDataBus.getInstance().with("forgotPwdReset", String.class).observe(this, new Observer<String>() {
            @Override
            public void onChanged(String string) {
//                ToastUtil.show("密码重置成功");
                ForgotPwdActivity.this.finish();
            }
        });
        LiveDataBus.getInstance().with("CaptchaCode", CaptchaCodeBean.class).observe(this, new Observer<CaptchaCodeBean>() {
            @Override
            public void onChanged(CaptchaCodeBean captBean) {
                if(captBean != null){
                    pageEntity.captchaToken = captBean.captchaToken;
                }
            }
        });
    }

    @Override
    public void getIntentData() {
        if(getIntent() != null){
            mLoginPhone = getIntent().getStringExtra(Constants.LOGIN_PHONE);
        }
    }
}
