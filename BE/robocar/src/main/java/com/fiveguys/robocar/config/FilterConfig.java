package com.fiveguys.robocar.config;

import com.fiveguys.robocar.filter.JwtRequestFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration

public class FilterConfig {
    @Bean
    // 필터 JwtRequestFilter 를 FilterRegistrationBean 으로 등록
    public FilterRegistrationBean<JwtRequestFilter> jwtRequestFilter() {

        // 필터를 SpringBoot 어플리케이션으로 등록
        FilterRegistrationBean<JwtRequestFilter> registrationBean = new FilterRegistrationBean<>();

        // 등록된 필터에 JwtRequestFilter 인스턴스를 설정
        registrationBean.setFilter(new JwtRequestFilter());

        //TODO
        // 특정 경로들은 토큰검사를 수행해야하지 않아야 함
        // 현재는 모든 요청이 이 필터를 거치도록 설정되어 있음
        registrationBean.addUrlPatterns("/*");

        return registrationBean;
    }

}
