package com.xbj.common.views.listItem;

import com.thoughtwork.base.customview.BaseCustomViewModel;
import com.xbj.common.response.Comment;

/**
 * Time :2021/4/21
 * Author:xbj
 * Description :评论信息子项对应的界面实体
 */
public class CommentContentItemViewModel extends  BaseCustomViewModel {

    //发表/评论内容
    public String content;
    public Comment.Sender sender;


    public class Sender extends BaseCustomViewModel {
        public String username;
        public String nick;
        public String avatar;

    }
}
