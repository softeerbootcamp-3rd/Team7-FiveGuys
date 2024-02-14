package com.fiveguys.robocar.config;

import com.fiveguys.robocar.filter.JwtRequestFilter;
import com.fiveguys.robocar.util.JwtUtil;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {
    private final JwtUtil jwtUtil;

    public FilterConfig(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }
    @Bean
    // 필터 JwtRequestFilter 를 FilterRegistrationBean 으로 등록
    public FilterRegistrationBean<JwtRequestFilter> jwtFilter() {

        // 필터를 SpringBoot 어플리케이션으로 등록
        FilterRegistrationBean<JwtRequestFilter> registrationBean = new FilterRegistrationBean<>();

        // 등록된 필터에 JwtRequestFilter 인스턴스를 설정
        registrationBean.setFilter(new JwtRequestFilter(jwtUtil));

        registrationBean.addUrlPatterns("/*");

        return registrationBean;
    }

}
