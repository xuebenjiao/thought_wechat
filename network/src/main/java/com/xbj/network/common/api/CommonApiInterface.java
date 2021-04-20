package com.xbj.network.common.api;

import com.xbj.network.common.api.response.Comment;
import com.xbj.network.common.api.response.TweetItemBean;

import io.reactivex.Observable;
import retrofit2.http.GET;


/**
 * date :2019/10/29.
 * author:xbj
 * description:
 */

public interface CommonApiInterface {
    @GET("jsmith/tweets")
    Observable<TweetItemBean> getTweets();

    @GET("jsmith")
    Observable<Comment.Sender> getUserInfor();
}

