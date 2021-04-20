package com.thoughtwork.comment.acitivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;

import com.gyf.immersionbar.ImmersionBar;
import com.yanzhenjie.permission.runtime.Permission;
import com.thoughtwork.base.BaseApplication;
import com.thoughtwork.base.activity.MvvmActivity;
import com.thoughtwork.base.utils.ToastUtil;
import com.thoughtwork.base.viewmodel.MvvmBaseViewModel;
import com.xbj.comment.R;
import com.xbj.comment.databinding.ActivityWelcomeLayoutBinding;
import com.xbj.network.ApiBase;
import com.xbj.network.interceptor.NetworkRequestInfo;

public class WelComeActivity extends MvvmActivity<ActivityWelcomeLayoutBinding, MvvmBaseViewModel>{

    private Handler delayHandler;
    private Runnable delayRunnable;
    public long begainTime = 0L;
    private boolean hasWindowsFocus = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(!isTaskRoot()){
            finish();
            return;
        }
        ImmersionBar.with(this).transparentNavigationBar().navigationBarAlpha(0.4f).init();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_welcome_layout;
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
        begainTime = System.currentTimeMillis();
        requestPermission();
    }



    /**
     * 生产设备唯一id
     */
    public void buildDeviceSerialId(){
        ApiBase.setNetworkRequestInfo(new NetworkRequestInfo());
    }
    /**
     * 申请权限
     */
    @SuppressLint("WrongConstant")
    private void requestPermission() {
        requestPermission(new PermissionQuestListener(){
                              @Override
                              public void onGranted() {
                                  buildDeviceSerialId();
                                  judgeLogin();
                              }
                              @Override
                              public void onDenied() {
                                  ToastUtil.show(BaseApplication.getmContext(),"没有授权暂时不能运行程序！");
                                  //拒绝后退出应用
                                  WelComeActivity.this.finish();
                              }
                          },
                Manifest.permission.ACCESS_NETWORK_STATE
//                ,Permission.CALL_PHONE
  /*              //定位
                ,Permission.ACCESS_COARSE_LOCATION
                //定位
                ,Permission.ACCESS_FINE_LOCATION*/
                //写错误日志
                , Permission.WRITE_EXTERNAL_STORAGE
                //下载
                , Permission.READ_EXTERNAL_STORAGE,
                //语音
//                ,Permission.RECORD_AUDIO,
                // 获取设备信息
                Permission.READ_PHONE_STATE
        );
    }

    /**
     * 判断是否停留超过1500毫秒，避免高版本手机加载过快，画面比较突兀的感觉
     */
    private  void judgeLogin() {
        long stayTime = System.currentTimeMillis() - begainTime;
        boolean isStayEnoughTimeFlag = stayTime > 1000 ? true : false;
        if (isStayEnoughTimeFlag) {
            gotoHome();
        } else {
            if(delayHandler == null){
                delayHandler = new Handler();
            }
            if(delayRunnable == null){
                delayRunnable =   new Runnable() {
                    @Override
                    public void run() {
                        gotoHome();
                    }
                };
            }
            if(delayHandler != null && delayRunnable != null) {
                delayHandler.postDelayed(delayRunnable, stayTime);
            }
        }
    }

    /**
     * 登录
     */
    private  void gotoHome(){

      /*  //初始化百度语音 因为离线百度语音播报需要读写权限，所以在此处申请
        if(!BaiduTTSUtil.getInstance().isInitTTS()){
            LogUtils.i("TTS没有初始化");
            //BaseApplication.getsApplication().getApplicationContext()
            BaiduTTSUtil.getInstance().initBaiduTTS(BaseApplication.getmContext());
        }
        buildDeviceSerialId();
        boolean isJumped2Home = SPUtils.getBooleanValue(Constants.JUMP_TO_HOME_SUCCESS);
        boolean remeberPwd = SPUtils.getBooleanValue(Constants.REMEBER_PWD);
        long remeberTimeLong = SPUtils.getLongValue(Constants.REMEBER_PWD_TIME);
        boolean pwdOverTimeFlag = (System.currentTimeMillis() - remeberTimeLong) > (7 * 24 * 3600 * 1000) ? true : false;
        //判断本地是否有用户角色，如果没有则进入登录界面
        boolean roleTyIsEmp=  TextUtils.isEmpty(SPUtils.getStringValue(Constants.ROLE_TYPE))?true:false;
        if (isJumped2Home && remeberPwd && !pwdOverTimeFlag && !roleTyIsEmp) {
            startActivity(new Intent(WelComeActivity.this, MainActivity.class));
        } else {
            startActivity(new Intent(WelComeActivity.this, LoginActivity.class));
        }*/
        startActivity(new Intent(WelComeActivity.this, HomeActvity.class));
    }
    @Override
    protected void onStop() {
        super.onStop();
        this.finish();
    }

    /**
     * 真正的窗体完成初始化可见获取焦点可交互是在onWindowFocusChanged()方法被执行时，而这之前，对用户的操作需要做一点限制。
     * @param hasFocus
     */
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(hasFocus&&!hasWindowsFocus && Build.VERSION.SDK_INT >= 19){
            hasWindowsFocus = true;
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        recyclePageParameters();
    }

    @Override
    public void finish() {
        super.finish();
        recyclePageParameters();
    }

    /**
     * 释放界面参数
     */
    private void recyclePageParameters(){
        if(delayHandler != null){
            if(delayRunnable != null) {
                delayHandler.removeCallbacks(delayRunnable);
            }
            delayHandler = null;
        }
    }


}
