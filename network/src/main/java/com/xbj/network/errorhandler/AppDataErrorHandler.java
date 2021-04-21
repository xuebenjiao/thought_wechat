package com.xbj.network.errorhandler;

import com.xbj.common.views.listItem.CommentContentItemViewModel;

import java.util.ArrayList;

import io.reactivex.functions.Function;

/**
 * author : xbj
 * e-mail : xbjzhu@163.com
 * date   : 2019/10/22 17:32
 * desc   :
 */

/**
 * HandleFuc处理以下网络错误：
 * 1、应用数据的错误会抛RuntimeException；
 */
public class AppDataErrorHandler implements Function<ArrayList<CommentContentItemViewModel>, ArrayList<CommentContentItemViewModel>> {
    private static final String TAG = "AppDataErrorHandler";
    @Override
    public ArrayList<CommentContentItemViewModel> apply(ArrayList<CommentContentItemViewModel> response) throws Exception {
        return response;
    }
}