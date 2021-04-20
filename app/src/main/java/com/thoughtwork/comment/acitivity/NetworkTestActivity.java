package com.thoughtwork.comment.acitivity;

import android.view.View;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.nalan.swipeitem.recyclerview.SwipeItemLayout;
import com.thoughtwork.comment.viewmodel.NetworkTestViewModel;
import com.thoughtwork.base.BaseApplication;
import com.thoughtwork.base.activity.MvvmActivity;
import com.xbj.common.CommonHandler;
import com.thoughtwork.base.utils.LiveDataBus;
import com.xbj.common.views.adapter.RecyclerViewAdapter;
import com.xbj.comment.R;
import com.xbj.comment.databinding.ActivityNetworkTestBinding;

import java.util.ArrayList;

/**
 * Time :2021/1/24
 * Author:xbj
 * Description :网络测试
 */
public class NetworkTestActivity  extends MvvmActivity<ActivityNetworkTestBinding, NetworkTestViewModel> {
    private RecyclerViewAdapter mAdapter;
    private static final String TAG = "NetworkTestActivity";
    private NetworkTestViewModel mViewModel;
    @Override
    public int getLayoutId() {
        return R.layout.activity_network_test;
    }
    @Override
    protected NetworkTestViewModel getViewModel() {
        mViewModel = new ViewModelProvider(this).get(NetworkTestViewModel.class);
        return mViewModel;
    }

    @Override
    protected void onRetryBtnClick() {
    }
    @Override
    protected void initParameters() {
        super.initParameters();
        mAdapter = new RecyclerViewAdapter();
        //设置布局
        viewDatabinding.recyclerview.setLayoutManager(new LinearLayoutManager(BaseApplication.getmContext()));
        viewDatabinding.recyclerview.addOnItemTouchListener(new SwipeItemLayout.OnSwipeItemTouchListener(BaseApplication.getmContext()));
        viewDatabinding.recyclerview.setAdapter(mAdapter);
        viewDatabinding.setEntity(new CommonTitleModel("网络测试","重试",true,true));
        viewDatabinding.setMyClickHandler(new CommonHandler(new CommonHandler.RightBtnCallBack() {
            @Override
            public void rightBtnCallBack(View view) {
                viewModel.startPingThread();
            }
        }));
        LiveDataBus.getInstance().with(TAG,ArrayList.class).observe(this, new Observer<ArrayList<CommonItemViewModel>>() {
            @Override
            public void onChanged(ArrayList<CommonItemViewModel> list) {
                if(list != null){
                    mAdapter.setData(list);
                }
            }
        });
    }
}
