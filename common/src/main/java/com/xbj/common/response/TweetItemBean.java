package com.xbj.common.response;

import com.xbj.common.views.listItem.PublishPicItemViewModel;

import java.util.ArrayList;

/**
 * Time :2021/4/20
 * Author:xbj
 * Description :Tweets列表请求响应实体对象
 */
public class TweetItemBean extends Comment {
   public ArrayList<PublishPicItemViewModel> images;
   public ArrayList<Comment> comments;
}
