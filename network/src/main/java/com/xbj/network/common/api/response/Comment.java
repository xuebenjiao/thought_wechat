package com.xbj.network.common.api.response;

/**
 * Time :2021/4/20
 * Author:xbj
 * Description :tweet列表子项评论解析实体
 */
public class Comment {
    //发表/评论内容
    public String content;
    public Sender sender;


    public class Sender {
        public String username;
        public String nick;
        public String avatar;

    }
}
