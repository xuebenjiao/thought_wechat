package com.thoughtwork.comment.databing.viewmodel;
import com.thoughtwork.base.activity.IBaseView;
import com.thoughtwork.base.constants.Constants;
import com.thoughtwork.base.model.BaseModel;
import com.thoughtwork.comment.databing.model.GetTweetsModel;
import com.xbj.network.common.api.response.TweetItemBean;

/**
 * Time :2019/11/4
 * Author:xbj
 * Description :
 */
public class TweetItemViewModel extends MvvmBaseViewModel<IBaseView, GetTweetsModel> implements BaseModel.IModelListener<TweetItemBean,String> {
    private GetTweetsModel mGetTweetModel;
    public LoginViewModel(){
        mGetTweetModel = new GetTweetsModel();
        mGetTweetModel.register(this);
    }
    @Override
    public void onLoadFinish(BaseModel model, TweetItemBean data) {
        if (getPageView() != null) {
            if (data == null) return;
            if (data.code == Constants.SUCCESS_CODE) {
                //登录
                if (model instanceof GetTweetsModel) {
                    TweetItemBean  tweetItemBean = data;
                }

            }
        }
    }

    @Override
    public void onLoadFail(BaseModel model, String entity) {

    }
}
