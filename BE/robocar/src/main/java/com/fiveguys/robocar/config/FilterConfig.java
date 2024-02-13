package com.fiveguys.robocar.config;

import com.fiveguys.robocar.filter.JwtRequestFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration

public class FilterConfig {
    @Bean
    public FilterRegistrationBean<JwtRequestFilter> jwtRequestFilter() {
        FilterRegistrationBean<JwtRequestFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new JwtRequestFilter());
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }

}
