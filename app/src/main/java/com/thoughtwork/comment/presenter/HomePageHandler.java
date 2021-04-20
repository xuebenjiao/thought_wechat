package com.thoughtwork.comment.presenter;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.thoughtwork.comment.acitivity.HomeSearchActivity;
import com.thoughtwork.base.constants.Constants;
import com.xbj.common.BaseClickHandler;
import com.xbj.selectcity.SelectCityActivity;

/**
 * Time :2021/2/18
 * Author:xbj
 * Description :首页、网点、驿站顶部title事件处理
 */
public class HomePageHandler  extends BaseClickHandler {
    private IClickMapView mCallBack;
    /**
     * 选择城市
     * @param view
     */
    public void onSelectCity(View view){
        Intent intent =  new Intent(view.getContext(), SelectCityActivity.class);
        ((Activity)view.getContext()).startActivityForResult(intent, Constants.START_ACTIVITY_FOR_RESULT_REQUEST_CODE);
    }

    /**
     * 点击地图
     * @param view
     */
    public void onClickMap(View view){
        if(mCallBack != null && isValidClick(view)){
            mCallBack.clickMapView(view);
        }
    }

    /**
     * 点击地图
     * @param view
     */
    public void onClickListMap(View view){
        if(mCallBack != null && isValidClick(view)){
            mCallBack.clickMapListView(view);
        }
    }
    /**
     * 搜索
     */
    public  void  searchClick(View view){
        view.getContext().startActivity(new Intent(view.getContext(), HomeSearchActivity.class));

    }

    @Override
    public void handlerBtnClick(View view) {

    }


    public interface IClickMapView{
        void clickMapView(View view);
        void clickMapListView(View view);
    }

    public void setmCallBack(IClickMapView mCallBack) {
        this.mCallBack = mCallBack;
    }

    public HomePageHandler() {
    }

    public HomePageHandler(IClickMapView mCallBack) {
        this.mCallBack = mCallBack;
    }
}
