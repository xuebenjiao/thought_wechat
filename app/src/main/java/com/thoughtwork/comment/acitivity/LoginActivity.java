package com.thoughtwork.comment.acitivity;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.billy.cc.core.component.CC;
import com.billy.cc.core.component.CCResult;
import com.google.gson.Gson;
import com.open.hule.library.listener.MainPageExtraListener;
import com.thoughtwork.comment.databing.LoginInforPageModel;
import com.thoughtwork.comment.viewmodel.LoginViewModel;
import com.yanzhenjie.permission.runtime.Permission;
import com.thoughtwork.base.BaseApplication;
import com.thoughtwork.base.activity.MvvmActivity;
import com.thoughtwork.base.constants.Constants;
import com.thoughtwork.base.dialog.SDCustomDialog;
import com.thoughtwork.base.model.UpdateBean;
import com.thoughtwork.base.utils.LogUtils;
import com.thoughtwork.base.utils.RegexUtils;
import com.thoughtwork.base.utils.RxAnimationTool;
import com.thoughtwork.base.utils.RxKeyboardTool;
import com.thoughtwork.base.utils.SPUtils;
import com.thoughtwork.base.utils.ToastUtil;
import com.thoughtwork.base.utils.rxtool.RxEncryptTool;
import com.thoughtwork.base.utils.LiveDataBus;
import com.xbj.comment.R;
import com.thoughtwork.comment.adapter.SmsLoginInforAdapter;
import com.xbj.comment.databinding.ActivityLoginBinding;
import com.thoughtwork.comment.entity.DoubleValidateRequestEntity;
import com.thoughtwork.comment.model.User;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;
import java.util.ArrayList;
import java.util.List;

/**
 * Time :2019/11/4
 * Author:xbj
 * Description :登录
 */

public class LoginActivity extends MvvmActivity<ActivityLoginBinding, LoginViewModel>   {//implements LoginViewModel.ILoginView implements MainPageExtraListener
    private LoginViewModel mViewModel;
    //双重校验请求实体
    private DoubleValidateRequestEntity doubleValidateRequestEntity;
    //上次双重校验的时间
    private long lastDoubleValidateTime;
    private      SDCustomDialog   ztoDialog;
    private SmsLoginInforAdapter smsLoginInforAdapter;
    private int GPS_REQUEST_CODE = 1;
    private LocationManager locationManager;
    private String locationProvider = null;
    private static final String TAG = "LoginActivity";
    private int mSrollHeight;
    private int screenHeight = 0;//屏幕高度
    private int keyHeight = 0; //软件盘弹起后所占高度
    private Activity mActivity;
    private Activity mSpearActivity;
    public User mUser;
    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected LoginViewModel getViewModel() {
        mViewModel  = new ViewModelProvider(this).get(LoginViewModel.class);
        return mViewModel;
    }



    @Override
    protected void onRetryBtnClick() {
    }
    private void initOrSetLoginInforList(ArrayList<LoginInforPageModel> list) {
        if(list != null) {
            viewDatabinding.loginNoteList.setHasFixedSize(true);
            //设置布局
            viewDatabinding.loginNoteList.setLayoutManager(new LinearLayoutManager(LoginActivity.this));
            smsLoginInforAdapter = new SmsLoginInforAdapter();
            smsLoginInforAdapter.setData(list);
            viewDatabinding.loginNoteList.setAdapter(smsLoginInforAdapter);
        }
    }
    /**
     * 跳转至主界面
     */
    private void gotoMainActivity() {
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if(!Build.MODEL.contains("vivo")&& !Build.MODEL.contains("MI")){//vivo X20A
        addLayoutListener();
//        }
        openGPSSEtting();
        checkUpdate();
    }
    public void onClick(View v) {
        if(viewDatabinding != null) {
            if (v.getId() == viewDatabinding.loginBtn.getId()) {
                RxKeyboardTool.hideSoftInput(LoginActivity.this);
                if (!RegexUtils.checkMobile(mUser.getUserPhone())) {
                    ToastUtil.show(BaseApplication.getmContext(), "请输入正确的手机号");
                    return;
                }
                if (!RegexUtils.checkIsOnlyHasNumAndChar(mUser.orgCode)) {
                    ToastUtil.show(BaseApplication.getmContext(), "网点编码只能包含字母与数字!");
                    return;
                }
                //如果是初始密码则跳过规则校验（密码规则修改为6~20数字加字母，但初始密码产品坚持还用123456）
                if(!mUser.getUserPass().equals("123456")) {
                    if (mUser.getUserPass().length() < 6 || mUser.getUserPass().length() > 20) {
                        ToastUtil.show(BaseApplication.getmContext(), "密码不符合要求，需6~20位数字加字母！");
                        return;
                    }
                    if (!RegexUtils.checkIsOnlyContainsNumAndChar(mUser.getUserPass())) {
                        ToastUtil.show(BaseApplication.getmContext(), "密码必须是6~20位数字加字母！");
                        return;
                    }
                }
                LogUtils.i(TAG, RxEncryptTool.encryptMD5ToString(mUser.userPass));
                if (isValidClick(v)) {
//                    requestLogin(new Gson().toJson(new RequestUserEntity(mUser)));
                }
            } else if (v.getId() == R.id.title_layout_left_btn) {
                setInitForKeyBackOrReback();
            }
        }
    }
    /**
     * 找回密码
     */
    public void setRemberPwd(View view){
        if(mUser != null){
            mUser.setSelectRemPwd(!mUser.isSelectRemPwd());
        }
    }
    /**
     * 找回密码
     */
    public void getBackPwd(View view){
        Intent intent = new Intent(LoginActivity.this,GetBackPwdActivity.class);
        intent.putExtra(Constants.LOGIN_PHONE,mUser.getUserPhone());
        intent.putExtra(Constants.ORG_CODE,mUser.getOrgCode());
        startActivity(intent);
    }
    /**
     * 网络测试
     */
    public void goNetworkTest(View view){
        Intent intent = new Intent(LoginActivity.this,NetworkTestActivity.class);
        startActivity(intent);
    }
    /**
     *
     * 物理返回键/左上角返回时将部分变量还原
     */
    private void setInitForKeyBackOrReback() {
        mUser.setShowLoginNoteLayout(false);
        mUser.isHasFailureExpress = false;
        mUser.initialPwdFlag = false;
        mUser.setShowZtoNote(false);
        if(ztoDialog != null){
            ztoDialog.dismiss();
            ztoDialog = null;
        }
    }
    /**
     *  1、获取main在窗体的可视区域
     *  2、获取main在窗体的不可视区域高度
     *  3、判断不可视区域高度
     *      1、大于100：键盘显示  获取Scroll的窗体坐标
     *                           算出main需要滚动的高度，使scroll显示。
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
                                //此种方式太突兀 UI让去掉动画
                                //viewDatabinding.loginPercentRelativeLayout.scrollTo(0, srollHeight);
                                RxAnimationTool.zoomIn(viewDatabinding.loginImage, 0.7f, srollHeight);
                                ObjectAnimator mAnimatorTranslateY = ObjectAnimator.ofFloat(viewDatabinding.loginPercentRelativeLayout, "translationY", 0.0f, -srollHeight);
                                mAnimatorTranslateY.setDuration(300);
                                mAnimatorTranslateY.setInterpolator(new LinearInterpolator());
                                mAnimatorTranslateY.start();
                            }

                        } else {
                            mSrollHeight = 0;
                            //此种方式太突兀 UI让去掉动画
                            //  viewDatabinding.loginPercentRelativeLayout.scrollTo(0, 0);
                            ObjectAnimator mAnimatorTranslateY = ObjectAnimator.ofFloat(viewDatabinding.loginPercentRelativeLayout, "translationY", viewDatabinding.loginPercentRelativeLayout.getTranslationY(), 0);
                            mAnimatorTranslateY.setDuration(300);
                            mAnimatorTranslateY.setInterpolator(new LinearInterpolator());
                            mAnimatorTranslateY.start();
                            RxAnimationTool.zoomOut(viewDatabinding.loginImage, 0.7f);
                        }
                    }
                }
            });
        }
    }
    @Override
    protected void initParameters() {
        super.initParameters();
        mUser = mViewModel.initUser();
        viewDatabinding.setUser(mUser);
        viewDatabinding.setLoginActivity(this);
        CCResult result = CC.obtainBuilder("UserCenter").setActionName("getUpdatePwdActivity").build().call();
        if(result != null && result.getDataMap() != null) {
            mActivity = (Activity) result.getDataMap().get("Activity");
        }
        CCResult spearRes = CC.obtainBuilder("UserCenter").setActionName("getSpearSettingActivity").build().call();
        if(spearRes != null && spearRes.getDataMap() != null) {
            mSpearActivity = (Activity) spearRes.getDataMap().get("Activity");
        }
        initView();
        setLiveDataObserve();

    }
    /**
     * 初始化键盘高度
     */
    private void initView() {
        //获取屏幕高度
        screenHeight = this.getResources().getDisplayMetrics().heightPixels;
        //弹起高度为屏幕高度的1/3
        keyHeight = screenHeight / 3;
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    /**
     * 设置liveData监听
     */
    private  void setLiveDataObserve(){
        LiveDataBus.getInstance().with(LiveDataBus.UPDATE_INIT_PWD,boolean.class).observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean o) {
                jumpToPwdPage();
            }
        });
        LiveDataBus.getInstance().with("gotoMainActivity",String.class).observe(this, new Observer<String>() {
            @Override
            public void onChanged(String o) {
                gotoMainActivity();
            }
        });
        LiveDataBus.getInstance().with("showLoingInfor", List.class).observe(this, new Observer< ArrayList<LoginInforPageModel>>() {
            @Override
            public void onChanged(ArrayList<LoginInforPageModel> list) {
                initOrSetLoginInforList(list);
            }
        });
        LiveDataBus.getInstance().with("gotoSmsLoginActivity",Object.class).observe(this, new Observer() {
            @Override
            public void onChanged(Object o) {
                gotoSmsLoginActivity();
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
//                requestLogin(new Gson().toJson(new RequestLoginEntity(mUser)));
            }
        });
        LiveDataBus.getInstance().with("AppUpdate",UpdateBean.class).observe(this, new Observer<UpdateBean>() {
            @Override
            public void onChanged(UpdateBean updateBean) {
                if(updateBean != null){
//                    setUpdateManagerParams(updateBean);
                }
            }
        });
    }
    /**
     * 获取定位信息
     */
    private void getLocation() {
//        if(TextUtils.isEmpty(SPUtils.getStringValue(Constants.LATIUDE))) {
        requestPermission(new PermissionQuestListener() {
                              @SuppressLint("MissingPermission")
                              @Override
                              public void onGranted() {
//                                  isHasLocationPermissonFlag = true;
                                  locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                                  //获取所有可用的位置提供器
                                  List<String> providers = locationManager.getProviders(true);
                                  if (providers.contains(LocationManager.NETWORK_PROVIDER)) {
                                      //如果是Network
                                      locationProvider = LocationManager.NETWORK_PROVIDER;
                                  } else if (providers.contains(LocationManager.GPS_PROVIDER)) {
                                      //如果是GPS
                                      locationProvider = LocationManager.GPS_PROVIDER;
                                  }
                                  if(locationProvider == null){//防止locationProvider 为null
                                      locationProvider = LocationManager.GPS_PROVIDER;
                                  }
                                  LogUtils.i(TAG,locationProvider);
                                  //监视地理位置变化
                                  locationManager.requestLocationUpdates(locationProvider, 3000, 1, locationListener);
                                  Location location = locationManager.getLastKnownLocation(locationProvider);
                                  if (location != null) {
//                                      isGetNewLocationFlag = true;
                                      SPUtils.saveLongValue(Constants.LAST_LOACTION_TIME,System.currentTimeMillis());
                                      SPUtils.saveStringValue(Constants.LATIUDE,location.getLatitude()+"");
                                      SPUtils.saveStringValue(Constants.LONGITUDE,location.getLongitude()+"");
                                  }
                              }

                              @Override
                              public void onDenied() {
                                  //如果拒绝权限，则清空上次的定位
                                  SPUtils.saveLongValue(Constants.LAST_LOACTION_TIME,0L);
                                  SPUtils.saveStringValue(Constants.LATIUDE,"");
                                  SPUtils.saveStringValue(Constants.LONGITUDE,"");
                              }
                          }
                , Permission.ACCESS_COARSE_LOCATION
                , Permission.ACCESS_FINE_LOCATION);
//        }
    }

    public LocationListener locationListener = new LocationListener() {
        // Provider的状态在可用、暂时不可用和无服务三个状态直接切换时触发此函数
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
        // Provider被enable时触发此函数，比如GPS被打开
        @Override
        public void onProviderEnabled(String provider) {
        }
        // Provider被disable时触发此函数，比如GPS被关闭
        @Override
        public void onProviderDisabled(String provider) {
        }
        //当坐标改变时触发此函数，如果Provider传进相同的坐标，它就不会被触发
        @Override
        public void onLocationChanged(Location location) {
            if (location != null) {
                SPUtils.saveLongValue(Constants.LAST_LOACTION_TIME,System.currentTimeMillis());
                SPUtils.saveStringValue(Constants.LATIUDE,location.getLatitude()+"");
                SPUtils.saveStringValue(Constants.LONGITUDE,location.getLongitude()+"");
                //不为空,显示地理位置经纬度
//                ToastUtil.show( LoginActivity.this,location.getLongitude() + " " + location.getLatitude() + "");
            }
        }
    };
    private void openGPSSEtting() {
        if (checkGpsIsOpen()){
            getLocation();
        }else {
            new AlertDialog.Builder(this).setTitle("GPS")
                    .setMessage("是否去设置GPS?")
                    //  取消选项
                    .setNegativeButton("取消",new DialogInterface.OnClickListener(){

                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //                            Toast.makeText(MainActivity.this, "close", Toast.LENGTH_SHORT).show();
                            // 关闭dialog
                            dialogInterface.dismiss();
                        }
                    })
                    //  确认选项
                    .setPositiveButton("设置", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            //跳转到手机原生设置页面
                            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivityForResult(intent,GPS_REQUEST_CODE);
                        }
                    })
                    .setCancelable(false)
                    .show();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode ==GPS_REQUEST_CODE){
            openGPSSEtting();
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        recyclePageParameters();
    }
    @Override
    public void finish() {
        super.finish();
        recyclePageParameters();
    }
    /**
     * 释放界面相关参数
     */
    private void recyclePageParameters() {
        if(viewDatabinding != null){
            viewDatabinding = null;
        }
        if(locationManager != null){
            locationManager = null;
        }
        if(mActivity != null){
            mActivity = null;
        }
        if(mUser != null){
            mUser = null;
        }
        if(ztoDialog != null){
            ztoDialog.dismiss();
            ztoDialog = null;
        }
        if(smsLoginInforAdapter != null) {
            if(smsLoginInforAdapter.getmItems() != null && smsLoginInforAdapter.getmItems().size()>0){
                smsLoginInforAdapter.getmItems().clear();
            }
            smsLoginInforAdapter = null;
        }
        if(locationManager != null){
            locationManager = null;
        }
        if(locationListener != null){
            locationListener = null;
        }
    }

    /**
     * 双重验证
     * @param
     */
    @Subscriber(tag = "sms_double_validate")
    public void doubleValidate(LoginInforPageModel model)  {
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
        Intent intent = new Intent(LoginActivity.this,mSpearActivity.getClass()) ;
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.GUN_BEAN,model.empGun);
        intent.putExtras(bundle);
        startActivity(intent);
    }
    /**
     * 获取中通短信验证码
     */
    private void getZtoSmsCode() {
        if(TextUtils.isEmpty(mViewModel.getToken())) return;
        mViewModel.getExpressSmsCode(new Gson().toJson(doubleValidateRequestEntity));
    }
    /**
     * 中通短信验证
     */
    private void showZtoValidateDialog() {
        if(ztoDialog == null) {
            ztoDialog = new SDCustomDialog(LoginActivity.this, R.layout.dialog_zto_sms_validate,
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
                                    // 验证中通短信验证码
                                    verivyZtoSmsCode(ztoDialog.getEtText());
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
            //物理返回键不可以退出
            ztoDialog.setCancelable(false);
            ztoDialog.setKeyDownForbid(false);
            ztoDialog.show();
            ztoDialog.startCountDown();
        }
        else {
            if(ztoDialog != null){
                ztoDialog.setEtText("");
                ztoDialog.show();
            }
        }

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
    @Override
    public void onBackPressed() {
        if(mUser != null && mUser.isShowLoginNoteLayout()){
            setInitForKeyBackOrReback();
        }
        else {
            super.onBackPressed();
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        validateOkhttpInterceptor();
        initLoadingDialog();
    }

    private void validateOkhttpInterceptor() {
     /*   if(ApiBase.networkRequestInfo == null) {
            ApiBase.setNetworkRequestInfo(new NetworkRequestInfo());
        }*/
    }
    /**
     * 登录请求
     * @param json
     */
    public void requestLogin(String json){
        validateOkhttpInterceptor();
        mViewModel.requestLogin(json);
    }
    /**
     * 跳转至图形校验界面
     */
    private void gotoSmsLoginActivity() {
        Intent intent = new Intent(LoginActivity.this, SmsLoginActivity.class);
        intent.putExtra(Constants.REMEBER_PWD,mUser.isSelectRemPwd());
        intent.putExtra(Constants.ORG_CODE,mUser.orgCode);
        intent.putExtra(Constants.LOGIN_PHONE,mUser.userPhone);
        intent.putExtra(Constants.LOGIN_PWD,mUser.userPass);
        startActivity(intent);
    }
    /**
     * 跳转至密码设置界面
     * @return
     */
    private void jumpToPwdPage() {
        if(mActivity!= null){
            //如果是初始密码则记住网点编码与电话，避免用户修改密码后重新登录再次输入
            SPUtils.saveStringValue(Constants.ORG_CODE,mUser.orgCode);
            SPUtils.saveStringValue(Constants.LOGIN_PHONE,mUser.userPhone);
            SPUtils.saveStringValue(Constants.USER_TOKEN, mViewModel.getToken());
            startActivity(new Intent(LoginActivity.this,mActivity.getClass()));
        }
    }

    @Override
    public void setStatusOrNavigationBar() {
        super.setStatusOrNavigationBar();
        mImmersionBar.reset();
        mImmersionBar.statusBarColor(com.xbj.common.R.color.color_5242FF)
                .fitsSystemWindows(true)
                .statusBarDarkFont(false)
                .init();
    }
    /**
     * 版本更新检测
     */
    private  void checkUpdate(){
        mViewModel.checkAppUpdate();
    }
}