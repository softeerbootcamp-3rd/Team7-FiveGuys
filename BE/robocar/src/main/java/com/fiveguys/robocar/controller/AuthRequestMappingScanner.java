package com.fiveguys.robocar.controller;

import com.fiveguys.robocar.controller.annotation.Auth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.util.pattern.PathPattern;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

@Component
public class AuthRequestMappingScanner {

    @Autowired
    private RequestMappingHandlerMapping requestMappingHandlerMapping;

    public List<String> getAuthAnnotatedUrls() {
        List<String> urls = new ArrayList<>();
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = requestMappingHandlerMapping.getHandlerMethods();

        for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : handlerMethods.entrySet()) {
            Method method = entry.getValue().getMethod();
            for (Parameter parameter : method.getParameters()) {
                if (parameter.isAnnotationPresent(Auth.class)) {
                    Set<PathPattern> patterns = entry.getKey().getPathPatternsCondition().getPatterns();
                    for (PathPattern pattern : patterns) {
                        urls.add(pattern.getPatternString());
                    }
                    break;
                }
            }

        }

        return urls;
    }
}
