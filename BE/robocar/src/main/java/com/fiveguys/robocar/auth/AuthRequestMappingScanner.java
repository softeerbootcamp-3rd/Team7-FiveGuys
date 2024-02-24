package com.fiveguys.robocar.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
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

    public RequestMappings getCustomRequestMappings() {
        RequestMappings requestMappings = new RequestMappings();

        Map<RequestMappingInfo, HandlerMethod> handlerMethods = requestMappingHandlerMapping.getHandlerMethods();
        for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : handlerMethods.entrySet()) {
            Method method = entry.getValue().getMethod();
            for (Parameter parameter : method.getParameters()) {
                if (parameter.isAnnotationPresent(Auth.class)) {
                    RequestMappingInfo requestMappingInfo = entry.getKey();

                    Set<PathPattern> patterns = requestMappingInfo.getPathPatternsCondition().getPatterns();
                    Set<RequestMethod> methods = requestMappingInfo.getMethodsCondition().getMethods();
                    for (PathPattern pattern : patterns) {
                        for (RequestMethod requestMethod : methods) {
                            String url = pattern.getPatternString();
                            String httpMethod = requestMethod.name();
                            requestMappings.add(url, httpMethod);
                        }
                    }
                    break;
                }
            }

        }

        return requestMappings;
    }
}
