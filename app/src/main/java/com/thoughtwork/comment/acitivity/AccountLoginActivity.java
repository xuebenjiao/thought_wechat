package com.thoughtwork.comment.acitivity;

import android.content.Intent;
import android.os.CountDownTimer;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.View;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.thoughtwork.comment.pageentity.LoginPageEntity;
import com.thoughtwork.comment.presenter.RegisterPageClickHandler;
import com.thoughtwork.comment.viewmodel.LoginViewModel;
import com.thoughtwork.base.RichTextClickableSpan;
import com.thoughtwork.base.activity.MvvmActivity;
import com.thoughtwork.base.constants.Constants;
import com.thoughtwork.base.utils.LiveDataBus;
import com.xbj.comment.R;
import com.xbj.comment.databinding.ActivityAccountLoginBinding;
import com.xbj.common.CommonHandler;
import com.xbj.network.common.api.bean.CaptchaCodeBean;

/**
 * Time :2021/2/22
 * Author:xbj
 * Description :账号/验证码登录
 */
public class AccountLoginActivity extends MvvmActivity<ActivityAccountLoginBinding, LoginViewModel> {
    private String mLoginPhone;
    private LoginViewModel loginViewModel;
    //短信验证码计时
    private  CountDownTimer smsTimer;
    private boolean isSmsCountDown = false;
    private LoginPageEntity pageEntity;

    @Override
    public int getLayoutId() {
        return R.layout.activity_account_login;
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
        pageEntity = new LoginPageEntity(2);
        pageEntity.setRegisterPhone(TextUtils.isEmpty(mLoginPhone)?"":mLoginPhone);
        RegisterPageClickHandler registerPageClickHandler = new RegisterPageClickHandler(pageEntity,loginViewModel);
        viewDatabinding.setClickHandler(registerPageClickHandler);
        viewDatabinding.setEntity(new CommonTitleModel("", "立即注册", true, true));
        viewDatabinding.setCommonHandler(new CommonHandler(registerPageClickHandler));
        viewDatabinding.setPageEntity(pageEntity);
        setNoticeContent();
        setLiveDataObserve();
    }
    private void setLiveDataObserve(){
        LiveDataBus.getInstance().with("gotoHomeActivity",String.class).observe(this, new Observer<String>() {
            @Override
            public void onChanged(String str) {
                startActivity(new Intent(AccountLoginActivity.this,HomeActvity.class));
                AccountLoginActivity.this.finish();
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

    /**
     * 设置注意事项内容显示
     */
    private void setNoticeContent() {
        //第一行
        String contentStr = getResources().getString(R.string.account_login_use_agreement);
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        SpannableString spannableString = new SpannableString(contentStr);
        RichTextClickableSpan span = new RichTextClickableSpan(this, getResources().getColor(R.color.theme_color), false, new RichTextClickableSpan.RichTextClickCallBack() {
            @Override
            public void onClick(View view) {
//                ToastUtil.show(view.getContext().getResources().getString(R.string.developing_note));
            }
        });
        spannableString.setSpan(span, contentStr.indexOf("《快递纵横用户协议》"), contentStr.indexOf("《快递纵横用户协议》") + 10, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableStringBuilder.append(spannableString);
        viewDatabinding.bottomNoteTv.setMovementMethod(LinkMovementMethod.getInstance());
        //去除点击后的底色
        viewDatabinding.bottomNoteTv.setHighlightColor(getResources().getColor(android.R.color.transparent));
        viewDatabinding.bottomNoteTv.setText(spannableStringBuilder);

    }

    @Override
    public void getIntentData() {
        if(getIntent() != null){
            mLoginPhone = getIntent().getStringExtra(Constants.LOGIN_PHONE);
        }
    }
}
