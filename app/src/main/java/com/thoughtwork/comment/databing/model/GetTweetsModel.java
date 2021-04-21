package com.thoughtwork.comment.databing.model;

import com.thoughtwork.base.model.BaseModel;
import com.xbj.common.views.listItem.PublisherInforItemViewModel;
import com.xbj.network.common.api.CommonApi;
import com.xbj.network.errorhandler.ExceptionHandle;
import com.xbj.network.observer.BaseObserver;

import java.util.ArrayList;


/**
 * Time :2021/4/20
 * Author:xbj
 * Description :获取tweet列表model
 */
public  class GetTweetsModel extends BaseModel<ArrayList<PublisherInforItemViewModel>> {
    @Override
    public void refresh() {
    }
    @Override
    public void load() {
        CommonApi.getInstance().getTweets(new BaseObserver<ArrayList<PublisherInforItemViewModel>>(null) {

            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
                e.printStackTrace();
                loadFail(e.message);

            }

            @Override
            public void onNext(ArrayList<PublisherInforItemViewModel> tweetItemBean) {
                loadSuccess(tweetItemBean);
            }
        });
    }

}