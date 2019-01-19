package com.clo.scs.aspect;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.ThrowsAdvice;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import sun.rmi.runtime.Log;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Aspect
@Component
public class WebExceptionAspect implements ThrowsAdvice {
    private void webPointcut() {}

    @AfterThrowing(pointcut = "execution(* com.clo.scs.controller.*.*(..))", throwing = "e")
    public void afterThrowing(Exception e) {
        System.err.println("Exception出现");
        if(!StringUtils.isEmpty(e.getMessage())) {
            writeContent(e.getMessage());
        } else {
            writeContent("参数错误！");
        }
    }

    private void writeContent(String errorMsg) {
        HttpServletResponse response = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getResponse();
        response.reset();
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "text/plain;charset=UTF-8");
        response.setHeader("icop-content-type", "exception");

        PrintWriter writer = null;
        try {
            writer = response.getWriter();
            writer.print(StringUtils.isEmpty(errorMsg) ? "" : errorMsg);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
