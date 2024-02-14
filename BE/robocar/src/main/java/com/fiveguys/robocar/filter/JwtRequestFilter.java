package com.fiveguys.robocar.filter;

import com.fiveguys.robocar.apiPayload.ResponseApi;
import com.fiveguys.robocar.apiPayload.ResponseStatus;
import com.fiveguys.robocar.util.JwtUtil;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class JwtRequestFilter implements Filter {
    final private List<String> whiteList = List.of("/login", "/");
    private final JwtUtil jwtUtil;
    @Autowired
    public JwtRequestFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        final String authorizationHeader = httpRequest.getHeader("Authorization");
        String requestUrl = httpRequest.getRequestURI();

        String jwt = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);

            // 유효한 토큰이 있으면 userId를 리퀘스트에 추가
            if (jwtUtil.validateToken(jwt)) {
                String userId = jwtUtil.extractUserId(jwt);
                httpRequest.setAttribute("userId", userId);
                chain.doFilter(request, response);
            }
            // 토큰이 없으나 로그인이 필요 없는 페이지인 경우
            else if(!isLoginCheckPath(requestUrl)){
                chain.doFilter(request, response);
            }
            // 그 외엔 전부 UNAUTHORIZED
            else {
                HttpServletResponse httpResponse = (HttpServletResponse) ResponseApi.of(ResponseStatus._UNAUTHORIZED);
            }


        }
    }

    private boolean isLoginCheckPath(String requestURL){
        return !whiteList.contains(requestURL);
    }
}
