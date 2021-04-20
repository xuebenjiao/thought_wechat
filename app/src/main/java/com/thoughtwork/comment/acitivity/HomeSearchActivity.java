package com.thoughtwork.comment.acitivity;

import com.thoughtwork.base.activity.MvvmActivity;
import com.thoughtwork.base.viewmodel.MvvmBaseViewModel;
import com.xbj.comment.R;
import com.xbj.comment.databinding.ActivityHomeSearchBinding;
import com.xbj.common.CommonHandler;

import scut.carson_ho.searchview.ICallBack;
import scut.carson_ho.searchview.bCallBack;

/**
 * Time :2021/2/23
 * Author:xbj
 * Description :搜索界面
 */
public class HomeSearchActivity extends MvvmActivity<ActivityHomeSearchBinding, MvvmBaseViewModel> {
    @Override
    public int getLayoutId() {
        return R.layout.activity_home_search;
    }

    @Override
    protected MvvmBaseViewModel getViewModel() {
        return null;
    }



    @Override
    protected void onRetryBtnClick() {

    }

    @Override
    protected void initParameters() {
        viewDatabinding.setEntity(new CommonTitleModel(true,"快递纵横"));
        viewDatabinding.setMyClickHandler(new CommonHandler());
        // 3. 绑定组件
        setClickListener();


    }

    private void setClickListener() {
        // 4. 设置点击搜索按键后的操作（通过回调接口）
        // 参数 = 搜索框输入的内容
       viewDatabinding.searchView.setOnClickSearch(new ICallBack() {
            @Override
            public void SearchAciton(String string) {
                System.out.println("我收到了" + string);
            }
        });

        // 5. 设置点击返回按键后的操作（通过回调接口）
        viewDatabinding.searchView.setOnClickBack(new bCallBack() {
            @Override
            public void BackAciton() {
                finish();
            }
        });
    }
}
