package com.thoughtwork.comment.presenter;

import android.app.Activity;
import android.content.Intent;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.thoughtwork.comment.acitivity.RegisterActivity;
import com.thoughtwork.comment.pageentity.LoginPageEntity;
import com.thoughtwork.comment.pageentity.RegisterPageEntity;
import com.thoughtwork.base.BaseApplication;
import com.thoughtwork.base.constants.Constants;
import com.thoughtwork.base.utils.RegexUtils;
import com.thoughtwork.base.utils.RxKeyboardTool;
import com.thoughtwork.base.utils.ToastUtil;
import com.thoughtwork.base.utils.rxtool.RxEncryptTool;
import com.thoughtwork.comment.acitivity.AccountLoginActivity;
import com.thoughtwork.comment.acitivity.ForgotPwdActivity;
import com.thoughtwork.comment.acitivity.NetworkTestActivity;
import com.thoughtwork.comment.entity.RequestLoginEntity;
import com.thoughtwork.comment.viewmodel.LoginViewModel;
import com.xbj.common.BaseClickHandler;
import com.xbj.common.CommonHandler;

/**
 * Time :2021/2/22
 * Author:xbj
 * Description :注册界面点击事件处理
 */
public class RegisterPageClickHandler extends BaseClickHandler implements CommonHandler.RightBtnCallBack{
    private LoginViewModel mViewModel;
    //短信验证码计时
    private  CountDownTimer smsTimer;
    private boolean isSmsCountDown = false;
    private RegisterPageEntity pageEntity;

    public RegisterPageClickHandler(RegisterPageEntity pageEntity) {
        this.pageEntity = pageEntity;
    }

    public RegisterPageClickHandler(RegisterPageEntity pageEntity, LoginViewModel viewModel) {
        this.pageEntity = pageEntity;
        this.mViewModel = viewModel;
    }

    /**
     * 注册
     */
    @Override
    public void handlerBtnClick(View view){
        if (!TextUtils.isEmpty(pageEntity.getRegisterPhone()) && !RegexUtils.checkMobile(pageEntity.getRegisterPhone())) {
            ToastUtil.show(BaseApplication.getmContext(), "请输入正确的手机号");
            return;
        }

        if (pageEntity.getRegisterPwd().length() < 6 || pageEntity.getRegisterPwd().length() > 20) {
            ToastUtil.show(BaseApplication.getmContext(), "密码不符合要求，需6~20位数字加字母！");
            return;
        }
        if (!RegexUtils.checkIsOnlyContainsNumAndChar(pageEntity.getRegisterPwd())) {
            ToastUtil.show(BaseApplication.getmContext(), "密码必须是6~20位数字加字母！");
            return;
        }
        if (!pageEntity.getRegisterPwd().equals(pageEntity.getRegisterConfirmPwd())) {
            ToastUtil.show("两次密码输入不相同");
            return;
        }

        if (mViewModel != null) {
            RequestLoginEntity entity = new RequestLoginEntity(pageEntity.getInvitationCode(), pageEntity.getRegisterPhone(), pageEntity.getValidateCode(), pageEntity.captchaToken, RxEncryptTool.encryptMD5ToString(pageEntity.getRegisterPwd()));
            mViewModel.loginRegister(new Gson().toJson(entity));
        }
    }

    /**
     * 忘记密码界面按钮处理
     */
    public void forgotPwdReset(View view){
        if(!TextUtils.isEmpty(pageEntity.getRegisterPhone()) && !RegexUtils.checkMobile(pageEntity.getRegisterPhone())) {
            ToastUtil.show(BaseApplication.getmContext(), "请输入正确的手机号");
            return;
        }

        if (pageEntity.getRegisterPwd().length() < 6 || pageEntity.getRegisterPwd().length() > 20) {
            ToastUtil.show(BaseApplication.getmContext(), "密码不符合要求，需6~20位数字加字母！");
            return;
        }
        if (!RegexUtils.checkIsOnlyContainsNumAndChar(pageEntity.getRegisterPwd())) {
            ToastUtil.show(BaseApplication.getmContext(), "密码必须是6~20位数字加字母！");
            return;
        }
        if(!pageEntity.getRegisterPwd().equals(pageEntity.getRegisterConfirmPwd())){
            ToastUtil.show("两次密码输入不相同");
            return;
        }
        if(mViewModel != null && isValidClick(view)){
            RequestLoginEntity entity = new RequestLoginEntity(pageEntity.getRegisterPhone(),pageEntity.getValidateCode(),pageEntity.captchaToken,RxEncryptTool.encryptMD5ToString(pageEntity.getRegisterPwd()));
            mViewModel.forgotPwdReset(new Gson().toJson(entity));
        }
    }
    /**
     * 账号/验证码登录
     */
    public void loginForAccountOrCode(View view){
        if(mViewModel != null && isValidClick(view)){
            RequestLoginEntity entity ;
            if(((LoginPageEntity)pageEntity).isShowValidate()){
                entity  = new RequestLoginEntity( pageEntity.getRegisterPhone(),pageEntity.getValidateCode(),pageEntity.captchaToken);
                entity.loginType = 1;
            }
            else{
                entity  = new RequestLoginEntity(RxEncryptTool.encryptMD5ToString(pageEntity.getRegisterPwd()), pageEntity.getRegisterPhone());
                entity.loginType = 0;
            }
            RxKeyboardTool.hideSoftInput((Activity) view.getContext());

            mViewModel.requestLogin(new Gson().toJson(entity));
        }
    }




    /**
     * 获取验证码登录
     */
    public void getValidateCode(View view){
        if(!isSmsCountDown) {
            if (TextUtils.isEmpty(pageEntity.getRegisterPhone())) {
                ToastUtil.show("请输入电话号码");
                return;
            }
            if (!TextUtils.isEmpty(pageEntity.getRegisterPhone()) && !RegexUtils.checkMobile(pageEntity.getRegisterPhone())) {
                ToastUtil.show(BaseApplication.getmContext(), "请输入正确的手机号");
                return;
            }
            if (mViewModel != null) {
                mViewModel.getCaptchaCode(pageEntity.getRegisterPhone());
            }
            startCountDown();
        }
    }


    @Override
    public void rightBtnCallBack(View view) {
        if(view != null){
            if(view instanceof  TextView){
                String rightBtnName =  ((TextView)view).getText().toString();
                if(rightBtnName.contains("注册")){
                    view.getContext().startActivity(new Intent(view.getContext(), RegisterActivity.class));
                }
                else{
                    view.getContext().startActivity(new Intent(view.getContext(), AccountLoginActivity.class));
                }
            }
        }


    }

    /**
     * 按钮切换处理
     */
    public  void handlerSwitchBtn(View view){
        if(view != null){
            if(view instanceof Button){
                String rightBtnName =  ((Button)view).getText().toString();
                if(rightBtnName.contains("密码")){
                    ( (LoginPageEntity) pageEntity).setShowValidate(false);
                    ( (LoginPageEntity) pageEntity).setSwitchBtnName("切换验证码登录");
                    pageEntity.setRegisterPwd("");
                }
                else{
                    ( (LoginPageEntity) pageEntity).setSwitchBtnName("切换密码登录");
                    ( (LoginPageEntity) pageEntity).setShowValidate(true);
                    pageEntity.setValidateCode("");
                }
            }
        }

    }
    /**
     * 网络测试
     */
    public void goNetworkTest(View view){
        view.getContext().startActivity(new Intent(view.getContext(), NetworkTestActivity.class));
    }
    /**
     * 忘记密码
     */
    public void forgotPwd(View view){
        Intent intent = new Intent(view.getContext(), ForgotPwdActivity.class);
        intent.putExtra(Constants.LOGIN_PHONE,pageEntity.getRegisterPhone());
        view.getContext().startActivity(intent);
    }

    /**
     * 图形验证码计时
     */
    public void startCountDown() {
        /** 倒计时60秒，一次1秒 */
        //避免正在记时时再次点击导致计时显示混乱
        if (!isSmsCountDown) {
            if (smsTimer != null) {
                smsTimer.start();
            } else {
                smsTimer = new CountDownTimer(60 * 1000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        // TODO Auto-generated method stub
                        if (!isSmsCountDown) {
                            isSmsCountDown = true;
                        }
                        pageEntity.setValidateBtnName(millisUntilFinished / 1000 + " 秒");
                    }
                    @Override
                    public void onFinish() {
                        pageEntity.setValidateBtnName("重新获取");
                        isSmsCountDown = false;
                    }
                }.start();
            }
        }
    }


}
