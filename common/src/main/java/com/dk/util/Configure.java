package com.dk.util;

/**微信相关参数类
 * Created by wuzu on 2017/3/19.
 */
public class Configure {
    //这个就是自己要保管好的私有Key了（切记只能放在自己的后台代码里，不能放在任何可能被看到源代码的客户端程序中）
    // 每次自己Post数据给API的时候都要用这个key来对所有字段进行签名，生成的签名会放在Sign这个字段，API收到Post数据的时候也会用同样的签名算法对Post过来的数据进行签名和验证
    // 收到API的返回的时候也要用这个key来对返回的数据算下签名，跟API的Sign数据进行比较，如果值不一致，有可能数据被第三方给篡改
    //private static String appID = "wx1a793c68a640ef22";

    //private static String appID = "wx1a793c68a640ef22";

    //微信分配的小程序(公众号)ID（企业支付商户号绑定的）
    private final static String appID = "wxccd88243d2cbd957";

    private final static  String aapScrete = "b7b7eaa14eb1616a753d1d4b3cc60902";
    //微信公众号授权回调地址
    private final static  String webRedictUrl = "xiaoaizhuan.cn/advertApp/user/webGrandAuth";
    //微信公众号授权成功页面
    private final static  String successRedictUrl = "http://xiaoaizhuan.cn/app/webgrand/success.html";

    //微信公众号授权失败页面
    private final static  String failRedictUrl = "http://xiaoaizhuan.cn/app/webgrand/error.html";

    //应用唯一标识，在微信开放平台提交应用审核通过后获得
    private static String mobleAppID = "wx7866e19630009c2e";

    //应用密钥AppSecret，在微信开放平台提交应用审核通过后获得
    private static  String mobleAapScrete = "77fb4bf250c050242c7199dcc1d1a324";

    private static  String certPath = "C:/wuzu/diankong/apiclient_cert.p12";

    //微信获取token地址
    private  static  String tokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token";

    //微信获取token地址
    private  static  String tokenUrlByBase = "https://api.weixin.qq.com/cgi-bin/token";

    //检测token是否过期
    private  static  String checkToken = "https://api.weixin.qq.com/sns/auth";

    //获取openid
    private static String getOpenIdUrl = "https://api.weixin.qq.com/sns/jscode2session";

    //获取微信用户信息
    private static String getUserInfor = "https://api.weixin.qq.com/sns/userinfo";

    //private static String getUserInfor = "https://api.weixin.qq.com/cgi-bin/user/info";

    public static String getCheckToken() {
        return checkToken;
    }

    public static void setCheckToken(String checkToken) {
        Configure.checkToken = checkToken;
    }

    public static String getAppID() {
        return appID;
    }

    public static String getAapScrete() {
        return aapScrete;
    }

    public static String getMobleAppID() {
        return mobleAppID;
    }

    public static void setMobleAppID(String mobleAppID) {
        Configure.mobleAppID = mobleAppID;
    }

    public static String getMobleAapScrete() {
        return mobleAapScrete;
    }

    public static void setMobleAapScrete(String mobleAapScrete) {
        Configure.mobleAapScrete = mobleAapScrete;
    }

    public static String getTokenUrl() {
        return tokenUrl;
    }

    public static void setTokenUrl(String tokenUrl) {
        Configure.tokenUrl = tokenUrl;
    }

    public static String getTokenUrlByBase() {
        return tokenUrlByBase;
    }

    public static void setTokenUrlByBase(String tokenUrlByBase) {
        Configure.tokenUrlByBase = tokenUrlByBase;
    }

    public static String getGetOpenIdUrl() {
        return getOpenIdUrl;
    }

    public static void setGetOpenIdUrl(String getOpenIdUrl) {
        Configure.getOpenIdUrl = getOpenIdUrl;
    }

    public static String getGetUserInfor() {
        return getUserInfor;
    }

    public static void setGetUserInfor(String getUserInfor) {
        Configure.getUserInfor = getUserInfor;
    }

    public static String getWebRedictUrl() {
        return webRedictUrl;
    }

    public static String getFailRedictUrl() {
        return failRedictUrl;
    }

    public static String getSuccessRedictUrl() {
        return successRedictUrl;
    }

    public static String getCertPath() {
        return certPath;
    }

    public static void setCertPath(String certPath) {
        Configure.certPath = certPath;
    }

}
