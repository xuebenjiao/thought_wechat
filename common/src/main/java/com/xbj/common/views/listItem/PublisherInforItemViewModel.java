package com.xbj.common.views.listItem;
import com.thoughtwork.base.customview.BaseCustomViewModel;
import com.xbj.common.response.Comment;
import com.xbj.common.response.TweetItemBean;

import java.util.ArrayList;

/**
 * Time :2021/4/21
 * Author:xbj
 * Description :原创用户信息展示视图对应的实体
 */
public class PublisherInforItemViewModel extends CommentContentItemViewModel {
    public ArrayList<PublishPicItemViewModel> images;
    public ArrayList<CommentContentItemViewModel> comments;
}
