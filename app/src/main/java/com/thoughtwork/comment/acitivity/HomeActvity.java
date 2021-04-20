package com.thoughtwork.comment.acitivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.billy.cc.core.component.CC;
import com.billy.cc.core.component.CCResult;
import com.thoughtwork.comment.pageentity.HomePageEntity;
import com.thoughtwork.comment.presenter.HomePageHandler;
import com.thoughtwork.comment.viewmodel.AppUpdateViewModel;
import com.yanzhenjie.permission.AndPermission;
import com.thoughtwork.base.BaseApplication;
import com.thoughtwork.base.activity.MvvmActivity;
import com.thoughtwork.base.constants.Constants;
import com.thoughtwork.base.model.UpdateBean;
import com.thoughtwork.base.utils.ActivityManager;
import com.thoughtwork.base.utils.LiveDataBus;
import com.thoughtwork.base.utils.SPUtils;
import com.thoughtwork.base.utils.ToastUtil;
import com.xbj.comment.R;
import com.xbj.comment.databinding.ActivityHomeBinding;
import com.xbj.common.bean.BaseCityBean;
import com.xbj.webview.CommonWebFragment;
import com.xbj.webview.JsBridgeFragment;
import com.xbj.webview.utils.WebConstants;
import com.open.hule.library.entity.AppUpdate;
import com.open.hule.library.listener.MainPageExtraListener;
import com.open.hule.library.utils.UpdateManager;

/**
 * Time :2021/2/18
 * Author:xbj
 * Description :首页
 */
public class HomeActvity extends MvvmActivity<ActivityHomeBinding, AppUpdateViewModel> implements  MainPageExtraListener , HomePageHandler.IClickMapView {
    /*更新下载管理类*/
    private UpdateManager updateManager;
    private boolean mIsManager = SPUtils.getIntValue(Constants.ROLE_TYPE) == 1;
    private boolean hasWindowsFocus = false;
    private boolean mSelectCityFlag = false;
    public LocationClient mLocationClient = null;
    private MyLocationListener myListener = new MyLocationListener();
    private HomePageEntity pageEntity;
    private SparseArray<Fragment> mFragmentSparseArray;
    private SparseArray<Fragment> mFragmentSparseIndexArray = new SparseArray<>();
    private Fragment mHomeFragment, mPublishFragment, mTaskCenterFragment,mNewsFragment, mimeFragment,mapViewFragment,mCurrentFragment,mFragmentBeforClickMap;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    public void initParameters() {
        pageEntity = new HomePageEntity();
        viewDatabinding.setEntity(pageEntity);
        HomePageHandler homePageHandler =   new HomePageHandler(this);
        viewDatabinding.setPresenter(homePageHandler);

//首页
        CCResult home = CC.obtainBuilder(getString(R.string.Scan)).setActionName(getString(R.string.Scan_ActionName_getHomeFragment)).build().call();
        mHomeFragment = (Fragment) home.getDataMap().get(getString(R.string.Component_parameter_fragment));

        //发布中心（管理者）
        CCResult outlets = CC.obtainBuilder(getString(R.string.Scan)).setActionName(getString(R.string.Scan_ActionName_getPublishTaskCenterFragment)).build().call();
        mPublishFragment = (Fragment) outlets.getDataMap().get(getString(R.string.Component_parameter_fragment));

        //任务中心（网点/驿站）
        CCResult station = CC.obtainBuilder(getString(R.string.Scan)).setActionName(getString(R.string.Scan_ActionName_getTaskCenterFragment)).build().call();
        mTaskCenterFragment = (Fragment) station.getDataMap().get(getString(R.string.Component_parameter_fragment));


        //江湖
        mNewsFragment = new JsBridgeFragment();
        Bundle bundle = new Bundle();
        bundle.putString(WebConstants.INTENT_TAG_URL,BaseApplication.webviewUrl);
        bundle.putString(WebConstants.INTENT_TAG_TITLE,"快递江湖");
        mNewsFragment.setArguments(bundle);

        //地图
        CCResult result = CC.obtainBuilder(getString(R.string.Scan)).setActionName(getString(R.string.Scan_ActionName_getMapviewFragment)).build().call();
        mapViewFragment = (Fragment) result.getDataMap().get(getString(R.string.Component_parameter_fragment));

        //我的
        CCResult center = CC.obtainBuilder(getString(R.string.UserCenter)).setActionName(getString(R.string.UserCenter_ActionName_getMyCenterFragment)).build().call();
        mimeFragment = (Fragment) center.getDataMap().get(getString(R.string.Component_parameter_fragment));
        initTab();
        setLiveDataObserve();
        setClickListener();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!SPUtils.getBooleanValue(Constants.SELECT_CITY)) {
            judgeIsHasLocationService();
        }
    }

    /**
     * 设置LiveData
     */
    private void setLiveDataObserve() {
        LiveDataBus.getInstance().with("AppUpdate", UpdateBean.class).observe(this, new Observer<UpdateBean>() {
            @Override
            public void onChanged(UpdateBean updateBean) {
                if(updateBean != null) {
                    setUpdateManagerParams(updateBean);
                }
            }
        });
    }

    /**
     * 设置监听
     */
    public void setClickListener(){
    }
    private void initTab() {
        mFragmentSparseArray = new SparseArray<>();
        mFragmentSparseArray.append(R.id.home_tab, mHomeFragment);
        mFragmentSparseArray.append(R.id.task_tab, mIsManager?mPublishFragment:mTaskCenterFragment);
        mFragmentSparseArray.append(R.id.world_tab, mNewsFragment);
        mFragmentSparseArray.append(R.id.settings_tab,mimeFragment);

        //用户没登录时记录便于登录后显示登录前的界面
        if(!TextUtils.isEmpty(SPUtils.getStringValue(Constants.USER_TOKEN))) {
            mFragmentSparseIndexArray.append(1, mIsManager?mPublishFragment:mTaskCenterFragment);
            mFragmentSparseIndexArray.append(2, mNewsFragment);
            mFragmentSparseIndexArray.append(3, mimeFragment);
        }
        viewDatabinding.tabsRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                pageEntity.setShowTopTitle(checkedId == viewDatabinding.homeTab.getId());
                Fragment selectFragment   =   mFragmentSparseArray.get(checkedId);
                switchFragment(mCurrentFragment, selectFragment);
                mCurrentFragment = selectFragment;

            }
        });


//        int showTabIndex = SPUtils.getIntValue(Constants.USER_CLICK_TAB);
       /* if(!TextUtils.isEmpty(SPUtils.getStringValue(Constants.USER_TOKEN)) && showTabIndex != 0){
            // 默认显示第一个
            mCurrentFragment =   mFragmentSparseIndexArray.get(showTabIndex);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    mCurrentFragment).commit();
            SPUtils.saveIntValue(Constants.USER_CLICK_TAB,0);
        }
        else{*/
        // 默认显示第一个
        mCurrentFragment =   mFragmentSparseArray.get(R.id.home_tab);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                mCurrentFragment).commit();
//        }

        int showTabIndex = SPUtils.getIntValue(Constants.USER_CLICK_TAB);
        if(!TextUtils.isEmpty(SPUtils.getStringValue(Constants.USER_TOKEN)) && showTabIndex != 0){
            // 默认显示第一个
/*            mCurrentFragment =   mFragmentSparseIndexArray.get(showTabIndex);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    mCurrentFragment).commit();*/
            SPUtils.saveIntValue(Constants.USER_CLICK_TAB,0);
            ((RadioButton)viewDatabinding.tabsRg.getChildAt(showTabIndex)).setChecked(true);
        }

    }

    /**
     * 界面底部Tab页切换
     * @param fromFragment
     * @param toFragment
     */
    private void switchFragment(Fragment fromFragment, Fragment toFragment) {
        if (fromFragment != toFragment) {
            FragmentManager manger = getSupportFragmentManager();
            FragmentTransaction transaction = manger.beginTransaction();
            if (!toFragment.isAdded()) {
                if (fromFragment != null) {
                    transaction.hide(fromFragment);
                }
                if (toFragment != null) {
                    transaction.add(R.id.fragment_container, toFragment).commit();
                }

            } else {
                if (fromFragment != null) {
                    transaction.hide(fromFragment);
                }
                if (toFragment != null) {
                    transaction.show(toFragment).commit();
                }
            }
        }
    }

    @Override
    public AppUpdateViewModel getViewModel() {
        return new ViewModelProvider(this).get(AppUpdateViewModel.class);
    }



    @Override
    protected void onRetryBtnClick() {

    }

    @Override
    public void forceExit() {
        //小于2000ms则认为是用户确实希望退出程序-调用System.exit()方法进行退出
        ActivityManager.getActivityManager().popAllActivity();
    }

    @Override
    public void setStatusOrNavigationBar() {
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public  void setLocationParameters() {
        mLocationClient = new LocationClient(getApplicationContext());LocationClientOption option = new LocationClientOption();
        //// 如果设置为0，则代表单次定位，即仅定位一次，默认为0
        //// 如果设置非0，需设置1000ms以上才有效 一秒定位一次
//        option.setScanSpan(1000);
        option.setIsNeedAddress(true);
//可选，是否需要地址信息，默认为不需要，即参数为false
//如果开发者需要获得当前点的地址信息，此处必须为true
        option.setNeedNewVersionRgc(true);
//可选，设置是否需要最新版本的地址信息。默认需要，即参数为true
        mLocationClient.setLocOption(option);
        //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);
        mLocationClient.start();
//mLocationClient为第二步初始化过的LocationClient对象
//需将配置好的LocationClientOption对象，通过setLocOption方法传递给LocationClient对象使用
//更多LocationClientOption的配置，请参照类参考中LocationClientOption类的详细说明
    }

    @Override
    public void clickMapView(View view) {
        pageEntity.setShowMapListFlag(true);
        pageEntity.setShowMapFlag(false);
        mFragmentBeforClickMap = mCurrentFragment;
        switchFragment(mCurrentFragment, mapViewFragment);
        mCurrentFragment = mapViewFragment;
    }

    @Override
    public void clickMapListView(View view) {
        pageEntity.setShowMapListFlag(false);
        pageEntity.setShowMapFlag(true);
        switchFragment(mCurrentFragment, mFragmentBeforClickMap);
        mCurrentFragment = mFragmentBeforClickMap;
    }

    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            String locationCityName = SPUtils.getStringValue(Constants.CURRENT_LOCATION_CITY);
            if (location == null || (!TextUtils.isEmpty(locationCityName) && location.getCity().contains(locationCityName))) {
                return ;
            }
            //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
            //以下只列举部分获取地址相关的结果信息
            //更多结果信息获取说明，请参照类参考中BDLocation类中的说明
            String addr = location.getAddrStr();    //获取详细地址信息
            String country = location.getCountry();    //获取国家
            String province = location.getProvince();    //获取省份
            String city = location.getCity();    //获取城市
            String district = location.getDistrict();    //获取区县
            String street = location.getStreet();    //获取街道信息
            String adcode = location.getAdCode();    //获取adcode
            String town = location.getTown();    //获取乡镇信息
            String cityName = city.replace("市", "");
            SPUtils.saveStringValue(Constants.CURRENT_LOCATION_CITY, cityName);
            //防止定位比数据查询慢，导致界面显示具体城市，列表显示的是全部
            if (TextUtils.isEmpty(SPUtils.getStringValue(Constants.CITY_CODE))) {
                LiveDataBus.getInstance().with("locationCityName", String.class).postValue(cityName);
            }
            pageEntity.setLocationCity(cityName);
            viewDatabinding.locationTv.setText(cityName);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case  Constants.START_ACTIVITY_FOR_RESULT_REQUEST_CODE: {
                if(data != null ) {
                    BaseCityBean selectCity = (BaseCityBean) data.getSerializableExtra(Constants.ACTIVITY_RESULT);
                    pageEntity.setLocationCity(selectCity.getName());
                    //便于切换到其他界面时查询数据
                    SPUtils.saveStringValue(Constants.CITY_CODE,selectCity.getCode());
                    SPUtils.saveStringValue(Constants.CITY_NAME,selectCity.getName());
                    //因为定位是在onResume中执行的，方便切换App时能重新定位，设置该变量方便识别是否去定位
                    SPUtils.saveBooleanValue(Constants.SELECT_CITY,true);
                    SPUtils.saveStringValue(Constants.CURRENT_LOCATION_CITY,"");
//                    selectCity.pageIndex = mCurrentFragment == mHomeFragment ?1:(mCurrentFragment == netWorkFragment)?2:3;
                    if(mCurrentFragment == mHomeFragment) {
                        selectCity.pageIndex = 1;
                        LiveDataBus.getInstance().with("cityCode", BaseCityBean.class).postValue(selectCity);
                    }
                }
                break;
            }
        }
    }


    /**
     * 处理返回键
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //判断用户是否点击了“返回键”
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            boolean flag = false;
            if (mCurrentFragment != null && mCurrentFragment instanceof JsBridgeFragment) {
                flag = ((JsBridgeFragment) mCurrentFragment).onKeyDown(keyCode, event);
            }
            if (flag) {
                return flag;
            } else {
                //与上次点击返回键时刻作差
                if ((System.currentTimeMillis() - lastClickTimeStamp) > 2000) {
                    //大于2000ms则认为是误操作，使用Toast进行提示
                    ToastUtil.show(BaseApplication.getmContext(), "再按一次退出程序");
                    //并记录下本次点击“返回键”的时刻，以便下次进行判断
                    lastClickTimeStamp = System.currentTimeMillis();
                } else {
                    //小于2000ms则认为是用户确实希望退出程序-调用System.exit()方法进行退出
                    ActivityManager.getActivityManager().popAllActivity();
                }
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 真正的窗体完成初始化可见获取焦点可交互是在onWindowFocusChanged()方法被执行时，而这之前，对用户的操作需要做一点限制。
     * @param hasFocus
     */
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && !hasWindowsFocus) {
            hasWindowsFocus = true;
            checkUpdate();
        }
    }
    /**
     * 请求版本更新接口
     */
    private void checkUpdate() {
        if (viewModel != null) {
            viewModel.reqestUpdateVersion();
        }
    }



    /**
     * 设置更新信息
     *
     * @param updateBean
     */
    public void setUpdateManagerParams(UpdateBean updateBean) {
        if (updateBean != null) {
            updateManager = new UpdateManager(this);
            // 更新的数据参数
            AppUpdate appUpdate = new AppUpdate.Builder()
                    //更新地址（必传）
                    .newVersionUrl(updateBean.upgradeUrl)//updateBean.updateUrl  "https://imtt.dd.qq.com/16891/apk/062C96A8B86D52D1EEECC0EFCC56DA14.apk"
                    // 版本号
                    .newVersionCode(updateBean.appVersion)
                    // 通过传入资源id来自定义更新对话框，注意取消更新的id要定义为btnUpdateLater，立即更新的id要定义为btnUpdateNow
                    .updateResourceId(R.layout.dialog_update)
                    // 文件大小
//                .fileSize("5.8M")
                    //是否采取静默下载模式（只显示更新提示，后台下载完自动弹出安装界面），否则，显示下载进度，显示下载失败弹框
                    .isSilentMode(false)
                    //默认不采取强制更新，否则，不更新无法使用
                    .forceUpdate(updateBean.lastForce)
                    //更新内容说明
                    .updateInfo(getUpdateInfo(updateBean))
                    .build();
            updateManager.startUpdate(appUpdate, this);
        }

    }


    /**
     * 获取富文本信息
     *
     * @param updateBean
     * @return
     */
    private String getUpdateInfo(UpdateBean updateBean) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            return (Html.fromHtml(updateBean.upgradeInfo, Html.FROM_HTML_MODE_LEGACY)).toString().replace("\n\n", "\n");
        } else {
            return (Html.fromHtml(updateBean.upgradeInfo)).toString().replace("\n\n", "\n");
        }
    }

//    @Override
  /*  public void forceExit() {
        //小于2000ms则认为是用户确实希望退出程序-调用System.exit()方法进行退出
        ActivityManager.getActivityManager().popAllActivity();
    }*/

    /**
     * android 8.0 申请androidO 安装未知应用的权限
     * 检测到无权限安装未知来源应用，回调接口中需要重新请求安装未知应用来源的权限
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void applyAndroidOInstall() {
        AndPermission.with(this)
                .install()
//                .file(apkFile)
                .onGranted(file -> {
                    // 被允许安装app执行
                    installApkAgain();
                })
                .onDenied(file -> {
                    ToastUtil.show(BaseApplication.getmContext(), "您拒绝了安装未知来源应用，应用暂时无法更新！");
                    if (0 != updateManager.getAppUpdate().getForceUpdate()) {
                        forceExit();
                    }
                })
                .start();

    }

    /**
     * 授权后，再次尝试安装
     */
    private void installApkAgain() {
        if (updateManager != null) {
            updateManager.installAppAgain();
        }
    }

}
