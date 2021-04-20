package com.xbj.mode;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Index;

/**
 * Time :2019/11/11
 * Author:xbj
 * Description :
 */
@Entity(indexes = {@Index(value = "tabName, expressScanNo", unique = true)})
public class ScanResultModel {
    //设置自增长
    @Id(autoincrement = true)
    private   Long id;
    //标签名称
    private String tabName;
    /*车签号*/
    private String carSignCode;
    //工号 区分不通过的用户
    private String jobNumber;
    //    private int drawable;
    //快递公司
    private String expressName;
    //快递公司编号（本地编号 用户区分快递公司）
    // 目前存储该单号状态
    // CURRENT_PLATFORM_NOT_LOGIN(-1, "当前平台用户未登录或登录失效"),
    //NORMAL(200, "正常单"),
    //INTERCEPT(604, "通缉件"),
    //ARRIVED(627, "已做过到件"),
    //DISPATCHED(609, "已做过派件"),
    //SIGNED(612, "已做过签收"),
    //PUSH_ACTION_ERROR(-2, "pushAction指定有误")
    //单号状态orderCode 默认是-100 表示未校验
    private int orderCode;
    //提示信息
    private String orderMessage;
    //快递单号
    private String expressScanNo;
    //日期
    private String date;
    //快递公司编码
    private String expressCmpCode;
    //关联线路/线路频次实体中的endOrgCode
    private String nextOrgCode;
    //线路code
    private String lineNo;
    //线路频次code
    private String lineFrequencyNo;
    //签收时 判断该订单是否是货到付款/到付的订单 是在列表显示到付
    //默认-100
    private int codStatus;
    //代收/到付金额
    private String codCharge;
    //代收/到付 提示信息
    private String codInfo;
    //收件人地址
    private String receiveAddress;
    //收件人姓名
    private String receiveName;
    //收件人电话
    private String receivePhone;

    //是否正确获取到了电话、地址等 默认是 -100
    private int orderInforCode;


    //改地址状态1 为改地址
    public int  changeAddrStatus;
    //具体修改的地址
    public String  changeAddrMsg;

    //1为精准电联
    public int  vipStatus;

    //上传时是否弹框 1 为弹框
    public int   tipStatus;
    //弹出框内容
    public  String tipMsg;


    //freshStatus = 0 普通运单 freshStatus = 1 生鲜单
    public int   freshStatus;
    //弹出框内容
    public  String freshMsg;


    //拍照签收上传成功后的url
    public String fileUrl;

    /**
     * 预付单标识 1 为预付单
     */

    public int  prePayStatus;

    /**
     * 预付单信息
     */
    public String  prePayMsg;

    /**
     * 短信发送状态
     */
    public String  smsStatus;


    @Generated(hash = 1306428131)
    public ScanResultModel(Long id, String tabName, String carSignCode, String jobNumber, String expressName, int orderCode,
            String orderMessage, String expressScanNo, String date, String expressCmpCode, String nextOrgCode, String lineNo,
            String lineFrequencyNo, int codStatus, String codCharge, String codInfo, String receiveAddress, String receiveName,
            String receivePhone, int orderInforCode, int changeAddrStatus, String changeAddrMsg, int vipStatus, int tipStatus,
            String tipMsg, int freshStatus, String freshMsg, String fileUrl, int prePayStatus, String prePayMsg, String smsStatus) {
        this.id = id;
        this.tabName = tabName;
        this.carSignCode = carSignCode;
        this.jobNumber = jobNumber;
        this.expressName = expressName;
        this.orderCode = orderCode;
        this.orderMessage = orderMessage;
        this.expressScanNo = expressScanNo;
        this.date = date;
        this.expressCmpCode = expressCmpCode;
        this.nextOrgCode = nextOrgCode;
        this.lineNo = lineNo;
        this.lineFrequencyNo = lineFrequencyNo;
        this.codStatus = codStatus;
        this.codCharge = codCharge;
        this.codInfo = codInfo;
        this.receiveAddress = receiveAddress;
        this.receiveName = receiveName;
        this.receivePhone = receivePhone;
        this.orderInforCode = orderInforCode;
        this.changeAddrStatus = changeAddrStatus;
        this.changeAddrMsg = changeAddrMsg;
        this.vipStatus = vipStatus;
        this.tipStatus = tipStatus;
        this.tipMsg = tipMsg;
        this.freshStatus = freshStatus;
        this.freshMsg = freshMsg;
        this.fileUrl = fileUrl;
        this.prePayStatus = prePayStatus;
        this.prePayMsg = prePayMsg;
        this.smsStatus = smsStatus;
    }
    @Generated(hash = 1480509403)
    public ScanResultModel() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTabName() {
        return this.tabName;
    }
    public void setTabName(String tabName) {
        this.tabName = tabName;
    }
    public String getExpressName() {
        return this.expressName;
    }
    public void setExpressName(String expressName) {
        this.expressName = expressName;
    }
    public String getExpressScanNo() {
        return this.expressScanNo;
    }
    public void setExpressScanNo(String expressScanNo) {
        this.expressScanNo = expressScanNo;
    }
    public String getJobNumber() {
        return this.jobNumber;
    }
    public void setJobNumber(String jobNumber) {
        this.jobNumber = jobNumber;
    }
    public String getCarSignCode() {
        return this.carSignCode;
    }
    public void setCarSignCode(String carSignCode) {
        this.carSignCode = carSignCode;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getExpressCmpCode() {
        return this.expressCmpCode;
    }

    public void setExpressCmpCode(String expressCmpCode) {
        this.expressCmpCode = expressCmpCode;
    }

    public String getNextOrgCode() {
        return this.nextOrgCode;
    }

    public void setNextOrgCode(String nextOrgCode) {
        this.nextOrgCode = nextOrgCode;
    }

    public String getLineNo() {
        return this.lineNo;
    }

    public void setLineNo(String lineNo) {
        this.lineNo = lineNo;
    }

    public String getLineFrequencyNo() {
        return this.lineFrequencyNo;
    }

    public void setLineFrequencyNo(String lineFrequencyNo) {
        this.lineFrequencyNo = lineFrequencyNo;
    }

    public int getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(int orderCode) {
        this.orderCode = orderCode;
    }

    public String getOrderMessage() {
        return orderMessage;
    }

    public void setOrderMessage(String orderMessage) {
        this.orderMessage = orderMessage;
    }

    public int getCodStatus() {
        return codStatus;
    }

    public void setCodStatus(int codStatus) {
        this.codStatus = codStatus;
    }

    public String getReceiveAddress() {
        return this.receiveAddress;
    }

    public void setReceiveAddress(String receiveAddress) {
        this.receiveAddress = receiveAddress;
    }

    public String getReceiveName() {
        return this.receiveName;
    }

    public void setReceiveName(String receiveName) {
        this.receiveName = receiveName;
    }

    public String getReceivePhone() {
        return this.receivePhone;
    }

    public void setReceivePhone(String receivePhone) {
        this.receivePhone = receivePhone;
    }

    public String getCodCharge() {
        return this.codCharge;
    }

    public void setCodCharge(String codCharge) {
        this.codCharge = codCharge;
    }

    public String getCodInfo() {
        return this.codInfo;
    }

    public void setCodInfo(String codInfo) {
        this.codInfo = codInfo;
    }

    public int getOrderInforCode() {
        return this.orderInforCode;
    }

    public void setOrderInforCode(int orderInforCode) {
        this.orderInforCode = orderInforCode;
    }

    public int getChangeAddrStatus() {
        return this.changeAddrStatus;
    }

    public void setChangeAddrStatus(int changeAddrStatus) {
        this.changeAddrStatus = changeAddrStatus;
    }

    public String getChangeAddrMsg() {
        return this.changeAddrMsg;
    }

    public void setChangeAddrMsg(String changeAddrMsg) {
        this.changeAddrMsg = changeAddrMsg;
    }

    public int getVipStatus() {
        return this.vipStatus;
    }

    public void setVipStatus(int vipStatus) {
        this.vipStatus = vipStatus;
    }
    public int getTipStatus() {
        return this.tipStatus;
    }
    public void setTipStatus(int tipStatus) {
        this.tipStatus = tipStatus;
    }
    public String getTipMsg() {
        return this.tipMsg;
    }
    public void setTipMsg(String tipMsg) {
        this.tipMsg = tipMsg;
    }
    public int getFreshStatus() {
        return this.freshStatus;
    }
    public void setFreshStatus(int freshStatus) {
        this.freshStatus = freshStatus;
    }
    public String getFreshMsg() {
        return this.freshMsg;
    }
    public void setFreshMsg(String freshMsg) {
        this.freshMsg = freshMsg;
    }
    public String getFileUrl() {
        return this.fileUrl;
    }
    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }
    public int getPrePayStatus() {
        return this.prePayStatus;
    }
    public void setPrePayStatus(int prePayStatus) {
        this.prePayStatus = prePayStatus;
    }
    public String getPrePayMsg() {
        return this.prePayMsg;
    }
    public void setPrePayMsg(String prePayMsg) {
        this.prePayMsg = prePayMsg;
    }
    public String getSmsStatus() {
        return this.smsStatus;
    }
    public void setSmsStatus(String smsStatus) {
        this.smsStatus = smsStatus;
    }
}
