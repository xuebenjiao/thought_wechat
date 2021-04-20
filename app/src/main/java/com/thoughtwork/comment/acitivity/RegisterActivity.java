package com.thoughtwork.comment.acitivity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.thoughtwork.comment.pageentity.RegisterPageEntity;
import com.thoughtwork.base.activity.MvvmActivity;
import com.thoughtwork.base.utils.LiveDataBus;
import com.xbj.comment.R;
import com.xbj.comment.databinding.ActivityRegisterBinding;
import com.thoughtwork.comment.presenter.RegisterPageClickHandler;
import com.thoughtwork.comment.viewmodel.LoginViewModel;
import com.xbj.common.CommonHandler;
import com.xbj.network.common.api.bean.CaptchaCodeBean;

/**
 * Time :2021/2/22
 * Author:xbj
 * Description : 注册界面
 */
public class RegisterActivity extends MvvmActivity<ActivityRegisterBinding, LoginViewModel> {
    private LoginViewModel loginViewModel;
    private RegisterPageEntity pageEntity;
    @Override
    public int getLayoutId() {
        return R.layout.activity_register;
    }
    @Override
    protected LoginViewModel getViewModel() {
        return loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
    }



    @Override
    protected void onRetryBtnClick() {

    }

    @Override
    protected void initParameters() {
        pageEntity= new RegisterPageEntity();
        RegisterPageClickHandler registerPageClickHandler =   new RegisterPageClickHandler(pageEntity,loginViewModel);
        viewDatabinding.setClickHandler(registerPageClickHandler);
        viewDatabinding.setEntity(new CommonTitleModel("","立即登录",true,true));
        viewDatabinding.setCommonHandler(new CommonHandler(registerPageClickHandler));
        viewDatabinding.setPageEntity(pageEntity);
        setLiveDataObserve();
    }
    private void setLiveDataObserve(){
        LiveDataBus.getInstance().with("CaptchaCode", CaptchaCodeBean.class).observe(this, new Observer<CaptchaCodeBean>() {
            @Override
            public void onChanged(CaptchaCodeBean captBean) {
                if(captBean != null){
                    pageEntity.captchaToken = captBean.captchaToken;
                }
            }
        });
        LiveDataBus.getInstance().with("registerLogin", String.class).observe(this, new Observer<String>() {
            @Override
            public void onChanged(String string) {
//                ToastUtil.show("注册成功！");
                RegisterActivity.this.finish();
            }
        });
    }
}
