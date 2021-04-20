package com.thoughtwork.base.constants;

/**
 * Time :2019/11/6
 * Author:xbj
 * Description :
 */
public class Constants {
    public static String FRAGMETN_HOME_SEARCH_FLAG = "FRAGMETN_HOME_SEARCH_FLAG";
    public static String SELECT_CITY = "SELECT_CITY";
    public static String FRAGMETN_OUTLETS_SEARCH_FLAG = "FRAGMETN_OUTLETS_SEARCH_FLAG";
    public static String FRAGMETN_STATION_SEARCH_FLAG = "FRAGMETN_STATION_SEARCH_FLAG";
    public static volatile boolean isOcrOpenFlag = false;
    public static volatile boolean isScanOcrOpenFlag = false;
    //打Release包时，是否是生产环境的标识（如果false,则是测试环境，如果是true，就是生产环境）
    public static final boolean IS_RELEASE  = false ;
    //是否是UAT环境
    public static final boolean IS_UAT  = false ;
    //定时器标识
    public static final String INTENT_ALARM_LOG = "intent_alarm";
    //请求服务端处理成功的标识，不是http请求成功的标识
    public static final int SUCCESS_CODE = 200;
    //本地存储的订单与代收到付默认的状态
    public static final int ORDER_OR_DEFAULT_STATUS = -100;

    //UAT测试环境
    public static final String UAT_URL_TEST= "http://share.uat.app.web.ng.agno.top/";
    //UAT统计测试环境
    public static final String UAT_WEBVIEW_URL_TEST = "http://share.uat.h5.web.ng.agno.top/#/";
    //UAT驾驶舱测试环境
    public static final String UAT_CONTROL_CABIN_SYS_MANAGER_WEBVIEW_URL_TEST ="http://share.uat.h5.web.ng.agno.top/cockpit/#/index";//token=
//    public static final String UAT_CONTROL_CABIN_SYS_MANAGER_WEBVIEW_URL_TEST ="http://10.130.35.118:8181/cockpit/";

    //系统管理员对应的驾驶舱地址（测试）
    public static final String CONTROL_CABIN_SYS_MANAGER_WEBVIEW_URL_TEST ="http://192.168.207.153:8181/cockpit/#/index";//?token=
    //测试统计测试环境
    public static final String WEBVIEW_URL_TEST = "http://share.test.h5.web.ng.agno.top/#/";//?token=
    //开发环境（暂时用测试地址）
    public static final  String URL_DEV = "http://share.test.app.api.ng.agno.top/";
    /*测试测试环境*///域名
    public static final  String URL_TEST = "http://share.test.app.api.ng.agno.top/";
    /*测试测试环境 域名对应的IP地址*/
    //    public static final  String URL_DEV = "http://192.168.207.186:7060/";
    //系统管理员对应的驾驶舱地址（生产）
    public static final String CONTROL_CABIN_SYS_MANAGER_WEBVIEW_URL_RELEASE ="http://yjtgp-h5.yto.net.cn/cockpit/#/index";//?token=
    //生产统计 url
    public static final String WEBVIEW_URL_RELEASE ="http://yjtgp-h5.yto.net.cn/#/";//?token=
    //生产环境 域名
    public static final String URL_RELEASE = "http://yjtgp-api.yto.net.cn/";



    /*开发测试环境*/
//    public static final  String URL_DEV = "http://192.168.207.178:7060/";
    //扫描单号验证先在测试环境调试
//    public static final  String URL_DEV = "http://192.168.207.186:7060/";

    public static final String KEEP_SCREEN_ON = "KEEP_SCREEN_ON";
    public static final String APP_DOWNLOAD_URL = "APP_DOWNLOAD_URL";
    public static final String CAR_SIGN_SCAN_RESULT_LAST_TIME = "CAR_SIGN_SCAN_RESULT";

    public static final String LAST_LOACTION_TIME = "LAST_LOACTION_TIME";
    public static final String UPDATE_DEV_SN = "UPDATE_DEV_SN";
    public static final String LATIUDE ="LATIUDE";
    public static final String CITY_CODE ="CITY_CODE";
    public static final String CITY_NAME ="CITY_NAME";
    public static final String LONGITUDE =  "LONGITUDE";

    public static final String ERROR_TYPE =  "ERROR_TYPE";
    public static final String DELIVERY_AREA_CODE =  "DELIVERY_AREA_CODE";

    //所有指派人员信息
    public static final String CHOOSE_PERSON_CODE =  "CHOOSE_PERSON_CODE";
    public static final String CHOOSE_PERSON_NAME =  "CHOOSE_PERSON_NAME";
    public static final String CHOOSE_PERSON_SHOW_NAME =  "CHOOSE_PERSON_SHOW_NAME";
    public static final String DELIVERY_AREA_CONTENT =  "DELIVERY_AREA_CONTENT";
    public static final String ERROR_EXPRESS_WAYBILL_NO =  "ERROR_EXPRESS_WAYBILL_NO";

    //选择的下一站信息
    //所有指派人员信息
    public static final String CHOOSE_NEXT_STATION_CODE =  "CHOOSE_NEXT_STATION_CODE";
    public static final String CHOOSE_NEXT_STATION_NAME =  "CHOOSE_NEXT_STATION_NAME";


    //签收扫描是否成功操作了签收操作
    public static final String EXPRESS_HANDLER_UPLOAD ="EXPRESS_HANDLER_UPLOAD";
    public static final String EXPRESS_SIGN_HANDLER_UPLOAD ="EXPRESS_SIGN_HANDLER_UPLOAD";

    public static final String ERROR_TYPE_CODE =  "ERROR_TYPE_CODE";
    public static final String ERROR_SUB_TYPE =  "ERROR_SUB_TYPE";
    //是否开启左滑删除
    public static final boolean OPEN_LEFT_DEL = false;
    public static final int NOT_FOUND = -1;
    public static final String REMEBER_PWD_TIME = "REMEBER_PWD_TIME";
    public static final String REMEBER_PWD = "REMEBER_PWD";
    public static final String JUMP_TO_HOME_SUCCESS = "JUMP_TO_HOME_SUCCESS";
    public static final String ROLE_TYPE = "ROLE_TYPE";
    public static final String ROLE_NAME = "ROLE_NAME";
    public static final String ORG_LEVEL = "ORG_LEVEL";
    public static final String IS_ASSIGN = "IS_ASSIGN";
    public static final String JOB_NUMBER = "JOB_NUMBER";
    //共配账号的网点编码
    public static final String ORG_CODE = "ORG_CODE";
    //共配账号关联的圆通账号的网点编码 用户查询发往下拉选数据
    public static final String EXPRESS_YTO_ORG_CODE = "EXPRESS_YTO_ORG_CODE";

    public static final String ORG_NAME = "ORG_NAME";
    public static final String LOGIN_PHONE = "LOGIN_PHONE";
    public static final String USER_NAME= "USER_NAME";
    public static final String LOGIN_PWD = "LOGIN_PWD";
    public static final String HEAD_URL = "HEAD_URL";
    public static final String EXPRESS_CABINET = "EXPRESS_CABINET";
    public static final String USER_TOKEN = "USER_TOKEN";
    public static final String USER_ID = "USER_ID";
    public static final String CLOSE_VOICE_SPEAK = "CLOSE_VOICE_SPEAK";

    public static final String CLOSE_MOBILE_SCAN_SWITCH = "CLOSE_MOBILE_SCAN_SWITCH";
    public static final String CLOSE_BAR_GUN_SCAN_SWITCH = "CLOSE_BAR_GUN_SCAN_SWITCH";
    public static final String IS_SHOW_HANDLER_SAN_BTN = "IS_SHOW_HANDLER_SAN_BTN";

    public static final String SIGN_TYPE_LENGTH = "SIGN_TYPE_LENGTH";


    public static final String ISFIRSTSHOW = "ISFIRSTSHOW";

    public static final int SCAN_REQUEST_CODE = 101;

    public static final int SCAN_SEARCH_REQUEST_CODE = 102;
    public static final int CAR_SIGN_REQUEST_CODE = 103;
    //添加签收模板
    public static final int ADD_CUSTOME_SIGN_TYPE = 104;
    public static final int GET_UPDATE_DELIVERY_ARAE = 105;
    public static final int UPLOAD_ERROR_INFOR_SUCCES_FROM_DELIVEIED = 106;
    public static final int CHOOSE_DELIVERY_PERSON = 107;
    public static final int START_ACTIVITY_FOR_RESULT_REQUEST_CODE = 108;
    public static final int GPS_REQUEST_CODE = 111;
    public static final int CHOOSE_NEXT_STATION = 109;
    public static final int CHOOSE_EXPRESS_STATION = 110;
    public static final String IS_NEED_ALL_ITEM = "IS_NEED_ALL_ITEM";
    public static final String ADD_CUSTOME_SIGN_TYPE_TITLE = "ADD_CUSTOME_SIGN_TYPE_TITLE";
    public static final String SCAN_SEARCH_UPLOAD_DATA  = "SCAN_SEARCH_UPLOAD_DATA";
    public static final String  IS_SCAN_ALLWAYS = "IS_SCAN_ALLWAYS";
    public static final String CURRENT_SCAN_TAB_NAME ="CURRENT_SCAN_TAB_NAME";
    public static final String GE_KOU_NAME ="GE_KOU_NAME";
    public static final String CAR_SIGN ="CAR_SIGN";
    //更新App文件夹名和名字 //与日志打印目录一致（F.AppFileName）
    public static String DownloadFolderName = "Share";
    public static String DownloadFileName = "Share";
    //登录强制跳转到密码修改界面
    public static final String FORCE_TO_UPDATE_PAGE = "1";
    public static final int TOKEN_OVERTIME = 1000;
    //首次登陆的标识码
    public static final int DEVICE_FIRST_LOING = 4021;

    public static final int SMS_BALANCE_NO_ENOUGH = 5999;

    //查询线路时间戳
    public static final String LINE_RELATE_TIME_STAMP = "LINE_RELATE_TIME_STAMP";
    //关联线路/线路频次实体中的endOrgCode
    public static final String NEXT_ORG_CODE = "NEXT_ORG_CODE";
    //线路code
    public static final String LINE_CODE = "LINE_CODE";
    //线路频次code
    public static final String LINE_FREQUENCY_CODE = "LINE_FREQUENCY_CODE";
    //线路频次code
    public static final String ANDROID_IMSI = "ANDROID_IMSI";

    //首页点击到派签总数 //点击派签标识
    public static final String HOME_CLICK_DELIVER_SIGN = "HOME_CLICK_DELIVER_SIGN";
    //    public static final String WEBVIEW_JUMP_SCAN_RECORD_PARAMS= "WEBVIEW_JUMP_SCAN_RECORD_PARAMS";
    public static final String WEBVIEW_JUMP_SCAN_PARAMS_EXPRESS_CODE= "WEBVIEW_JUMP_SCAN_PARAMS_EXPRESS_CODE";
    public static final String WEBVIEW_JUMP_SCAN_PARAMS_EXPRESS_NAME= "WEBVIEW_JUMP_SCAN_PARAMS_EXPRESS_NAME";
    public static final String WEBVIEW_JUMP_SCAN_PARAMS_DATE= "WEBVIEW_JUMP_SCAN_PARAMS_DATE";


    //工业机扫描标识
    public static final String DEVICE_SCAN = "DEVICE_SCAN";
    public static final String CAMERA_SCAN = "CAMERA_SCAN";
    //OCR识别电话发短信
    public static final String OCR_SEND_SMS = "OCR_SEND_SMS";

    //异步校验标识
    public static final String SCAN_VALIDATE_ASYNCHRONOUS = "SCAN_VALIDATE_ASYNCHRONOUS";

    public static final String IS_SKIP_CAR_SIGN = "IS_SKIP_CAR_SIGN";//是否跳过车签号
    //是否跳过车签号
    public static final String PRIVACY_POLICY_SERVICE_AGREEMENT = "PRIVACY_POLICY_SERVICE_AGREEMENT";

    public static final String MODULE_NAME = "MODULE_NAME";

    //首次进入待派件的日期
    public static final String FIRST_COME_WAIT_DELIVERY_DATE = "FIRST_COME_WAIT_DELIVERY_DATE";

    //签收
    public static final String LAST_SELECT_SIGNER ="LAST_SELECT_SIGNER";
    //把枪
    public static final String GUN_BEAN ="GUN_BEAN";

    //保存模板
    public static final String TEMPLATE_CONTENT ="TEMPLATE_CONTENT";

    //微信支付的appId
//    public static final String WEIXIN_PAY_APP_ID = "wx8aebf11a55aede8b";

    public static final String WEIXIN_PAY_APP_ID = "wxd44e833b779fdea4";
    //条码前缀
    public static final String EMP_BARCODE = "EMP_BARCODE";
    //消息入口token
    public static final String MESSAGE_TOKEN = "MESSAGE_TOKEN";
    public static final String EXPRESS_STATION_NAME = "EXPRESS_STATION_NAME";
    public static final String EXPRESS_STATION_CODE = "EXPRESS_STATION_CODE";

    //快递柜 预约界面数据
    public static final String LOCKER_EXPRESS_CABINET_APPOINT_DATA = "LOCKER_EXPRESS_CABINET_APPOINT_DATA";

    public static final String OCR_THREAD_NAME = "YTO_OCR_";


    //我的界面跳转至我的/邀请的网点列表
    public static final String ADD_OR_LOOK_EXPRESS_OUTLETS_STATION_NAME = "ADD_OR_LOOK_EXPRESS_OUTLETS_STATION_NAME";
    public static final String ACTIVITY_RESULT= "ACTIVITY_RESULT";
    public static final String CURRENT_LOCATION_CITY= "CURRENT_LOCATION_CITY";
    public static final String LOCATION_PROVINCE= "LOCATION_PROVINCE";
    public static final String LOCATION_CITY= "LOCATION_CITY";
    public static final String LOCATION_AREA= "LOCATION_AREA";


    //任务
    public static final String TASK_ID = "TASK_ID";
    public static final String ORG_ID = "ORG_ID";
    //驿站/网点ID
    public static final String OUTLETS_OR_STATION_ID = "OUTLETS_OR_STATION_ID";
    public static final String LIST_DATA = "LIST_DATA";
    public static final String MODULE_TITLE = "MODULE_TITLE";

    public static final String INTENT_DATA = "INTENT_DATA";
    //车辆/设备维护界面
    //主键
    public static final  String DETAIL_ID = "DETAIL_ID";
    //任务号
    public static final String TASKPUBLISH_ID = "TASKPUBLISH_ID";
    public static final String USER_CLICK_TAB= "USER_CLICK_TAB";
    public static final String TASK_TYPE_ID= "TASK_TYPE_ID";
    public static final String TASK_TYPE_NAME= "TASK_TYPE_NAME";
    public static final String TASK_CODE= "TASK_CODE";
    public static final String TASK_PROGRESS= "TASK_PROGRESS";
    public static final String TASK_NAME= "TASK_NAME";
    public static final String TASK_DATE= "TASK_DATE";
    public static final String STATION_FLAG= "STATION_FLAG";
    public static final String PICTURE_LIST= "PICTURE_LIST";












}
