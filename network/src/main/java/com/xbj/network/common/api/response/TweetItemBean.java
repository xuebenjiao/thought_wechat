package com.xbj.network.common.api.response;

import java.util.ArrayList;

/**
 * Time :2021/4/20
 * Author:xbj
 * Description :Tweets列表请求响应实体对象
 */
public class TweetItemBean extends Comment {
   public ArrayList<Image> images;
   public ArrayList<Comment> comments;

    public class Image{
        String  url;
    }
}
