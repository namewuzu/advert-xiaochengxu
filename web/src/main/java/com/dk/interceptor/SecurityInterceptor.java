package com.dk.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.dk.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Origin, No-Cache, X-Requested-With, If-Modified-Since, Pragma, Last-Modified, Cache-Control, Expires, Content-Type, X-E4M-With,userId,token,Access-Control-Allow-Headers");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("XDomainRequestAllowed","1");

        String requestUri = request.getRequestURI();
        String contextPath = request.getContextPath();
        String url = requestUri.substring(contextPath.length());

        //websocket

        if (url.indexOf("/login") != -1)
        {// 如果要访问的资源不需要验证
            return true;
        }
        HttpSession session = request.getSession();
        String loginName = (String) session.getAttribute("loginName");
        if (StringUtils.isBlank(loginName))
        {// 如果没有登录或登录超时
            tologinPage(response);
            return false;
            // 本地测试时关闭登陆认证
            //return true;
        }
        return true;
    }

    /**
     * 不满足条件返回到登录页面
     * @param response
     */
    public void tologinPage(HttpServletResponse response) throws IOException {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("status", 800);
        map.put("msg", "请重新登录");
        //将实体对象转换为JSON Object转换
        JSONObject responseJSONObject = (JSONObject) JSONObject.toJSON(map);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        response.getWriter().write(responseJSONObject.toString());
    }
}
