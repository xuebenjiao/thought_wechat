package com.xbj.network.common.api.bean.request;

import com.thoughtwork.base.constants.Constants;
import com.thoughtwork.base.utils.SPUtils;

/**
 * Time :2021/3/18
 * Author:xbj
 * Description :发布中心分页查询参数（管理者）
 */
public class PublishedTaskPageQueryBean extends BasePageQueryBean{
    public  String createUserId = SPUtils.getStringValue(Constants.USER_ID);
    public  String  taskName;
    //任务归类(1:网点任务,2:驿站任务）
    public  String taskTypeClassify  = "1";
    public  String taskTypeName;
}
