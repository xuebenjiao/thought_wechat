package com.thoughtwork.comment.databing.viewmodel;

import com.thoughtwork.base.activity.IBaseView;
import com.thoughtwork.base.model.BaseModel;
import com.thoughtwork.base.utils.LiveDataBus;
import com.thoughtwork.base.viewmodel.MvvmBaseViewModel;
import com.thoughtwork.comment.databing.model.GetTweetsModel;
import com.xbj.common.views.listItem.PublisherInforItemViewModel;

import java.util.ArrayList;

/**
 * Time :2019/11/4
 * Author:xbj
 * Description :
 */
public class TweetItemViewModel extends MvvmBaseViewModel<IBaseView, GetTweetsModel> implements BaseModel.IModelListener<ArrayList<PublisherInforItemViewModel>,String> {
    private GetTweetsModel mGetTweetModel;
    public TweetItemViewModel(){
        mGetTweetModel = new GetTweetsModel();
        mGetTweetModel.register(this);
        mGetTweetModel.load();
    }
    @Override
    public void onLoadFinish(BaseModel model, ArrayList<PublisherInforItemViewModel> data) {
        if (getPageView() != null) {
            if (data == null) return;
            if (model instanceof GetTweetsModel) {
                LiveDataBus.getInstance().with("sweetListData",ArrayList.class).postValue(data);
            }
        }
    }

    @Override
    public void onLoadFail(BaseModel model, String entity) {

    }
}
