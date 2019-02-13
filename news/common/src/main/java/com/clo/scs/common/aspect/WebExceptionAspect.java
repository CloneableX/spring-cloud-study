package com.clo.scs.common.aspect;

import com.alibaba.fastjson.JSON;
import com.clo.scs.common.domain.result.Code;
import com.clo.scs.common.domain.result.SingleResult;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Aspect
@Component
public class WebExceptionAspect {
    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    private void webPointcut() {}

    @AfterThrowing(pointcut = "webPointcut()", throwing = "e")
    public void handleThrowing(Exception e) {
        System.err.println("发现异常:" + e.getMessage());
        System.err.println(JSON.toJSONString(e.getStackTrace()));

        try {
            if(StringUtils.isBlank(e.getMessage())) {
                writeContent(JSON.toJSONString(SingleResult.buildFailure()));
            } else {
                writeContent(JSON.toJSONString(SingleResult.buildFailure(Code.Error, e.getMessage())));
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    private void writeContent(String content) throws IOException {
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        response.reset();
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-Type", "text/plain;charset=utf-8");
        response.setHeader("icop-content-type", "exception");
        PrintWriter writer = response.getWriter();
        writer.print(content);
        writer.close();
    }
}
