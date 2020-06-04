package com.dk.util;

public class Constants {

	//==============系统常量定义===================//
	/** 全局系统联接可配置文件名称 ca-server.properties */
	public static final String DEFAULT_PROPERTIES = "server.properties";
	/** 全局系统项目名称*/
	public static final String SERVER_NAME = ConfigUtils.getProperty("server" +
			".name");

	// =========================================================
	// ================redis 服务相关参数宏定义=====================
	// =========================================================
	/** 缓存redis服务器ip及port变量属性 */
	public static final String REDIS_SERVER_IP = "redis.server.ip";
	public static final String REDIS_SERVER_PORT = "redis.server.port";
	public static final String REDIS_SERVER_PASS = "redis.server.pass";

	/** redis 缓冲池相关配置 */
	public static final String REDIS_MAX_ACTIVE = "redis.pool.maxActive";
	public static final String REDIS_POOL_MAXIDLE = "redis.pool.maxIdle";
	public static final String REDIS_POOL_MAXWAIT = "redis.pool.maxWait";
	/*阿里云短信配置*/
	public static final String SMS_API_KEY = "LTAI8ReqhvZ6nORT";
	public static final String SMS_API_SCRET = "0P96BXphxKeNrSXHSVaQU3dtey2bkt";
	public static final String SMS_LTP_ID = "SMS_129763937";
	public static final int SMS_CODE_TIME = 600;


	/** 帐号入库缓存数量值 */
	public static final int EX_COIN = 1000;

	/*随机视频数量*/
	public static int bookCount = 8756;
	public static int randomCount = 8;

	//微信授权获取openId
	public static String GET_OPENID_URL = "https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=CODE&grant_type=authorization_code";
	//微信授权获取token
	public static String GET_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	//微信授权获取用户信息
	public static String GET_USER_INFO_URL = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
	//微信小程序appid
	public static String WX_APP_ID = "wx373f3bb942bd1a7e";
	//微信小程序appsecret
	public static String WX_APP_SECRET = "1985844f6730dd7155aff535c3e90f46";
	//商户appkey
	public static String WX_MUCH_SECRET = "diankongwzssdfsfsdzswerweewr8899";
	//微信商户号
	public static String WX_MUCH_ID = "1502432601";
	//微信支付回掉地址
	public static String WX_PAY_NOTIFY = "https://www.diankong.net.cn/advertApp/order/notify";
	//域名
	public static String WX_PAY_DOMAIN = "api.mch.weixin.qq.com";

	//redis存储推送消息信息前缀
	public static String PRE_PUSH = "PUSH_INFO_";

	//小程序推送相关
	//小程序推送获取token url地址
	public  static  String PUSH_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token";
	//小程序模板id
	public  static  String TEMPT_ID = "UFhJj1iPEmM34xYEfqEXVkEhRkMz9zl8K_QnPfq81j4";
	//推送消息地址
	public  static  String SEND_MESSAGE_URL = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send";
    //推送内容
	public  static  String PUSH_CONTENT = "小海马更新了视频啦，请点击进入继续观看更多精彩视频!";


}
