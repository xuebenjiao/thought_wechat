package com.thoughtwork.comment.databing.model;

import com.thoughtwork.base.model.BaseModel;
import com.xbj.network.common.api.CommonApi;
import com.xbj.network.common.api.response.TweetItemBean;
import com.xbj.network.errorhandler.ExceptionHandle;
import com.xbj.network.observer.BaseObserver;


/**
 * Time :2021/4/20
 * Author:xbj
 * Description :获取tweet列表model
 */
public  class GetTweetsModel extends BaseModel<TweetItemBean> {
    @Override
    public void refresh() {
    }
    @Override
    public void load() {
        CommonApi.getInstance().getTweets(new BaseObserver<TweetItemBean>(null) {

            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
                e.printStackTrace();
                loadFail(e.message);

            }

            @Override
            public void onNext(TweetItemBean tweetItemBean) {
                loadSuccess(tweetItemBean);
            }
        });
    }

}