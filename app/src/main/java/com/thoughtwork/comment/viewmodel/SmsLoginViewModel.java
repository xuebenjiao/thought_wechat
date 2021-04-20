package com.thoughtwork.comment.viewmodel;

import android.text.TextUtils;

import com.thoughtwork.base.BaseApplication;
import com.thoughtwork.base.constants.Constants;
import com.thoughtwork.base.model.BaseModel;
import com.thoughtwork.base.utils.ToastUtil;
import com.thoughtwork.base.utils.LiveDataBus;
import com.thoughtwork.comment.model.GuidInforModel;
import com.thoughtwork.comment.model.LoginModel;
import com.thoughtwork.comment.model.PicCodeModel;
import com.thoughtwork.comment.model.SmsLoginPageModel;
import com.thoughtwork.comment.model.ValidatePicCodeModel;
import com.thoughtwork.comment.model.ValidateRegisterSmsCodeModel;
import com.xbj.network.common.api.bean.BaseResponse;
import com.xbj.network.common.api.bean.LoginBean;
import com.xbj.network.common.api.bean.PersonGuidBean;
import com.xbj.network.common.api.bean.PicCodeBean;

/**
 * Time :2021/1/26
 * Author:xbj
 * Description : 图形校验界面对应的ViewModel
 */
public class SmsLoginViewModel extends LoginViewModel {
    //界面绑定实体
    private SmsLoginPageModel mSmsUser;
    //获取图形验证码
    private BaseModel picCodeModel;
    //获取用户指引信息
    private BaseModel guidInforModel;
    //校验图形码
    private ValidatePicCodeModel validatePicCodeModel;
    //设备注册短信验证码
    private ValidateRegisterSmsCodeModel validateRegisterSmsCodeModel;


    @Override
    public void onLoadFinish(BaseModel model, BaseResponse data) {
        if(getPageView() != null) {
            if (data == null) return;
            if (data.code == Constants.SUCCESS_CODE) {
                //校验注册时短信验证码
                if(model instanceof ValidateRegisterSmsCodeModel) {
                    ToastUtil.show(BaseApplication.getmContext(), "短信验证" + data.message);
                    LiveDataBus.getInstance().with("requestLogin",String.class).postValue("");
                }
                //登录
                else  if(model instanceof LoginModel){
                    BaseResponse<LoginBean>  loginBean   = data;
                     mLoginSuccessBean = data;
                   /* if(loginBean.data != null ) {
                        mLoginSuccessBean = loginBean;
                        //如果是1 则强制跳转到密码修改界面修改面
                        if(loginBean.data.originalPwd.equals(Constants.FORCE_TO_UPDATE_PAGE)) {
                            mSmsUser.initialPwdFlag = true;
                        }
                        else{
                            mSmsUser.initialPwdFlag = false;
                        }
                        setloginNote(loginBean);
                    }*/
                }
                //图像验证码
                if(model instanceof PicCodeModel) {
                    BaseResponse<PicCodeBean> picCodeBean = data;
                    if (!TextUtils.isEmpty(picCodeBean.data.img)){
                        LiveDataBus.getInstance().with("pic_code",String.class).postValue(picCodeBean.data.img);
                        mSmsUser.captchaToken = picCodeBean.data.captchaToken;
                        //防止从上一步返回至此时，界面显示的还是之前输入的内容
                        mSmsUser.setPicCode("");
                        mSmsUser.setSmsCode("");
                        mSmsUser.setShowSmsLayout(false);
                        mSmsUser.setLoginBtnName("下一步");
                    }
                }
                //校验图形码
                else if(model instanceof  ValidatePicCodeModel){
                    BaseResponse<String> smsToken = data;
                    mSmsUser.smsToken = smsToken.data;
                    mSmsUser.setShowSmsLayout(true);
                    //防止确定按钮没有输入的时候可点击
                    mSmsUser.setPicCode("");
                    mSmsUser.setSmsCode("");
                    LiveDataBus.getInstance().with("ValidatePicCodeSuccess",String.class).postValue("");
                }
                //用户指引
                else if(model instanceof  GuidInforModel){
                    BaseResponse<PersonGuidBean> personGuidBean = data;
                    if(personGuidBean.data != null){
                        mSmsUser.privacyPolicy = personGuidBean.data.privacyPolicy;
                        mSmsUser.serviceAgreement = personGuidBean.data.serviceAgreement;
                    }
                }

                else {
                    super.onLoadFinish(model, data);
                }
            }
            else{
                //如果校验失败重新获取图形码
                if(model instanceof  ValidatePicCodeModel){
                    getImgPicCode();
                }
                //校验注册短信验证码失败
                else  if(model instanceof ValidateRegisterSmsCodeModel) {
                    mSmsUser.setLoginBtnName("上一步");
                }
                ToastUtil.show(BaseApplication.getmContext(),data.message);
            }
        }
    }
    @Override
    public void onLoadFail(BaseModel model, String prompt) {
        super.onLoadFail(model, prompt);
    }

    /**
     * 初始化界面对应的实体
     * @return
     */
    public SmsLoginPageModel initPageModel(){
        if(mSmsUser == null) {
            mSmsUser = new SmsLoginPageModel();
        }
        mUser = mSmsUser;
        return mSmsUser;
    }

    /**
     * 获取图形验证码
     */
    public void getImgPicCode(){
        if(picCodeModel == null){
            picCodeModel = new PicCodeModel();
            picCodeModel.register(this);
        }
        picCodeModel.load();
    }
    /**
     * 获取用户指引信息
     */
    public void getPersonGuidInfor(){
        if(guidInforModel == null){
            guidInforModel = new GuidInforModel();
            guidInforModel.register(this);
        }
        guidInforModel.load();
    }

    /**
     * 校验图形码
     */
    public void validatePicCode(String json){
        if(validatePicCodeModel == null){
            validatePicCodeModel = new ValidatePicCodeModel();
            validatePicCodeModel.register(this);
        }
        validatePicCodeModel.setJson(json);
        validatePicCodeModel.load();
    }
    /**
     * 校验设备注册短信验证码
     */
    public void validateRegisterSmsCode(String json){
        if(validateRegisterSmsCodeModel == null){
            validateRegisterSmsCodeModel = new ValidateRegisterSmsCodeModel();
            validateRegisterSmsCodeModel.register(this);
        }
        validateRegisterSmsCodeModel.setJson(json);
        validateRegisterSmsCodeModel.load();
    }

    @Override
    public void goToHomePage() {
        LiveDataBus.getInstance().with("showUserGuidInfor",String.class).postValue("");
    }
}
