package com.dk.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.dk.util.RedisPoolUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 权限拦截器
 */

@SuppressWarnings("ALL")
public class SecurityInterceptor implements HandlerInterceptor
{
    private static final Logger logger = LoggerFactory.getLogger(SecurityInterceptor.class);

    private List<String> excludeUrls;// 不需要拦截的资源

    public List<String> getExcludeUrls()
    {
        return excludeUrls;
    }

    public void setExcludeUrls(List<String> excludeUrls)
    {
        this.excludeUrls = excludeUrls;
    }

    /**
     * 完成页面的render后调用
     */
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object object,
                                Exception exception) throws Exception
    {

    }

    /**
     * 在调用controller具体方法后拦截
     */
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object object,
                           ModelAndView modelAndView) throws Exception
    {

    }

    /**
     * 在调用controller具体方法前拦截
     */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception
    {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
        response.setHeader("Access-Control-Allow-Credentials", "true");

        String requestUri = request.getRequestURI();
        String contextPath = request.getContextPath();
        String url = requestUri.substring(contextPath.length());

        if (url.indexOf("/qq/test") != -1)
        {//qq浏览器屏蔽设置
            return true;
        }

        if (url.indexOf("/pages/index/index") != -1)
        {// 扫码跳转小程序首页
            return true;
        }

        if (url.indexOf("/registerUser") != -1)
        {// 如果要访问的资源是不需要验证的
            return true;
        }

        if (url.indexOf("/sendCode") != -1)
        {// 如果要访问的资源是不需要验证的
            logger.info("进入发送验证码..............");
            return true;
        }


        //websocket
        if (url.indexOf("/wss") != -1)
        {// 如果要访问的资源是不需要验证的
            return true;
        }

        if (url.indexOf("/login") != -1)
        {// 如果要访问的资源是不需要验证的
            return true;
        }

        String userId = request.getParameter("uid");
        String token = request.getParameter("token");
        if(userId==null){
            tologinPage(response);
            return false;
        }

        if (token == null)
        {// 如果没有登录或登录超时
            logger.info(url + ">>>>>>>>>>>>>>>>>>>>");
            tologinPage(response);
            return false;
        }else{
            String redisToken = RedisPoolUtil.getString("userId"+userId);
            //redis 中token 为空返回登录页面
            if(redisToken==null){
                tologinPage(response);
                return false;
            }
            //redis中保存的token和用户提交的token不相等返回等于页面
            if(!redisToken.equals(token)){
                tologinPage(response);
                return false;
            }
        }
        return true;
    }

    /**
     * 不满足条件返回到登录页面
     * @param response
     */
    public void tologinPage(HttpServletResponse response) throws IOException {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("status", "008");
        map.put("msg", "请重新登录");
        //将实体对象转换为JSON Object转换
        JSONObject responseJSONObject = (JSONObject) JSONObject.toJSON(map);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        response.getWriter().write(responseJSONObject.toString());
    }
}
