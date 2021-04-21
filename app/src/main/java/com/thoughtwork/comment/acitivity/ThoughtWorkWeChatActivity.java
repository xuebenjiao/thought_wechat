package com.thoughtwork.comment.acitivity;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.thoughtwork.base.activity.MvvmActivity;
import com.thoughtwork.base.utils.LiveDataBus;
import com.thoughtwork.comment.R;
import com.thoughtwork.comment.databinding.ActivityThoughtWorkWechatLayoutBinding;
import com.thoughtwork.comment.databing.viewmodel.TweetItemViewModel;
import com.xbj.common.views.adapter.RecyclerViewAdapter;
import com.xbj.common.views.listItem.PublisherInforItemViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Time :2021/4/20
 * Author:xbj
 * Description :tweets信息展示列表
 */
public class ThoughtWorkWeChatActivity extends MvvmActivity<ActivityThoughtWorkWechatLayoutBinding, TweetItemViewModel> {
    private boolean mIsLastPage;
    private int pageNo = 1;
    private int pageLimit = 5;

    private ArrayList<PublisherInforItemViewModel> mOriginDataList = new ArrayList<PublisherInforItemViewModel>();
    //tweet类型列表
    private RecyclerViewAdapter mTweetAdapter;
    @Override
    public int getLayoutId() {
        return R.layout.activity_thought_work_wechat_layout;
    }

    @Override
    protected TweetItemViewModel getViewModel() {
        return new ViewModelProvider(this).get(TweetItemViewModel.class);
    }

    @Override
    protected void onRetryBtnClick() {

    }

    @Override
    protected void initParameters() {
        setListParameters();
        setClickListener();
        setLiveDataObserve();
    }

    /**
     * 设置监听
     */
    private  void setClickListener(){
        viewDatabinding.refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (mIsLastPage) {
                    finishListReFreshLoadMore();
                } else {
                    pageNo++;
                    if(mOriginDataList.size() >= pageNo * pageLimit ){
                        mIsLastPage = true;
                        viewDatabinding.refreshLayout.setNoMoreData(mIsLastPage);
                        mTweetAdapter.setData(mOriginDataList);
                    }
                    else{
                        List<PublisherInforItemViewModel> list = new ArrayList<PublisherInforItemViewModel>();
                        list = mOriginDataList.subList(0,pageNo * pageLimit-1);
                        mTweetAdapter.setData(list);
                    }
                    finishListReFreshLoadMore();
                }
            }
        });
        viewDatabinding.refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mIsLastPage = false;
                viewDatabinding.refreshLayout.setNoMoreData(mIsLastPage);
                queryDataForFirstPage();
                finishListReFreshLoadMore();
            }
        });
    }

    /**
     * 设置liveData数据监听
     */
    private void setLiveDataObserve(){
        LiveDataBus.getInstance().with("sweetListData", ArrayList.class).observe(this, new Observer<ArrayList<PublisherInforItemViewModel>>() {
            @Override
            public void onChanged(ArrayList<PublisherInforItemViewModel> tweetList) {
                if(tweetList != null && tweetList.size() >0){
                    mOriginDataList = tweetList;
                    queryDataForFirstPage();
                }
            }
        });
    }

    /**
     * 查询第一页数据
     */
    private void queryDataForFirstPage() {
        if(mOriginDataList.size() <= 5) {
            mIsLastPage = true;
            mTweetAdapter.setData(mOriginDataList);
            viewDatabinding.refreshLayout.setNoMoreData(mIsLastPage);
        }
        else{
            List<PublisherInforItemViewModel> list = new ArrayList<PublisherInforItemViewModel>();
            list = mOriginDataList.subList(0,5);
            mTweetAdapter.setData(list);
        }
    }

    /**
     * 设置recycleView
     */
    private void setListParameters() {
        mTweetAdapter = new RecyclerViewAdapter();
        viewDatabinding.listview.setHasFixedSize(true);
        viewDatabinding.listview.setLayoutManager(new LinearLayoutManager(this));
        viewDatabinding.listview.setAdapter(mTweetAdapter);
    }
    /**
     * 结束下来刷新
     */
    @Override
    public void finishListReFreshLoadMore() {
        if(viewDatabinding != null && viewDatabinding.refreshLayout != null) {
            viewDatabinding.refreshLayout.finishLoadMore();
            viewDatabinding.refreshLayout.finishRefresh();
        }
    }
}
