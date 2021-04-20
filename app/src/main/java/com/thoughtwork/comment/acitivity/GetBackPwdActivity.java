package com.thoughtwork.comment.acitivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import com.google.gson.Gson;
import com.thoughtwork.base.BaseApplication;
import com.thoughtwork.base.activity.MvvmActivity;
import com.thoughtwork.base.constants.Constants;
import com.thoughtwork.base.utils.RegexUtils;
import com.thoughtwork.base.utils.RxKeyboardTool;
import com.thoughtwork.base.utils.SPUtils;
import com.thoughtwork.base.utils.ToastUtil;
import com.thoughtwork.base.utils.rxtool.RxEncryptTool;
import com.thoughtwork.base.viewmodel.MvvmBaseViewModel;
import com.xbj.common.CommonHandler;
import com.xbj.comment.R;
import com.xbj.comment.databinding.ActivityGetBackPwdBinding;
import com.thoughtwork.comment.entity.FindBackPwdEntity;
import com.thoughtwork.comment.entity.RequestPicSmsEntity;
import com.thoughtwork.comment.model.GetBackPwdPageEntity;
import com.xbj.network.common.api.CommonApi;
import com.xbj.network.common.api.bean.BaseResponse;
import com.xbj.network.common.api.bean.PicCodeBean;
import com.xbj.network.errorhandler.ExceptionHandle;
import com.xbj.network.observer.BaseObserver;

/**
 * Time :2020/7/27
 * Author:xbj
 * Description :找回密码
 * */
public class GetBackPwdActivity extends MvvmActivity<ActivityGetBackPwdBinding, MvvmBaseViewModel> {
    private boolean isUpdatePwdSuccess = false;
    //短信验证码计时
    private  CountDownTimer smsTimer;
    private GetBackPwdPageEntity pageEntity;

    @Override
    public int getLayoutId() {
        return R.layout.activity_get_back_pwd;
    }

    @Override
    protected MvvmBaseViewModel getViewModel() {
        return null;
    }



    @Override
    protected void onRetryBtnClick() {

    }

    @Override
    protected void initParameters() {
        super.initParameters();
        setPageData();
    }
    private void setPageData(){
        viewDatabinding.setEntity(new CommonTitleModel(getResources().getString(R.string.login_get_back_pwd),"",false));
        viewDatabinding.setMyClickHandler(new CommonHandler());
        pageEntity = new GetBackPwdPageEntity();
        if(getIntent() != null){
            String phoneNum =  getIntent().getStringExtra(Constants.LOGIN_PHONE);
            String orgCode =  getIntent().getStringExtra(Constants.ORG_CODE);
            if(!TextUtils.isEmpty(phoneNum)){
                pageEntity.userPhone = phoneNum;
            }
            if(!TextUtils.isEmpty(orgCode)){
                pageEntity.setOrgCode(orgCode);
            }
            if(!TextUtils.isEmpty(phoneNum) && !TextUtils.isEmpty(orgCode)){
                pageEntity.setBtnClick(true);
            }
        }
        viewDatabinding.setPageEntity(pageEntity);
        viewDatabinding.setPreseter(this);
        initData();
    }

    /**
     * 获取数据源
     */
    private void initData(){
//        getPicCode();
    }
    /**
     * 下一步按钮处理逻辑
     */
    public void btnNextClick(View view){
        if (!RegexUtils.checkMobile(pageEntity.getUserPhone())) {
            ToastUtil.show(BaseApplication.getmContext(), "请输入正确的手机号");
            return;
        }
        //todo 验证短信验证码是否正确 如果正确则显示密码修改界面
        pageEntity.setShowPhoneLayout(false);
    }
    /**
     * 确认按钮处理逻辑
     */
    public void btnConfirmClick(View view){
        RxKeyboardTool.hideSoftInput(GetBackPwdActivity.this);
        //todo 返回至登录界面重新登录
        //验证电话号码与网点编码
        if(pageEntity != null){
            //验证电话号码与网点编码
            if(pageEntity.isShowPhoneLayout() && pageEntity.isShowOrgCodeLayout()){
                validatePhoneNumAndOrgcod();
            }
            //验证图形验证码
            else if(pageEntity.isShowPicCodeLayout()){
                verivyPicCode();
            }
            //验证短信验证码
            else if(pageEntity.isShowSmsLayout()){
                validateSMSFPCode();
            }
            else if(pageEntity.isShowPwdLayout()){
                validatePwdAndPostRequest();
            }
        }
    }

    /**
     * 校验密码输入内容并发送请求
     */
    private void validatePwdAndPostRequest() {

        if (pageEntity.getNewUserPass().length() < 6 || pageEntity.getNewUserPass().length() > 20) {
            ToastUtil.show(BaseApplication.getmContext(), "新密码不符合要求，需6~20位数字加字母！");
            return;
        }
        if (pageEntity.getConfirmUserPass().length() < 6 || pageEntity.getConfirmUserPass().length() > 20) {
            ToastUtil.show(BaseApplication.getmContext(), "新密码不符合要求，需6~20位数字加字母！");
            return;
        }
        if (!RegexUtils.checkIsOnlyContainsNumAndChar(pageEntity.getNewUserPass())) {
            ToastUtil.show(BaseApplication.getmContext(), "新密码必须是6~20位数字加字母！");
            return;
        }

        if (!RegexUtils.checkIsOnlyContainsNumAndChar(pageEntity.getConfirmUserPass())) {
            ToastUtil.show(BaseApplication.getmContext(), "确认密码必须是6~20位数字加字母！");
            return;
        }
        if(!pageEntity.getNewUserPass().equals(pageEntity.getConfirmUserPass())){
            ToastUtil.show(BaseApplication.getmContext(),"两次密码输入不一致，请确认!");
            return ;
        }
        //隐藏键盘
        RxKeyboardTool.hideSoftInput(GetBackPwdActivity.this);
     /*   pageEntity.setNewUserPass( MD5Util.MD5(pageEntity.getNewUserPass()));
        pageEntity.setConfirmUserPass( MD5Util.MD5(pageEntity.getConfirmUserPass()));*/
        resetPwd();
    }


    /**
     * 验证短信验证码请求
     */
    public void validateSMSFPCode(){
        CommonApi.getInstance().validateSMSFPCode(new BaseObserver<BaseResponse<String>>(null) {
            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
                e.printStackTrace();
                ToastUtil.show(BaseApplication.getmContext(),e.message);
            }
            @Override
            public void onNext(BaseResponse<String> loginBean) {
                if(loginBean.code == Constants.SUCCESS_CODE) {
                    if(pageEntity != null){
                        pageEntity.fpToken = loginBean.data;
                        pageEntity.setSmsCode("");
                        //隐藏短信验证
                        pageEntity.setShowSmsLayout(false);
                        //隐藏电话号码
                        pageEntity.setShowPhoneLayout(false);
                        //显示密码输入框
                        pageEntity.setShowPwdLayout(true);
                        pageEntity.setLoginBtnName("确认提交");
                        if(smsTimer != null){
                            smsTimer.cancel();
                            smsTimer = null;
                        }
                    }
                }
                else {
                    if(pageEntity != null) {
                        //隐藏短信验证
                        pageEntity.setShowSmsLayout(false);
//                        pageEntity.setShowSmsCodeLayout(false);
                        pageEntity.setSmsCode("");
                        pageEntity.smsToken = "";
                        pageEntity.setShowPhoneLayout(false);
                        pageEntity.setShowPicCodeLayout(true);
                        //隐藏电话号码
                        pageEntity.setShowPhoneLayout(false);
                        if(smsTimer != null){
                            smsTimer.cancel();
                        }
                        pageEntity.smsTvEnabled = true;

                        getPicCode();
                    }
                    ToastUtil.show(BaseApplication.getmContext(), loginBean.message);
                }
            }
        },new Gson().toJson(new RequestPicSmsEntity(pageEntity)));
    }

    /**
     * 校验电话号码与网点编码
     */
    private void validatePhoneNumAndOrgcod() {

        CommonApi.getInstance().validatePhoneNumAndOrgcode(pageEntity.userPhone, pageEntity.getOrgCode(), new BaseObserver<BaseResponse<PicCodeBean>>(null) {
            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {

                e.printStackTrace();
                ToastUtil.show(BaseApplication.getmContext(),e.message);
            }
            @Override
            public void onNext(BaseResponse<PicCodeBean> validateModel) {

                if(validateModel.code == Constants.SUCCESS_CODE && validateModel.data != null ) {
                    setPicCode(validateModel);
                }
                else{
                    //获取图形验证码
//                    getPicCode();
//                    validatePhoneNumAndOrgcod();
                    ToastUtil.show(BaseApplication.getmContext(),validateModel.message);
                }

            }
        },true);
    }

    /**
     * 获取短信验证码
     */
    public void getSmsCode(View view){
        startCountDown();
    }

    @Override
    public void onBackPressed() {
        if(!handlerBackPressedOrReback()){
            super.onBackPressed();
        }
      /*  //判断当前是否显示的密码设置界面，如果是则返回到电话号码输入界面，否则退回到登录界面
        if(!pageEntity.isShowPhoneLayout()){
            pageEntity.setShowPhoneLayout(true);
            pageEntity.setConfirmUserPass("");
            pageEntity.setNewUserPass("");
            viewDatabinding.smsTv.setText(getResources().getString(R.string.get_back_pwd_validate_sms_code));
        }
        else {
            super.onBackPressed();
        }*/
    }

    /**
     * 处理返回键
     */
    private boolean handlerBackPressedOrReback(){
        boolean isHandler = false;
        if(pageEntity != null){
            if(pageEntity.isShowPwdLayout()){
                pageEntity.setShowSmsLayout(true);
                pageEntity.setShowPhoneLayout(true);
                pageEntity.setShowPwdLayout(false);
                pageEntity.setShowOrgCodeLayout(false);
                isHandler = true;
            }
            else if(pageEntity.isShowSmsLayout()){
                pageEntity.setShowPicCodeLayout(true);
                pageEntity.setShowSmsLayout(false);
                pageEntity.setShowPhoneLayout(false);
                if(smsTimer != null){
                    smsTimer.cancel();
                }
                pageEntity.smsTvEnabled = true;
                getPicCode();
                isHandler = true;
            }
            else if(pageEntity.isShowPicCodeLayout()){
                //可编辑
                viewDatabinding.etPhone.setEnabled(true);
                viewDatabinding.etPhone.setTextColor(getResources().getColor(R.color.color_303133));
                pageEntity.setShowPicCodeLayout(false);
                pageEntity.setShowPhoneLayout(true);
                pageEntity.setShowOrgCodeLayout(true);
                pageEntity.setLoginBtnName("确定");
                pageEntity.setBtnClick(true);
                isHandler = true;
            }
        }
        return isHandler;
    }

    /**
     * 图形验证码计时
     */
    public void startCountDown() {
        /** 倒计时60秒，一次1秒 */
        //避免正在记时时再次点击导致计时显示混乱
        if (pageEntity.smsTvEnabled) {
            if (smsTimer != null) {
                smsTimer.start();
            } else {
                smsTimer = new CountDownTimer(60 * 1000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        // TODO Auto-generated method stub
                        if (pageEntity.smsTvEnabled) {
                            pageEntity.smsTvEnabled = false;
                        }
                        viewDatabinding.smsTv.setText(millisUntilFinished / 1000 + " 秒");
                    }
                    @Override
                    public void onFinish() {
                        viewDatabinding.smsTv.setText("重新发送");
                        pageEntity.smsTvEnabled = true;
                    }
                }.start();
            }
        }
    }

    @Override
    public void finish() {
        if(isUpdatePwdSuccess || !handlerBackPressedOrReback()) {
            super.finish();
            recyclePageParameters();
        }
    }

    /**
     * 释放界面参数
     */
    public void recyclePageParameters(){
        isUpdatePwdSuccess = false;
        if(pageEntity != null){
            pageEntity = null;
        }
        if(smsTimer != null){
            smsTimer.cancel();
            smsTimer = null;
        }
    }
    /**
     * 获取图片验证码
     */
    private void getPicCode() {
        validatePhoneNumAndOrgcod();
    }

    /**
     * 设置图形验证码
     * @param picCodeBean
     */
    private void setPicCode(BaseResponse<PicCodeBean> picCodeBean) {
        pageEntity.captchaToken = picCodeBean.data.captchaToken;
        //data:image/jpeg;base64,
        StringBuilder base64 = new StringBuilder();
        if(!TextUtils.isEmpty(picCodeBean.data.img)) {
            base64.append(picCodeBean.data.img);
        }
        byte[] bitmapArray = Base64.decode(base64.toString(), Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
        viewDatabinding.verifyPicCode.setImageBitmap(bitmap);
        pageEntity.setPicCode("");
        pageEntity.setSmsCode("");
        pageEntity.setShowPhoneLayout(false);
        pageEntity.setShowOrgCodeLayout(false);
        pageEntity.setShowPicCodeLayout(true);
        pageEntity.setBtnClick(false);
        pageEntity.setLoginBtnName("下一步");
    }

    /**
     * 验证图形码
     */
    public void verivyPicCode(){
        CommonApi.getInstance().validatePicCode(new BaseObserver<BaseResponse<String>>(null) {
            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
                getPicCode();//获取图形验证码
            }
            @Override
            public void onNext(BaseResponse<String> smsToken) {

                if(smsToken.code == Constants.SUCCESS_CODE ) {
                    pageEntity.smsToken = smsToken.data;
                    pageEntity.setPicCode("");
                    //显示短信验证码布局
                    pageEntity.setShowSmsLayout(true);
//                    pageEntity.setShowSmsCodeLayout(true);
                    //隐藏图形验证码布局
                    pageEntity.setShowPicCodeLayout(false);
                    //显示电话号码
                    pageEntity.setShowPhoneLayout(true);
                    //不可编辑
                    viewDatabinding.etPhone.setEnabled(false);
                    viewDatabinding.etPhone.setTextColor(getResources().getColor(R.color.float_cancle_font_color));
                    //隐藏网点编码
                    pageEntity.setShowOrgCodeLayout(false);
                    startCountDown();
                }
                else{
                    getPicCode();//获取图形验证码
                    ToastUtil.show(BaseApplication.getmContext(),smsToken.message);
                }

            }
        },new Gson().toJson(new RequestPicSmsEntity(pageEntity)));
    }
    /**
     * 重新获取图片验证码
     * @param v
     */
    public void picClick(View v)
    {
        getPicCode();
    }


    /**
     * 请求
     */
    private void resetPwd() {
        CommonApi.getInstance().updateUserPwd(SPUtils.getStringValue(Constants.USER_TOKEN),
                new Gson().toJson(new FindBackPwdEntity(RxEncryptTool.encryptMD5ToString(pageEntity.getConfirmUserPass()),RxEncryptTool.encryptMD5ToString(pageEntity.getNewUserPass()),pageEntity.fpToken)), new BaseObserver<BaseResponse>(null) {
                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable e) {

                        ToastUtil.show(BaseApplication.getmContext(), e.message);
                    }
                    @Override
                    public void onNext(BaseResponse baseResponse) {
                        if(baseResponse.code == Constants.SUCCESS_CODE){
                            ToastUtil.show(BaseApplication.getmContext(),"重置密码成功!");
                            isUpdatePwdSuccess = true;
                            GetBackPwdActivity.this.finish();
                        }
                        else if(baseResponse.code == Constants.TOKEN_OVERTIME){//token 失效
                            showTokenOverTimeDialog(baseResponse.message);
                        }
                        else {
                            ToastUtil.show(BaseApplication.getmContext(), baseResponse.message);
                        }
                    }
                });
    }

}
