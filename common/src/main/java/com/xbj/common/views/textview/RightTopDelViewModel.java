package com.xbj.common.views.textview;

import com.xbj.common.entity.response.PublishResponBean;
import com.thoughtwork.base.customview.BaseCustomViewModel;

import java.util.List;

/**
 * Time :2021/3/15
 * Author:xbj
 * Description :
 */
public class RightTopDelViewModel extends BaseCustomViewModel {
    //用于区分同一界面不同listItem
    public int itemType;
    public String content;
    public String contentCode;
    public String relationCode;
    public int index;

    public List<PublishResponBean.OrgBean> orgList;


}
