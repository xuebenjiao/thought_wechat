package com.xbj.common.views.listItem;

import android.text.TextUtils;

import androidx.databinding.Bindable;

import com.xbj.common.views.databinding.PicViewModel;
import com.xbj.common.R;
import com.thoughtwork.base.constants.Constants;
import com.xbj.mode.ScanResultModel;
import com.thoughtwork.base.utils.Utils;

/**
 * Time :2020/4/20
 * Author:xbj
 * Description :
 */
public class AddressItemViewViewModel extends PicViewModel {
    //是否显示通缉件标识
    public boolean isShowIntercept = false;
    public String carSignCode;
    //是否显示右侧checkbox
    public boolean isShowChb = true;
    //用于上传后删除本地数据的
    public Long id;
    public String expressName;
    public String scanCode;
    public String dateTime;

    public boolean subCheck;
    public String nextStation;
    //快递公司对应的orgCode 只有在相机扫描界面指定快递公司时才传递
    public String expressCmpCode;
    //该单号状态
    //CURRENT_PLATFORM_NOT_LOGIN(-1, "当前平台用户未登录或登录失效"),
    //NORMAL(200, "正常单"),
    //INTERCEPT(604, "通缉件"),
    //ARRIVED(627, "已做过到件"),
    //DISPATCHED(609, "已做过派件"),
    //SIGNED(612, "已做过签收"),
    //PUSH_ACTION_ERROR(-2, "pushAction指定有误")
    public int orderStatus;
    //同步校验时提示 已签收、未派件等
    public String orderMessage;
    //展示失败列表错误原因、问题件原因、暂不支持提示信息
    public String errorMessage;

    // 到件/派件失败的标识
    public String orderErrorType;

    public  boolean isShowStatus = true;
    //收件人姓名
    public String receiveName;

    //收件人电话
    public String receivePhone;
    //是否显示打电话图标
    public boolean isShowTelFlag;
    //收件人地址
    public String receiveAddress;

    //是否另起一行顯示orderMessage(避免精準電聯、生鮮、cod同時存在時，orderMessage顯示換行導致界面畸形)
    public boolean isOtherLineToShowOrerMsg = false;
    /**
     !1 - 非到付件，1 - 到付件
     此状态为1的单子需要给用户弹窗提醒
     */
    public int codStatus;
    //代收/到付金额
    public String codCharge;
    //代收/到付 提示信息
    public String codInfo;

    //待派列表数据日期
    public String waitDeliveDate;



    //1 标识改地址
    public int changeAddrStatus;
    //具体该地址信息
    public String       changeAddrMsg;
    //1 标识精准电联
    public int vipStatus;
    public String vipNote = "精准电联";


    //上传时是否弹框 1 为弹框
    public int   tipStatus;
    //弹出框内容
    public  String tipMsg;

    //freshStatus = 0 普通运单 freshStatus = 1 生鲜单
    public int   freshStatus;
    //弹出框内容
    public  String freshMsg;


    /**
     * 预付单标识 1 为预付单
     */
    public int  prePayStatus;

    /**
     * 预付单信息
     */
    public String  prePayMsg;


    //单号渠道 散单、电商等
    public String channelOrderType;

    //短信发送状态 发送成功、发送失败、发送中、未发送
    public String msgStatus;

    //单号对应上传图片的url
    public String fileUrl;

    //关联线路/线路频次实体中的endOrgCode
    public String nextOrgCode;

    //驿站名称
    public String courierName ;
    //驿站编码
    public String courierCode ;


    public boolean isShowChb() {
        return isShowChb;
    }

    public void setShowChb(boolean showChb) {
        isShowChb = showChb;
    }

    public boolean isShowStatus() {
        return isShowStatus;
    }

    public void setShowStatus(boolean showStatus) {
        isShowStatus = showStatus;
    }

    @Bindable
    public boolean isSubCheck() {
        return subCheck;
    }

    public void setSubCheck(boolean subCheck) {
        if(this.subCheck != subCheck) {
            this.subCheck = subCheck;
            notifyPropertyChanged(com.xbj.common.BR.subCheck);
        }
    }
    public AddressItemViewViewModel(){

    }
    public AddressItemViewViewModel(ScanResultModel model){
        if(TextUtils.isEmpty(model.getExpressCmpCode())){
            this.drawable = R.drawable.img_logo;
            this.expressName = "运单号：";
        }
        else{
            this.drawable = Utils.getExpressPicFromChinaName(model.getExpressName());
            this.expressName = model.getExpressName();
        }
        this.scanCode = model.getExpressScanNo();
        this.dateTime = model.getDate();
        this.expressCmpCode = model.getExpressCmpCode();
        this.orderMessage = (model.getOrderCode() == Constants.ORDER_OR_DEFAULT_STATUS ) ?"未校验": model.getOrderMessage();
        this.orderStatus  = model.getOrderCode();
        this.codStatus = model.getCodStatus();
        this.codCharge = model.getCodCharge();
        this.codInfo = model.getCodInfo();
        this.carSignCode = model.getCarSignCode();
        //收件人信息
//        if(model.getOrderInforCode() == Constants.SUCCESS_CODE) {
        this.receivePhone = TextUtils.isEmpty(model.getReceivePhone()) ? "" : model.getReceivePhone();
        this.receiveName = TextUtils.isEmpty(model.getReceiveName()) ? "" : model.getReceiveName();
        this.receiveAddress = TextUtils.isEmpty(model.getReceiveAddress()) ? "" : model.getReceiveAddress();
//        }
        this.vipStatus = model.vipStatus;
        this.changeAddrStatus = model.changeAddrStatus;
        this.changeAddrMsg = model.changeAddrMsg;
        this.tipMsg = TextUtils.isEmpty(model.tipMsg)?"":model.tipMsg;
        this.tipStatus = model.tipStatus;
        this.freshMsg = TextUtils.isEmpty(model.freshMsg)?"":model.freshMsg;
        this.freshStatus = model.freshStatus;
        this.fileUrl = model.fileUrl;
        this.prePayStatus = model.prePayStatus;
        this.prePayMsg = model.prePayMsg;
        this.msgStatus = model.smsStatus;
    }

    public AddressItemViewViewModel(String expressName,String expressCode,String dateTime,String scanCode,String nextStation,String receiveAddress,String receiveName,String receivePhone){
        this.expressName = expressName;
        this.drawable = Utils.getExpressPicFromExpressCode(expressCode);
        this.expressCmpCode = expressCode;
        this.scanCode = scanCode;
//        this.dateTime = "日期："+dateTime;
        this.dateTime = dateTime;
        this.nextStation = TextUtils.isEmpty(nextStation)?"":"下一站网点："+nextStation;
        this.receiveAddress = receiveAddress;
        if(!TextUtils.isEmpty(receiveAddress)){
            this.receiveAddress = receiveAddress;
        }
        if(!TextUtils.isEmpty(receiveName)){
            this.receiveName  = receiveName;
        }
        if(!TextUtils.isEmpty(receivePhone)){
            this.receivePhone = receivePhone;
        }
    }
}
