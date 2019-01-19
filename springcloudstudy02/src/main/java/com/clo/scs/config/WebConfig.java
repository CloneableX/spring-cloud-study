package com.clo.scs.config;

import com.clo.scs.entity.RemoteConn;
import com.clo.scs.interceptor.ApiInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@SpringBootConfiguration
public class WebConfig extends WebMvcConfigurationSupport {
    @Value("${userDefined.testValue.ip}")
    String ip;

    @Value("${userDefined.testValue.port}")
    String port;

    @Bean
    public RemoteConn getRemoteConn() {
        return RemoteConn.options()
                    .setIp(ip)
                    .setPort(port)
                    .build();
    }

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        super.addInterceptors(registry);
        registry.addInterceptor(new ApiInterceptor());
    }
}
