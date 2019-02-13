package com.clo.scs.common.aspect;

import com.clo.scs.common.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class ApiInterceptor implements HandlerInterceptor {
    @Value("${api.encrypt.key}")
    private String key;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
        // 统一request和response格式
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "application/json;charset=utf-8");
        // 构建签名
        StringBuilder urlBuilder = getUrlAuthenticationApi(request);
        String sign = MD5Utils.md5(urlBuilder.toString(), key);
        String signature = request.getHeader("signature");
        // 比对签名
        if(sign.equals(signature)) {return true;}
        response.getWriter().print("签名错误");
        response.getWriter().close();
        return false;
    }

    private StringBuilder getUrlAuthenticationApi(HttpServletRequest request) {
        // 获取parameters名称 token timestamp
        List<String> paramNameList = new ArrayList<String>();
        paramNameList.add("token");
        paramNameList.add("timestamp");
        // 获取所有参数名称
        Enumeration<String> paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            paramNameList.add(paramNames.nextElement());
        }
        // 排序参数遍历获取参数值
        StringBuilder stringBuilder = new StringBuilder();
        paramNameList.stream().sorted().forEach(name -> {
            if("token".equals(name) || "timestamp".equals(name)) {
                if("token".equals(name) && request.getHeader(name) == null) {return;}
                stringBuilder.append("&");
                stringBuilder.append(name).append("=").append(request.getHeader(name));
            } else {
                stringBuilder.append("&");
                stringBuilder.append(name).append("=").append(request.getParameter(name));
            }
        });

        stringBuilder.deleteCharAt(0);
        System.err.println(stringBuilder.toString());
        return stringBuilder;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
