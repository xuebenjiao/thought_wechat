package com.xbj.network.common.api.bean.request;

import com.thoughtwork.base.constants.Constants;
import com.thoughtwork.base.utils.SPUtils;

/**
 * Time :2021/3/17
 * Author:xbj
 * Description :任务中心界面查询参数实体类
 */
public class TaskListQueryBean extends BasePageQueryBean{
    //任务归类(1:网点任务,2:驿站任务）
    //1 标识网点  2 标识驿站
    public String classify = "1";
    //任务类型
    public String taskTypeId;

    public String  userId = SPUtils.getStringValue(Constants.USER_ID);
}
