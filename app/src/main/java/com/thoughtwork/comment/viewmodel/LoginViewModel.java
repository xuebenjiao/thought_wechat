package com.thoughtwork.comment.viewmodel;

import android.os.Handler;
import android.text.TextUtils;
import android.view.Gravity;

import com.thoughtwork.base.BaseApplication;
import com.thoughtwork.base.activity.IBaseView;
import com.thoughtwork.base.model.BaseModel;
import com.thoughtwork.base.model.UpdateBean;
import com.thoughtwork.base.utils.AppDeviceUtils;
import com.thoughtwork.base.utils.ToastUtil;
import com.thoughtwork.base.utils.Utils;
import com.thoughtwork.base.viewmodel.MvvmBaseViewModel;
import com.thoughtwork.comment.model.ForgotPwdResetModel;
import com.thoughtwork.comment.model.GetCaptchaCodeModel;
import com.thoughtwork.comment.model.RegisterLoginModel;
import com.thoughtwork.base.utils.LiveDataBus;
import com.xbj.comment.R;
import com.thoughtwork.comment.application.DeliveryApplication;
import com.thoughtwork.comment.model.AppUpdateModel;
import com.thoughtwork.comment.model.LoginModel;
import com.thoughtwork.comment.model.ExpressSmsCodeModel;
import com.thoughtwork.comment.model.User;
import com.thoughtwork.base.constants.Constants;
import com.thoughtwork.comment.model.ValidateSmsCodeModel;
import com.xbj.network.common.api.bean.BaseResponse;
import com.xbj.network.common.api.bean.CaptchaCodeBean;
import com.xbj.network.common.api.bean.LoginBean;
import com.thoughtwork.base.utils.SPUtils;

/**
 * Time :2019/11/4
 * Author:xbj
 * Description :
 */
public class LoginViewModel extends MvvmBaseViewModel<IBaseView, LoginModel> implements BaseModel.IModelListener<BaseResponse,String> {
    //忘记密码重置
    private ForgotPwdResetModel forgotPwdResetModel;
    //注册
    private RegisterLoginModel registerLoginModel;
    //获取验证码
    private GetCaptchaCodeModel getCaptchaCodeModel;
    //发送短信验证码
    private ExpressSmsCodeModel smsCodeModel;
    //校验短信验证码
    private ValidateSmsCodeModel validateSmsCodeModel;
    //版本检测
    private AppUpdateModel appUpdateModel;
    public User mUser;
    public BaseResponse<LoginBean>  mLoginSuccessBean;
    public LoginViewModel(){
        model = new LoginModel();
        model.register(this);
    }
    @Override
    public void onLoadFinish(BaseModel model, BaseResponse data) {
        if(getPageView() != null){
            if(data == null) return;
            if (data.code == Constants.SUCCESS_CODE) {
                //登录
                if(model instanceof  LoginModel){
                    BaseResponse<LoginBean>  loginBean   = data;
                    if(loginBean.data != null ) {
                        SPUtils.saveStringValue(Constants.USER_TOKEN, loginBean.data.token);
                        SPUtils.saveStringValue(Constants.USER_NAME, loginBean.data.name);
                        SPUtils.saveStringValue(Constants.LOGIN_PHONE, loginBean.data.phone);
                        SPUtils.saveStringValue(Constants.HEAD_URL, loginBean.data.headUrl);
                        SPUtils.saveIntValue(Constants.ROLE_TYPE, loginBean.data.type);
                        SPUtils.saveStringValue(Constants.USER_ID, loginBean.data.id);
                        goToHomePage();
                    }
                }
                //校验短信验证码
                else if(model instanceof GetCaptchaCodeModel) {
                    BaseResponse<CaptchaCodeBean> captchBean = data;
                    if(captchBean.data != null){
                        LiveDataBus.getInstance().with("CaptchaCode", CaptchaCodeBean.class).postValue(captchBean.data);
                    }
                }  //校验短信验证码
                else if(model instanceof RegisterLoginModel) {
                    LiveDataBus.getInstance().with("registerLogin", String.class).postValue("成功");
                }
                //校验短信验证码
                else if(model instanceof ForgotPwdResetModel) {
                    LiveDataBus.getInstance().with("forgotPwdReset", String.class).postValue("成功");
                }
                //获取短信验证码
                else if(model instanceof ExpressSmsCodeModel) {
                    BaseResponse<ExpressLoginBean> smsCodeBean = data;
                    if (smsCodeBean.data != null) {
                        if (smsCodeBean.data.code == 0) {
                            //短信发送成功
                            ToastUtil.show(BaseApplication.getmContext(), "短信发送" + smsCodeBean.message);
                            LiveDataBus.getInstance().with("showExpressValidateDialog", boolean.class).postValue(true);
                        } else if (smsCodeBean.data.code == -700000) {
                            ToastUtil.show(BaseApplication.getmContext(), smsCodeBean.data.message);
                            LiveDataBus.getInstance().with("showExpressValidateDialog", boolean.class).postValue(false);
                        } else {
                            ToastUtil.show(BaseApplication.getmContext(), smsCodeBean.message);
                        }
                    }
                }
                else if(model instanceof AppUpdateModel){
                    BaseResponse<UpdateBean> updateBean = data;
                    int currentCode = AppDeviceUtils.getVersionCode(DeliveryApplication.getmContext());
                    if (updateBean.data.versionNum != 0) {
                        SPUtils.saveStringValue(Constants.APP_DOWNLOAD_URL, updateBean.data.upgradeUrl);
                        //判断服务端版本是否比当前版本高
                        if (currentCode < updateBean.data.versionNum) {
                            LiveDataBus.getInstance().with("AppUpdate",UpdateBean.class).postValue(updateBean.data);
                        }
                    }
                }
            }
            else if(model instanceof ExpressSmsCodeModel){
                ToastUtil.showGravity(BaseApplication.getmContext(), data.message, Gravity.CENTER);
                LiveDataBus.getInstance().with("showZtoValidateDialog", boolean.class).postValue(false);
            }
            //首次登陆的标识码
            else if(data.code == Constants.DEVICE_FIRST_LOING){
                LiveDataBus.getInstance().with("gotoSmsLoginActivity",Object.class).postValue("");
            }
            else {
                ToastUtil.showGravity(BaseApplication.getmContext(), data.message, Gravity.CENTER);
            }

        }
    }
    /**
     * 中通验证完后停留两秒 进入主界面
     */
    public  void setStayCurrentPageTime(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                goToHomePage();
            }
        },2000);
    }
    /**
     * 进入主界面
     */
    public void goToHomePage(){
        Utils.playFromRawFile(R.raw.login_success);
//        saveParameters();
        LiveDataBus.getInstance().with("gotoHomeActivity",String.class).postValue("");
    }

    /**
     * 登录成功后保存参数
     */
    public void saveParameters() {
//        setLocalParameters();
        if(mLoginSuccessBean != null && mLoginSuccessBean.data  != null) {
            SPUtils.saveStringValue(Constants.USER_TOKEN, mLoginSuccessBean.data.token);
        /*    //网点编码
            SPUtils.saveStringValue(Constants.ORG_NAME, mLoginSuccessBean.data.orgName);
            //便于本地数据库区分用户
            SPUtils.saveStringValue(Constants.USER_NAME, mLoginSuccessBean.data.userName);
            SPUtils.saveStringValue(Constants.USER_TOKEN, mLoginSuccessBean.data.userToken);
            //获取快递公司列表
            SPUtils.saveStringValue(Constants.JOB_NUMBER, mLoginSuccessBean.data.jobNumber);
            SPUtils.saveStringValue(Constants.ROLE_TYPE, mLoginSuccessBean.data.roleType);
            SPUtils.saveStringValue(Constants.ROLE_NAME, mLoginSuccessBean.data.roleName);
            //网点
            SPUtils.saveStringValue(Constants.ORG_LEVEL, mLoginSuccessBean.data.orgLevel);
            //是否有指派权限
            SPUtils.saveIntValue(Constants.IS_ASSIGN, mLoginSuccessBean.data.isAssign);
            //是否显示快递柜
            SPUtils.saveBooleanValue(Constants.EXPRESS_CABINET, mLoginSuccessBean.data.isShowDiyi);*/
        }
    }

    @Override
    public void onLoadFail(BaseModel model, String prompt) {
        if(model instanceof ExpressSmsCodeModel){
            LiveDataBus.getInstance().with("showExpressValidateDialog", boolean.class).postValue(false);
        }
        if(model instanceof LoginModel){
            mLoginSuccessBean = null;
        }
        ToastUtil.show(BaseApplication.getmContext(), prompt);

    }
    /**
     * 登录请求
     * @param json
     */
    public void requestLogin(String json){
        model.requestLogin(json);
    }
    /**
     * 初始化登录界面对应的实体对象
     * @return
     */
    public  User initUser(){
        if(SPUtils.getBooleanValue(Constants.REMEBER_PWD)) {
            mUser = new User(SPUtils.getStringValue(Constants.ORG_CODE), SPUtils.getStringValue(Constants.LOGIN_PHONE), SPUtils.getStringValue(Constants.LOGIN_PWD));
            mUser.selectRemPwd = true;
        }
        else if(!TextUtils.isEmpty(SPUtils.getStringValue(Constants.ORG_CODE))&& !TextUtils.isEmpty(SPUtils.getStringValue(Constants.LOGIN_PHONE))){
            mUser = new User(SPUtils.getStringValue(Constants.ORG_CODE), SPUtils.getStringValue(Constants.LOGIN_PHONE));
        }
        else{
            mUser = new User();
        }
        return mUser;
    }


    /**
     * 获取短信验证码
     */
    public void getExpressSmsCode(String json){
        if(TextUtils.isEmpty(getToken())) return;
        if(smsCodeModel == null){
            smsCodeModel = new ExpressSmsCodeModel();
            smsCodeModel.register(this);
        }
        smsCodeModel.setSmsToken(getToken());
        smsCodeModel.setJson(json);
        smsCodeModel.load();
    }
    /**
     * 版本检测
     */
    public void checkAppUpdate(){
        if(appUpdateModel  == null){
            appUpdateModel = new AppUpdateModel();
            appUpdateModel.register(this);
        }
        appUpdateModel.load();
    }

    /**
     * 验证短信验证码
     * @return
     */
    public void validateSmsCode(String json){
        if(validateSmsCodeModel == null){
            validateSmsCodeModel = new ValidateSmsCodeModel();
            validateSmsCodeModel.register(this);
        }
        validateSmsCodeModel.setSmsToken(getToken());
        validateSmsCodeModel.setJson(json);
        smsCodeModel.load();
    }

    public String getToken(){
      /*  if(mLoginSuccessBean != null && mLoginSuccessBean.data != null && mLoginSuccessBean.data.userToken != null){
            return  mLoginSuccessBean.data.userToken;
        }
        else{*/
        return "";
//        }
    }

    /**
     * 获取验证码
     */
    public void getCaptchaCode(String phone){
        if(!TextUtils.isEmpty(phone)) {
            if (getCaptchaCodeModel == null) {
                getCaptchaCodeModel = new GetCaptchaCodeModel();
                getCaptchaCodeModel.register(this);
            }
            getCaptchaCodeModel.setPhone(phone);
            getCaptchaCodeModel.load();
        }
        else{
            ToastUtil.show("请输入电话号码");
        }
    }

    /**
     * 注册
     */
    public  void loginRegister(String json){
        if(registerLoginModel == null){
            registerLoginModel = new RegisterLoginModel();
            registerLoginModel.register(this);
        }
        registerLoginModel.setJson(json);
        registerLoginModel.load();
    }

    /**
     * 忘记密码--修改密码
     */
    public  void forgotPwdReset(String json){
        if(forgotPwdResetModel == null){
            forgotPwdResetModel = new ForgotPwdResetModel();
            forgotPwdResetModel.register(this);
        }
        forgotPwdResetModel.forgetPassWord(json);
    }

}
