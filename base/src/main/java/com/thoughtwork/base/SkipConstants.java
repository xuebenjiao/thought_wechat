package com.thoughtwork.base;

/**
 * 描述： 界面跳转的标记
 *
 * @author hbp.ping
 *
 */
public class SkipConstants {

    /** 标记登录界面跳转. */
    public static final int SIGN_SKIP_LOGIN = 0;

    /** 标记设置界面跳转. */
    public static final int SIGN_SKIP_SETTING = 1;

    /** 标记登录界面跳转. */
    public static final String SIGN_SKIP_KEY = "SignSkip";

    /** push通知跳转到抢单页面 */
    public static final int GRAB_SKIP_PUSH = 0;

    /** 主页提示跳转到抢单页面 */
    public static final int GRAB_SKIP_MAINTIP = 1;

    /** 主页提示跳转到b网抢单页面 */
    public static final int GRAB_SKIP_MAINTIPO2O = 2;

    /** 标记登录界面跳转. */
    public static final String GRAB_SKIP_KEY = "GrabSkip";

    /** 标记取件界面跳转. */
    public static final int RECEIVE_SKIP = 0;

    /** 标记电子面单寄件及打印界面收件跳转. */
    public static final int SEND_PRINT_SKIP = 1;

    /** 标记电子面单寄件及打印界面跳转. */
    public static final int SEND_PRINT_SENDER_SKIP = 2;

    /** 标记取件界面寄件跳转. */
    public static final int SEND_SENDER_SKIP = 3;

    /** 标记取件城市选择跳转. */
    public static final String RESOURCE_SKIP_KEY = "ResourceSkip";

    /** 标记收件城市选择跳转. */
    public static final int RECEIVE_SHORTCODE_SKIP = 10;

    /** 标记寄件城市选择跳转. */
    public static final int SEND_SHORTCODE_SKIP = 11;

    /** 运费查询：标记寄件城市选择跳转. */
    public static final int FREIGHT_QUERY_SENDER = 12;

    /** 运费查询：标记收件城市选择跳转. */
    public static final int FREIGHT_QUERY_RECEIVE = 13;

    /** 是否关闭扫描页面 **/
    public static final String SKIP_SIGNIN_FINISH  = "isFinishSign";

    // 二维码扫描界面的跳转

    /** 扫一扫key. */
    public static String SKIP_QRCODE = "SkipQRCode";

    /** 标记正常签收界面的跳转. */
    public static final int SKIP_NORMAL_SIGN = 0;

    /** 标记从主界面到取件界面的跳转. */
    public static final int SKIP_RECEIVE = 1;

    /** 标记查件界面的跳转. */
    public static final int SKIP_SCAN_SEARCH = 2;

    /** 标记异常签收界面的跳转. */
    public static final int SKIP_EXCEPTION_SIGN = 3;

    /** 标记取件界面的扫一扫. */
    public static final int SKIP_RECEIVE_BACK = 4;

    /** 标记签收界面的扫一扫后再回到签收界面. */
    public static final int SKIP_SIGN_CALLBACK = 5;

    /** 标记待派待取界面到取件界面. */
    public static final int SKIP_AWAIT = 6;

    /** 标记手动输入界面到取件界面. */
    public static final int SKIP_MANUAL = 7;

    /** 标记从支付页面跳转到支付扫描 */
    public static final int SKIP_ALIPAY = 8;

    /** 标记从实名制认证跳转到实名二维码扫描 */
    public static final int SKIP_REALNAME = 9;

    /** 问题件查询 */
    public static final int SKIP_SCAN_SEARCHISSUE = 10;

    /** 扫描派件 */
    public static final int SKIP_DELIVERY_SCANNER = 11;

    /** 安易递二维码扫描 */
    public static final int SKIP_REALNAMEINFO_ANYIDI = 12;

    /** 钉钉揽件码取件 */
    public static final int SKIP_RECEIVE_DINGDINGCODE = 13;

    /** 支付宝二维码扫描 */
    public static final int SKIP_REALNAMEINFO_ALIPAY = 14;

    /** 待取列表扫描搜索 */
    public static final int SKIP_SCAN_SEARCHTOGET = 15;

    /** 代收货款 */
    public static final int SKIP_COD = 16;

    /**二维码取件*/
    public static final int SKIP_RECEIVE_QRCODEPICKUP = 17;

    /** 去签收去取件--传过去ExpressNo的key */
    public static final String SIGNORTAKE_MAIL_KEY = "ExpressNo";

    /** 去取件界面存储ExpressNo的key */
    public static final String SKIP_RECEIVE_KEY = "ReceiveExpressNo";

    /** 去正常签收界面存储ExpressNo的key */
    public static final String SKIP_SIGN_KEY = "SignExpressNo";

    /** 去异常签界面存储ExpressNo的key */
    public static final String SKIP_EXCEPTION_KEY = "ExceptionExpressNo";

    /** 标记跳转到登录页面的标记key */
    public static final String SKIP_TOLOGIN_KEY = "ToLoginKey";

    /** 从绑定银行卡跳转到登录页面的标记 */
    public static final String SKIP_BINDBANK_TOLOGIN = "FromBindBank";

    // 标记取件界面电子面单是否显示
    public static final String RECEIVE_IS_SHOW = "SurfaceSingle";

    // 标记电子面单取件界面纸质面单是否显示
    public static final String ELERECEIVE_IS_SHOW = "EleSurfaceSingle";

    /** 标记显示电子面单[从去取件界面来的] */
    public static final int SHOW_STATE = 0;

    /**标记主界面取件扫描.*/
    public static final int NOSHOW_STATE = 1;



}
