package com.thoughtwork.comment.acitivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Html;
import android.text.TextUtils;
import android.util.Base64;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.billy.cc.core.component.CC;
import com.billy.cc.core.component.CCResult;
import com.google.gson.Gson;
import com.thoughtwork.comment.databing.LoginInforPageModel;
import com.thoughtwork.comment.viewmodel.SmsLoginViewModel;
import com.thoughtwork.base.BaseApplication;
import com.thoughtwork.base.RichTextClickableSpan;
import com.thoughtwork.base.activity.MvvmActivity;
import com.thoughtwork.base.constants.Constants;
import com.thoughtwork.base.dialog.SDCustomDialog;
import com.thoughtwork.base.dialog.SDEnsureDialog;
import com.thoughtwork.base.utils.NetWorkUtil;
import com.thoughtwork.base.utils.RxKeyboardTool;
import com.thoughtwork.base.utils.SPUtils;
import com.thoughtwork.base.utils.ToastUtil;
import com.thoughtwork.base.utils.Utils;
import com.xbj.common.CommonHandler;
import com.thoughtwork.base.utils.LiveDataBus;
import com.xbj.comment.R;
import com.thoughtwork.comment.adapter.SmsLoginInforAdapter;
import com.xbj.comment.databinding.ActivitySmsLoginBinding;
import com.thoughtwork.comment.entity.DoubleValidateRequestEntity;
import com.thoughtwork.comment.entity.RequestPicSmsEntity;
import com.thoughtwork.comment.model.SmsLoginPageModel;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.List;


/**
 * Time :2020/2/8
 * Author:xbj
 * Description :
 */
public class SmsLoginActivity extends MvvmActivity<ActivitySmsLoginBinding, SmsLoginViewModel> implements CommonHandler.RightBtnCallBack{
    private SmsLoginViewModel mViewModel;
    //双重校验请求实体
    private DoubleValidateRequestEntity doubleValidateRequestEntity;
    //上次双重校验的时间
    private long lastDoubleValidateTime;
    //如下两个变量是防止多次点击
    private int lastId;
    private long lastTimeStamp;
    private SmsLoginInforAdapter smsLoginInforAdapter;
    private      SDCustomDialog   ztoDialog;
    private Activity mActivity;
    private Activity mSpearActivity;
    //短信验证码计时
    private  CountDownTimer smsTimer;
    private boolean isSmsCountDown = false;
    private String smsLoginPhone;
    private static final String TAG = "SmsLoginActivity";
    private int mSrollHeight;
    //屏幕高度
    private int screenHeight = 0;
    //软件盘弹起后所占高度
    private int keyHeight = 0;
    public SmsLoginPageModel mUser;
    @Override
    public int getLayoutId() {
        return  R.layout.activity_sms_login;
    }

    @Override
    protected SmsLoginViewModel getViewModel() {
        mViewModel  = new ViewModelProvider(this).get(SmsLoginViewModel.class);
        return mViewModel;
    }


    @Override
    protected void onRetryBtnClick() {
    }
    public void onClick(View v) {
        if(viewDatabinding != null) {
            if (v.getId() == viewDatabinding.loginBtn.getId()) {
                RxKeyboardTool.hideSoftInput(SmsLoginActivity.this);
                if (NetWorkUtil.isAvailable(this)) {
                    if (!isValidClick(v)) {
                        return;
                    }
                    if (mUser.getLoginBtnName().equals("下一步")) {
                        verivyPicCode();
                    }
                    //点击上一步时重新验证图形验证的操作
                    else if (mUser.getLoginBtnName().equals("上一步")) {
                        getPicCode();//获取图形验证码
                    } else {//确定
                        if (smsTimer != null) {//用户点击后停止计时
                            isSmsCountDown = false;
                            smsTimer.cancel();
                        }
                        verivySmsCode();
                    }
                }
                else {
                    ToastUtil.show(BaseApplication.getmContext(), "网络不可用，请设置后重试！");
                }

            }
            else if(v.getId() == viewDatabinding.titleLayoutLeftBtn.getId()){
                this.finish();
            }
        }
    }
    /**
     * 验证图形码
     */
    public void verivyPicCode(){
        mViewModel.validatePicCode(new Gson().toJson(new RequestPicSmsEntity(mUser)));
    }
    /**
     * 验证短信验证码请求
     */
    public void verivySmsCode(){
        mViewModel.validateRegisterSmsCode(new Gson().toJson(new RequestPicSmsEntity(mUser)));
    }

    /**
     * 验证中通短信验证码请求
     */
    public void verivyZtoSmsCode(String ztoSmsCode){
        if(TextUtils.isEmpty(mViewModel.getToken())) return;
        if(doubleValidateRequestEntity != null){
            doubleValidateRequestEntity.smsVerifier = ztoSmsCode;
        }
        mViewModel.validateSmsCode(new Gson().toJson(doubleValidateRequestEntity));
    }
    /**
     * 获取图片验证码
     */
    private void getPicCode() {
        mViewModel.getImgPicCode();
    }
    /**
     * 设置图形验证码
     * @param picCodeStr
     */
    private void setImgPicCode(String picCodeStr) {
        StringBuilder base64 = new StringBuilder();
        if(!TextUtils.isEmpty(picCodeStr)) {
            base64.append(picCodeStr);
        }
        //data:image/jpeg;base64,
        byte[] bitmapArray = Base64.decode(base64.toString(), Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
        viewDatabinding.verifyPicCode.setImageBitmap(bitmap);
    }

    /**
     * 获取中通短信验证码
     */
    private void getZtoSmsCode() {
        if(TextUtils.isEmpty(mViewModel.getToken())) return;
        mViewModel.getExpressSmsCode(new Gson().toJson(doubleValidateRequestEntity));
    }
    /**
     *  1、获取main在窗体的可视区域
     *  2、获取main在窗体的不可视区域高度
     *  3、判断不可视区域高度
     *      1、大于100：键盘显示  获取Scroll的窗体坐标
     *        算出main需要滚动的高度，使scroll显示。
     *      2、小于100：键盘隐藏
     * viewDatabinding.loginPercentRelativeLayout  根布局
     * viewDatabinding.loginBtn 需要显示的最下方View 此处是btn
     */
    public void addLayoutListener() {
        if(viewDatabinding!= null && viewDatabinding.loginPercentRelativeLayout != null) {
            viewDatabinding.loginPercentRelativeLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    if (viewDatabinding != null && viewDatabinding.loginPercentRelativeLayout != null) {

                        Rect rect = new Rect();

                        viewDatabinding.loginPercentRelativeLayout.getWindowVisibleDisplayFrame(rect);
                        int mainInvisibleHeight = viewDatabinding.loginPercentRelativeLayout.getRootView().getHeight() - rect.bottom;
                        if (mainInvisibleHeight > 200) {

                            int[] location = new int[2];
                            viewDatabinding.loginBtn.getLocationInWindow(location);
                            int srollHeight = (location[1] + viewDatabinding.loginBtn.getHeight()) - rect.bottom;
                            if (mSrollHeight != srollHeight) {
                                mSrollHeight = screenHeight;
                                mSrollHeight = (location[1] + viewDatabinding.loginBtn.getHeight()) - rect.bottom;
//
                                viewDatabinding.loginPercentRelativeLayout.scrollTo(0, srollHeight);//此种方式太突兀 UI让去掉动画
//                        RxAnimationTool.zoomIn(viewDatabinding.loginImage, 0.6f, srollHeight);
                     /*   ObjectAnimator mAnimatorTranslateY = ObjectAnimator.ofFloat(viewDatabinding.loginPercentRelativeLayout, "translationY", 0.0f, -srollHeight);
                        mAnimatorTranslateY.setDuration(300);
                        mAnimatorTranslateY.setInterpolator(new LinearInterpolator());
                        mAnimatorTranslateY.start();*/
                            }

                        } else {
                            mSrollHeight = 0;
                            viewDatabinding.loginPercentRelativeLayout.scrollTo(0, 0);//此种方式太突兀 UI让去掉动画
                   /* ObjectAnimator mAnimatorTranslateY = ObjectAnimator.ofFloat(viewDatabinding.loginPercentRelativeLayout, "translationY", viewDatabinding.loginPercentRelativeLayout.getTranslationY(), 0);
                    mAnimatorTranslateY.setDuration(300);
                    mAnimatorTranslateY.setInterpolator(new LinearInterpolator());
                    mAnimatorTranslateY.start();*/
//                    RxAnimationTool.zoomOut(viewDatabinding.loginImage, 0.6f);

                        }
                    }
                }
            });
        }
    }
    @Override
    protected void initParameters() {
        super.initParameters();
        mUser = mViewModel.initPageModel();
        viewDatabinding.setUser(mUser);
        viewDatabinding.setSmsLoginActivity(this);
        viewDatabinding.setMyClickHandler(new CommonHandler(this));
        viewDatabinding.setEntity(new CommonTitleModel("快递公司登录","",false));
        CCResult result = CC.obtainBuilder("UserCenter").setActionName("getUpdatePwdActivity").build().call();
        if(result != null && result.getDataMap() != null) {
            mActivity = (Activity) result.getDataMap().get("Activity");
        }
        CCResult spearRes = CC.obtainBuilder("UserCenter").setActionName("getSpearSettingActivity").build().call();
        if(spearRes != null && spearRes.getDataMap() != null) {
            mSpearActivity = (Activity) spearRes.getDataMap().get("Activity");
        }
        if(getIntent() != null){
            mUser.selectRemPwd = getIntent().getBooleanExtra(Constants.REMEBER_PWD,false);
            //便于本地数据库区分用户
            mUser.userPhone = getIntent().getStringExtra(Constants.LOGIN_PHONE);
            //网点编码
            mUser.orgCode = getIntent().getStringExtra(Constants.ORG_CODE);
            mUser.loginShowPhone = Utils.setMaskNumberForStr(mUser.userPhone);
            //方便后续更改密码*/
            mUser.userPass = getIntent().getStringExtra(Constants.LOGIN_PWD);
        }
        setLiveDataObserve();
        getPicCode();
        mViewModel.getPersonGuidInfor();
    }

    /**
     * 设置LiveData监听
     */
    private void setLiveDataObserve(){
        LiveDataBus.getInstance().with("pic_code",String.class).observe(this, new Observer<String>() {
            @Override
            public void onChanged(String imgCode) {
                if(!TextUtils.isEmpty(imgCode)){
                    setImgPicCode(imgCode);
                }
            }
        });
        LiveDataBus.getInstance().with("showLoingInfor", List.class).observe(this, new Observer< ArrayList<LoginInforPageModel>>() {
            @Override
            public void onChanged(ArrayList<LoginInforPageModel> list) {
                initOrSetLoginInforList(list);
            }
        });

        LiveDataBus.getInstance().with("showExpressValidateDialog",boolean.class).observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean flag) {
                //避免首页验证失败 flag true 验证成功
                if(flag ||(!flag && ztoDialog !=null)){
                    showZtoValidateDialog();
                }
            }
        });
        LiveDataBus.getInstance().with("hideExpressValidateDialog",boolean.class).observe(this, new Observer() {
            @Override
            public void onChanged(Object o) {
                if(ztoDialog != null){
                    ztoDialog.dismiss();
                    ztoDialog  = null;
                }
                requestLogin();
            }
        });
        LiveDataBus.getInstance().with("ValidatePicCodeSuccess",String.class).observe(this, new Observer<String>() {
            @Override
            public void onChanged(String o) {
                setCountDownTv();
            }
        });
        LiveDataBus.getInstance().with("requestLogin",String.class).observe(this, new Observer<String>() {
            @Override
            public void onChanged(String o) {
                requestLogin();
            }
        });
        LiveDataBus.getInstance().with("showUserGuidInfor",String.class).observe(this,new Observer<String>() {
            @Override
            public void onChanged(String o) {
                jump2HomeOrUpdatePwd();
            }
        });
    }
    /**
     * 重新获取图片验证码
     * @param v
     */
    public void picClick(View v){
        getPicCode();
    }
    /**
     * 重新获取短信验证码
     */
    public void smsClick(View v){
        setCountDownTv();
    }
    /**
     * 登录请求
     */
    public void requestLogin(){
        validateOkhttpInterceptor();
//        mViewModel.requestLogin(new Gson().toJson(new RequestLoginEntity(mUser)));
    }
    /**
     * 登录成功后的跳转逻辑
     */
    private void jump2HomeOrUpdatePwd() {
        new SDEnsureDialog(SmsLoginActivity.this).builder().setTitle(getResources().getString(R.string.show_personal_infor_title))
                //富文本内容
                .setSubTitle(
                        getResources().getString(R.string.privacy_content1),
                        //可点击富文本字体颜色
                        getResources().getColor(R.color.red),
                        new RichTextClickableSpan.RichTextClickCallBack() {
                            @Override
                            public void onClick(View view) {
                                if (!TextUtils.isEmpty(mUser.privacyPolicy)) {
//                                    Intent intent = new Intent(SmsLoginActivity.this, PrivacyPolicyActivity.class);
                               /*     intent.putExtra(Constants.MODULE_NAME, "隐私政策");
                                    intent.putExtra(Constants.PRIVACY_POLICY_SERVICE_AGREEMENT, getRichText(mUser.privacyPolicy));
                                    startActivity(intent);*/
                                } else {
                                    ToastUtil.show(BaseApplication.getmContext(), "获取隐私政策内容失败，暂时不能查看！");
                                }
                            }
                        }
                        , new RichTextClickableSpan.RichTextClickCallBack() {
                            @Override
                            public void onClick(View view) {
                                if (!TextUtils.isEmpty(mUser.serviceAgreement)) {
                                 /*   Intent intent = new Intent(SmsLoginActivity.this, PrivacyPolicyActivity.class);
                                    intent.putExtra(Constants.MODULE_NAME, "服务协议");
                                    intent.putExtra(Constants.PRIVACY_POLICY_SERVICE_AGREEMENT, getRichText(mUser.serviceAgreement));
                                    startActivity(intent);*/
                                } else {
                                    ToastUtil.show(BaseApplication.getmContext(), "获取服务协议内容失败，暂时不能查看！");
                                }
                            }
                        }
                )
                .setPositiveButton("我知道了", SmsLoginActivity.this.getResources().getColor(R.color.color_5242FF), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        jumpToOtherPage();
                    }
                })
                .setSubTitleGravity(Gravity.CENTER | Gravity.LEFT)
                .setNegativeButton("", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                }).setCancelable(false)
                .show();
    }
    /**
     * 跳转逻辑
     */
    private void jumpToOtherPage() {
        //如果是1 则强制跳转到密码修改界面修改面
        if(mUser != null && mUser.initialPwdFlag){
            if(mActivity!= null){
                //如果是初始密码则记住网点编码与电话，避免用户修改密码后重新登录再次输入
                SPUtils.saveStringValue(Constants.ORG_CODE,mUser.orgCode);
                SPUtils.saveStringValue(Constants.LOGIN_PHONE,mUser.userPhone);
                SPUtils.saveStringValue(Constants.USER_TOKEN, mViewModel.getToken());
                startActivity(new Intent(SmsLoginActivity.this,mActivity.getClass()));
                this.finish();
            }
        }
        else {
            Utils.playFromRawFile(R.raw.login_success);
            mViewModel.saveParameters();
            startActivity(new Intent(SmsLoginActivity.this, MainActivity.class));
        }
        this.finish();
    }

    /**
     * 图形验证码计时
     */
    public void setCountDownTv() {
        mUser.setShowSmsLayout(true);
        /** 倒计时60秒，一次1秒 */
        if(!isSmsCountDown) {//避免正在记时时再次点击导致计时显示混乱
            if (smsTimer != null) {
                smsTimer.start();
            } else {
                smsTimer = new CountDownTimer(60 * 1000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        // TODO Auto-generated method stub
                        if(!isSmsCountDown){
                            isSmsCountDown = true;
                        }
                        viewDatabinding.loginBtn.setText(millisUntilFinished / 1000 + " 秒"+"   确定");
                    }

                    @Override
                    public void onFinish() {
                        mUser.setClickable(true);//如果到时用户还么有输入，则设置按钮可点击
                        viewDatabinding.loginBtn.setText("上一步");
                        isSmsCountDown = false;
                    }
                }.start();
            }
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        recyclePageParameters();
    }

    /**
     * 释放界面相关变量
     */
    private void recyclePageParameters() {
        if(smsLoginInforAdapter != null) {
            smsLoginInforAdapter = null;
        }
        if(ztoDialog !=null){
            ztoDialog.dismiss();
            ztoDialog = null;
        }
        if(mActivity != null){
            mActivity = null;
        }
        //用户点击后停止计时
        if(smsTimer != null) {
            smsTimer.cancel();
            smsTimer =null;
        }
        if(viewDatabinding != null){
            viewDatabinding = null;
        }
        if(mUser != null){
            mUser = null;
        }
    }
    @Override
    public void finish() {
        super.finish();
        recyclePageParameters();
    }

    private void initOrSetLoginInforList(ArrayList<LoginInforPageModel> list) {
        if(list != null) {
            viewDatabinding.loginNoteList.setHasFixedSize(true);
            //设置布局
            viewDatabinding.loginNoteList.setLayoutManager(new LinearLayoutManager(SmsLoginActivity.this));
            smsLoginInforAdapter = new SmsLoginInforAdapter();
            smsLoginInforAdapter.setData(list);
            viewDatabinding.loginNoteList.setAdapter(smsLoginInforAdapter);
        }
    }
    /**
     * 中通短信验证
     */
    private void showZtoValidateDialog() {
        if(ztoDialog == null){
            ztoDialog = new SDCustomDialog(SmsLoginActivity.this, R.layout.dialog_zto_sms_validate,
                    new int[]{
                            R.id.verify_sms_code,
                            R.id.tv_zto_cancle,
                            R.id.tv_confirm,
                    },
                    R.id.edt_alert_dialog_content,
                    R.id.verify_sms_code,
                    R.id.sms_phone,
                    new SDCustomDialog.OnCustomDialogItemClickListener() {
                        @Override
                        public void OnCustomDialogItemClick(SDCustomDialog dialog, View view) {
                            switch (view.getId()) {
                                case R.id.verify_sms_code:
                                    if("重新获取".equals(((TextView)view).getText().toString())) {
                                        getZtoSmsCode();
                                        ztoDialog.startCountDown();
                                    }
                                    break;
                                case R.id.tv_confirm:
                                    if (TextUtils.isEmpty(ztoDialog.getEtText())) {
                                        ToastUtil.show(BaseApplication.getmContext(), "请输入验证码!");
                                        return;
                                    }
                                    if (!isValidClick(view)) return;
                                    //TODO 验证中通短信验证码
                                    verivyZtoSmsCode(ztoDialog.getEtText());//现在中通验证服务端暂未开发完成
                                    break;
                                case R.id.tv_zto_cancle:
                                    if(ztoDialog != null){
                                        ztoDialog.dismiss();
                                        ztoDialog = null;
                                    }
                                    break;
                            }
                        }
                    },
                    false, true, false, doubleValidateRequestEntity.phoneNum);
            ztoDialog.setCancelable(false);
            ztoDialog.setKeyDownForbid(false);
            ztoDialog.show();
            ztoDialog.startCountDown();
        }
        else if(ztoDialog != null){
            ztoDialog.setEtText("");
            ztoDialog.show();
        }
    }
    /**
     * 双重验证
     * @param
     */
    @Subscriber(tag = "sms_double_validate")
    public void doubleValidate(LoginInforPageModel model){
        if(model.isDoubleValiteFlag()) {
            if (!TextUtils.isEmpty(model.expressCode)) {
                //短信验证码发送成功再弹出框，避免电话号码出错也弹框的问题
                //中通双重校验60秒只能发一次
                if (System.currentTimeMillis() - lastDoubleValidateTime > 60000) {
                    lastDoubleValidateTime = System.currentTimeMillis();
                    if(doubleValidateRequestEntity == null){
                        doubleValidateRequestEntity = new DoubleValidateRequestEntity(model.expressCode,model.doubleValitePhone);
                    }
                    getZtoSmsCode();
                } else {
                    ToastUtil.show(BaseApplication.getmContext(), "请一分钟后再试！");
                }
            }
            else{
                ToastUtil.show(BaseApplication.getmContext(), "快递公司编码为空！");
            }
        }
        else if(model.isValidateFlag()){
            gotoSpearActivity(model);
        }
    }
    /**
     * 跳转至配置信息设置界面
     * @param model
     */
    private void gotoSpearActivity(LoginInforPageModel model) {
        SPUtils.saveStringValue(Constants.USER_TOKEN, mViewModel.getToken());
        Intent intent = new Intent(SmsLoginActivity.this,mSpearActivity.getClass()) ;
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.GUN_BEAN,model.empGun);
        intent.putExtras(bundle);
        startActivity(intent);
    }
    @Override
    public void rightBtnCallBack(View view) {
        mUser.setShowLoginNoteLayout(false);
    }
    /**
     * 是否多次点击
     * @param view
     * @return
     */
    public boolean isValidClick(View view) {
        long time = System.currentTimeMillis();
        boolean valid = view.getId() != lastId || time - lastTimeStamp > 500;
        lastId = view.getId();
        lastTimeStamp = time;
        return valid;
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
    @Override
    protected void onResume() {
        super.onResume();
        validateOkhttpInterceptor();
    }
    private void validateOkhttpInterceptor() {
       /* if(ApiBase.getsHttpsRequestInterceptor() == null) {
            ApiBase.setNetworkRequestInfo(new NetworkRequestInfo());
        }*/
    }
    /**
     * 获取富文本信息
     * @return
     */
    private String getRichText(String content) {
        if(!TextUtils.isEmpty(content)) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                return (Html.fromHtml(content, Html.FROM_HTML_MODE_LEGACY)).toString().replace("\n\n","\n");
            } else {
                return (Html.fromHtml(content)).toString().replace("\n\n","\n");
            }
        }
        return "";
    }
    /**
     * 蓝底白字
     */
    @Override
    public void setStatusOrNavigationBar() {
        super.setStatusOrNavigationBar();
        mImmersionBar.reset();
        mImmersionBar.statusBarColor(com.xbj.common.R.color.color_5242FF)
                .fitsSystemWindows(true)
                .statusBarDarkFont(false)
                .init();
    }
}
