package com.xbj.network.common.api;



import com.xbj.common.response.Comment;
import com.xbj.common.response.TweetItemBean;
import com.xbj.common.views.listItem.PublisherInforItemViewModel;

import java.util.ArrayList;

import io.reactivex.Observable;
import retrofit2.http.GET;


/**
 * date :2019/10/29.
 * author:xbj
 * description:
 */

public interface CommonApiInterface {
    @GET("jsmith/tweets")
    Observable<ArrayList<PublisherInforItemViewModel>> getTweets();

    @GET("jsmith")
    Observable<Comment.Sender> getUserInfor();
}

