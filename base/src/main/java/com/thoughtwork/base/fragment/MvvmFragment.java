package com.thoughtwork.base.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.gyf.immersionbar.components.ImmersionFragment;
import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;

import com.thoughtwork.base.customview.DialogLoading;
import com.thoughtwork.base.loadsir.EmptyCallback;
import com.thoughtwork.base.loadsir.ErrorCallback;
import com.thoughtwork.base.utils.ToastUtil;
import com.thoughtwork.base.viewmodel.IMvvmBaseViewModel;
import com.xbj.base.R;
import com.thoughtwork.base.activity.MvvmActivity;

import org.simple.eventbus.Subscriber;

/**
 * author : xbj
 * e-mail : xbjzhu@163.com
 * date   : 2019/10/23 15:49
 * desc   :
 */
public abstract class MvvmFragment<V extends ViewDataBinding,VM extends IMvvmBaseViewModel> extends ImmersionFragment implements IBasePagingView {

    //如下两个变量是防止多次点击
    public int lastId;
    public long lastClickTimeStamp;
    //时间戳
    public long lastTimeStamp;
    public long lastWebClickTimeStamp;
    public long mFirstOpenCameraTime;
    //是否已经加载过数据
    public  boolean isLoadData  = false;
    //是否要重置数据操作标识
    public boolean isResetHandlerFlag = false;
    private   Callback.OnReloadListener onReloadListener;
    private MvvmActivity mHostActivity;
    private DialogLoading dialogLoading; //载入loading条
    protected  VM viewModel;
    protected  V viewDataBinding;
    protected  String mFragmentTag = "";
    private LoadService mLoadService;
    private boolean isShowedContent = false;
    //    public abstract  int getBindindVariable();
    public  abstract
    @LayoutRes
    int getLayoutId();
    public abstract VM getViewModel();
    protected  abstract  void onRetryBtnClick();
    protected abstract String getFragmentTag();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusOrNavigationBar();
    }

    /**
     * 设置状态栏
     */
    public void setStatusOrNavigationBar(){
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getIntentData();
        viewDataBinding = DataBindingUtil.inflate(inflater,getLayoutId(),container,false);
        return viewDataBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = getViewModel();
        if(viewModel != null  && view != null){
            //ViewModel 与UI进行绑定
            viewModel.attachUI(this);
        }
/*        if(getBindindVariable() >0){
            viewDataBinding.setVariable(getBindindVariable(),viewModel);
            viewDataBinding.executePendingBindings();
        }*/
        viewDataBinding.executePendingBindings();
        initParameters();
    }

    public void getIntentData(){}

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    @Override
    public void showContent() {
        if(mLoadService != null){
            isShowedContent = true;
            mLoadService.showSuccess();
        }
    }

    @Override
    public void showLoading() {
        if(mLoadService != null){
//            mLoadService.showCallback(LoadingCallback.class);
        }
    }

    @Override
    public void onRefreshEmpty() {
        if(mLoadService != null) {
            mLoadService.showCallback(EmptyCallback.class);
        }
    }

    @Override
    public void onRefreshFailure(String message) {
        if(mLoadService != null){
            if(!isShowedContent){
                mLoadService.showCallback(ErrorCallback.class);
            }else{
                ToastUtil.show(getContext(),message);
            }

        }
    }
    /**
     * 初始化参数 类似于Bundle等
     */
    protected  void initParameters(){

    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(getContext());
        Log.d(getFragmentTag(), this + ": " + "onAttach");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(getFragmentTag(), this + ": " + "onDetach");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(getFragmentTag(), this + ": " + "onStop");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(getFragmentTag(), this + ": " + "onPause");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(getFragmentTag(), this + ": " + "onResume");
    }

    @Override
    public void onDestroy() {
        Log.d(getFragmentTag(), this + ": " + "onDestroy");
        super.onDestroy();
        recycleParentPageParameters();
    }

    /**
     * 释放父类中相关变量
     */
    private void recycleParentPageParameters() {
        if(mHostActivity != null){
            mHostActivity = null;
        }
        if(onReloadListener != null){
            onReloadListener = null;
        }
        if(dialogLoading != null){
            dialogLoading.dismiss();
            dialogLoading = null;
        }
        if(viewModel != null){
            viewModel = null;
        }
        if(mLoadService != null){
            mLoadService = null;
        }
    }

    public void setLoadSir(View view) {
        // You can change the callback on sub thread directly.
        onReloadListener =    new Callback.OnReloadListener() {
            @Override
            public void onReload(View v) {
                onRetryBtnClick();
            }
        };
        mLoadService = LoadSir.getDefault().register(view, onReloadListener);
    }

    @Override
    public void onLoadMoreFailure(String message) {
        ToastUtil.show(getContext(), message);
    }

    @Override
    public void onLoadMoreEmpty() {
        ToastUtil.show(getContext(), getString(R.string.no_more_data));
    }

    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);
        if(activity instanceof  MvvmActivity){
            mHostActivity = (MvvmActivity) activity;
        }
    }

    public  void showTokenOverTimeDialog(String message) {
        if(mHostActivity != null) {
            mHostActivity.showTokenOverTimeDialog(message);
        }
    }
    public void requestPermission(MvvmActivity.PermissionQuestListener callback,String... permissions){
        mHostActivity.requestPermission(callback,permissions);
    }

    @Override
    public void initImmersionBar() {

    }


    /**
     * 是否多次点击
     *
     * @param view
     * @return
     */
    public boolean isValidClick(View view) {
        long time = System.currentTimeMillis();
        boolean valid = view.getId() != lastId || time - lastClickTimeStamp > 500;
        lastId = view.getId();
        lastClickTimeStamp = time;
        return valid;
    }
}
