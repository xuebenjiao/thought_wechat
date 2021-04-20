package com.xbj.network.common.api.bean.request;

import com.thoughtwork.base.constants.Constants;
import com.thoughtwork.base.utils.SPUtils;

/**
 * Time :2021/3/18
 * Author:xbj
 * Description :网点/驿站 数据分页查询请求实体
 */
public class TaskRankPageQueryBean  extends BasePageQueryBean{
    public  String createUserId = SPUtils.getStringValue(Constants.USER_ID);
    public  String taskTypeClassify;
    //排序方式  1 降序  2  升序
    public int rankType = 1;
}

