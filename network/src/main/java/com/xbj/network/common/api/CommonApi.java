package com.xbj.network.common.api;


import com.thoughtwork.base.BaseApplication;
import com.xbj.network.ApiBase;
import com.xbj.network.common.api.response.Comment;
import com.xbj.network.common.api.response.TweetItemBean;

import io.reactivex.Observer;

/**
 * date :2021/4/20.
 * author:xbj
 * description:
 */

public final class CommonApi extends ApiBase {
    private static volatile CommonApi instance = null;
    private CommonApiInterface commonApiInterface;
    public static final String PAGE = "page";
    private CommonApi() {
        super(BaseApplication.serverUrl);
        commonApiInterface = retrofit.create(CommonApiInterface.class);
    }

    public static CommonApi getInstance() {
        if (instance == null) {
            synchronized (CommonApi.class) {
                if (instance == null) {
                    instance = new CommonApi();
                }
            }
        }
        return instance;
    }
    /**
     * 获取tweets列表
     * @param observer
     */
    public void getTweets(Observer<TweetItemBean> observer) {
        ApiSubscribe(commonApiInterface.getTweets(),observer);
    }
    /**
     * 获取用户信息
     * @param observer
     */
    public void getUserInfor(Observer<Comment.Sender> observer) {
        ApiSubscribe(commonApiInterface.getUserInfor(), observer);
    }

}
