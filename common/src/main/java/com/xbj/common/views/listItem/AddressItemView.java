package com.xbj.common.views.listItem;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.thoughtwork.base.constants.Constants;
import com.thoughtwork.base.customview.BaseCustomView;
import com.thoughtwork.base.utils.SPUtils;
import com.xbj.common.R;
import com.xbj.common.databinding.AddresssItemViewBinding;
/**
 * Time :2020/4/20
 * Author:xbj
 * Description :
 */
public class AddressItemView extends BaseCustomView<AddresssItemViewBinding, AddressItemViewViewModel> {
    //是否为异步校验
    private boolean asynchFlag = SPUtils.getBooleanValue(Constants.SCAN_VALIDATE_ASYNCHRONOUS);
    /*是否发送EventBus事件的标识 默认是发送，避免公用时到时主界面按钮显示混乱*/
    private boolean isSendEventBusFlag = true;
    private String mTabName;
    private String mCurrentModuleName;
    private boolean isShowCheckBox = true;
    //是否点击可查看物流轨迹
    private boolean isClickShowExpressTrajectory;

    public AddressItemView(Context context, boolean isSendEventBusFlag, String tabName, String mCurrentModuleName) {
        super(context);
        this.mTabName = tabName;
        this.isSendEventBusFlag = isSendEventBusFlag;
        this.mCurrentModuleName = mCurrentModuleName;
    }
    public AddressItemView(Context context, boolean isSendEventBusFlag, String tabName, String mCurrentModuleName,boolean isShowCheckBox,boolean isClickShowExpressTrajectory) {
        super(context);
        this.mTabName = tabName;
        this.isSendEventBusFlag = isSendEventBusFlag;
        this.mCurrentModuleName = mCurrentModuleName;
        this.isShowCheckBox = isShowCheckBox;
        this.isClickShowExpressTrajectory = isClickShowExpressTrajectory;
    }

    public AddressItemView(Context context) {
        super(context);
    }

    public AddressItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public int setViewLayoutId() {
        return R.layout.addresss_item_view;
    }

    @Override
    public void setDataToView(AddressItemViewViewModel data) {
        data.setShowChb(isShowCheckBox);
    }

    /**
     * 处理短信发送状态
     * @param data
     */
    private void handlerMsgStatus(AddressItemViewViewModel data) {
        if(!TextUtils.isEmpty(data.msgStatus)) {

        }
    }

    @Override
    public void onRootClick(View view) {
        if (isSendEventBusFlag) {
        }
    }

    @Override
    public void onClick(View v) {

    }

    /**
     * 处理点击事件
     */
    private void handleClickEvent() {

    }
}