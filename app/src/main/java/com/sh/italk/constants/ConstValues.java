package com.sh.italk.constants;

/**
 * Created by Administrator on 2017/9/1.
 */

public class ConstValues {

    /**
     * 文件夹父路径
     */
    public static final String FILE_ROOT_DIRECTORY = "/CashLoan";
    public static final String FILE_DIRECTORY_IMG = FILE_ROOT_DIRECTORY+"/img";

    /**sharedpreference 判断是否已登录字段*/
    public static final String ISLOGIN = "islogin";
    public static final String USERID = "user_id";
    public static final String TOKEN = "token";
    public static final String USER_PHONE = "user_phone";
    public static final String USER_PWD = "user_pwd";
    public static final String USER_NAME = "user_name";
    public static final String USER_IDCARD = "user_idcard";
    public static final String USER_LATEST_LOGINTIME = "user_latest_logintime";
    public static final String USER_AUTHEN_STATUS = "user_status";
    public static final String USER_DEFAULT_BANKCARDNO = "user_default_card_no";
    public static final String USER_CREATETIME = "user_createtime";
    public static final String SERVICE_WEIXIN = "service_weixin";
    public static final String SERVICE_QQ = "service_qq";
    public static final String SERVICE_PHONE = "service_phone";
    //魔蝎 apikey
    public static final String MOXIE_API_KEY ="moxie_api_key";
    public static final String SERVICE_PUHONENUM = "";

    /**
     * 服务器后台地址
     */
    public static final String BASE_BASE_URL = "http://wwd.yqs0571.com/wwdapp/";
    public static final String BASE_URL = BASE_BASE_URL+"api/Send/";
    public static final String ACCOUNT_URL = BASE_BASE_URL+"api/Account/";
    public static final String USER_URL = BASE_BASE_URL+"api/User/";
    public static final String LOAN_URL = BASE_BASE_URL+"api/Loan/";
    public static final String MESAGE_URL = BASE_BASE_URL+"api/Send/";
    public static final String BANNER_IMG = BASE_BASE_URL+"front/app/";
    public static final String NOTICE_URL = BASE_BASE_URL+"front/app/";
    public static final String IMG_URL = BASE_BASE_URL + "";
    public static final String IMG_VERIFY = BASE_BASE_URL + "Api/imgCode?phone=";

    //极验
    public static final String STARTCAPTCHA_URL = "http://wwd.yqs0571.com/wwdapp/api/init";
    public static final String SENDVERIFY_URL = "http://wwd.yqs0571.com/wwdapp/api/init1";
    /**
     * 登录界面图形验证码获取url
     */
    public static final String IMG_CHECKNUM_URL = BASE_BASE_URL + "api/imgCode?phone=";

    /**
     * 注册协议
     */
    public static final String PROTOCOL_REGIST = BASE_BASE_URL+"api/zcxy";


    //bugly app key
    public static final String BUGLY_KEY = "cbf09a1170";
    //白骑士 密钥
    public static final String BAIQISHI_VERIFYKEY = "";//生产环境eaa98f3aeb0946f18502d88ca64d035f   测试环境c78d21810d61400b8e7ddea0b63a30e8
    //白骑士 商户号
    public static final String BAIQISHI_PARTNERID = "";
    //白骑士运营商接入url
    public static final String BAIQISHI_OPERATEAUTHEN_URL ="";

    //连连还款回调
    public static final String LIANLIANPAY_NOTIFY_URL_REPAY = ConstValues.LOAN_URL+"asynRechargeInfo_hk";
    //连连续借支付回调
    public static final String LIANLIANPAY_NOTIFY_URL_RELOAN = ConstValues.LOAN_URL+"asynRechargeInfo_xj";
    //连连逾期支付回调
    public static final String LIANLIANPAY_NOTIFY_URL_OVERDUE = ConstValues.LOAN_URL+"asynoverdueInfo_xj";
    //连连商户编号
    public static final String LIANLIANPAY_PARTNER = "";
    //连连商户（RSA）私钥
    public static final String LIANLIANPAY_RSA_PRIVATE = "";
    //有盾商户pub_key ： 开户时通过邮件发送给商户
    public static final String YOUDUN_AUTHKEY = "4d813b95-9ed8-42e7-84c7-45270c937003";
    //有盾异步通知接口地址(非必传)
    public static final String YOUDUN_URLNOTIFY = null;
    //富友支付key
    public static final String FUYOU_KEY = "52dgaory837pu9yhegdsgtzl9tgk98pd";
    //富友支付商户号
    public static final String FUYOU_ID = "0003320F1415894";
    //富有还款回调
    public static final String FUYOU_NOTIFY_URL_REPAY = BASE_BASE_URL+ "api/fy_hk";
    //富有续借回调
    public static final String FUYOU_NOTIFY_URL_RELOAN = BASE_BASE_URL+ "api/fy_xj";
    //富有延期回调
    public static final String FUYOU_NOTIFY_URL_EXTENSION = BASE_BASE_URL+ "api/fy_yq";
    //聚合支付key
    public static final String JUHEPAY_KEY = "aluNjq62zw5zy87FpCY4Y9OVgvDIiUVJ";
    //聚合支付商户号
    public static final String JUHEPAY_MERID = "yft2018092700003";
    //聚合支付回调地址还款
    public static final String JUHE_PAY_NOTIFYURL_hk = BASE_BASE_URL + "api/zfb_hk";
    //聚合支付回调地址续借
    public static final String JUHE_PAY_NOTIFYURL_XJ = BASE_BASE_URL + "api/zfb_xj";
    //聚合支付回调地址延期
    public static final String JUHE_PAY_NOTIFYURL_YANQI = BASE_BASE_URL + "api/zfb_yq";
    //快捷支付回调地址还款
    public static final String JUHE_QUICK_PAY_NOTIFYURL_hk = BASE_BASE_URL + "api/zfb_hk2";
    //快捷支付回调地址续借
    public static final String JUHE_QUICK_PAY_NOTIFYURL_XJ = BASE_BASE_URL + "api/zfb_xj2";
    //快捷支付回调地址延期
    public static final String JUHE_QUICK_PAY_NOTIFYURL_YANQI = BASE_BASE_URL + "api/zfb_yq2";

    //阿里热更新 appid应用号  appkey应用密钥  appscreat私钥
    public static final String SOPHIX_APPID = "";
    public static final String SOPHIX_APPKEY = "";
    public static final String SOPHIX_APPSCREAT = "";

    public static final int SOPHIX_VERSION = 0;

    //fir.im id
    public static final String FIR_ID = "5baee9fb959d693cfc026a8f";
    //fir.im apitoken
    public static final String FIR_API_TOKEN = "8a7518bcab60cedb183d62f1e4f8ae78";
}
