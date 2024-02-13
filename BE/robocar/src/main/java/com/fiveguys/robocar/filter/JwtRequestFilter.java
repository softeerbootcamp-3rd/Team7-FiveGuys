package com.fiveguys.robocar.filter;

import com.fiveguys.robocar.apiPayload.ResponseApi;
import com.fiveguys.robocar.apiPayload.ResponseStatus;
import com.fiveguys.robocar.util.JwtUtil;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class JwtRequestFilter implements Filter {


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        final String authorizationHeader = httpRequest.getHeader("Authorization");

        String jwt = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);

            try {
                // 토큰이 있으면 loginId 리퀘스트에 추가, 없으면 그대로 다음 필터/컨트롤러 넘겨줌
                if (JwtUtil.validateToken(jwt)) {
                    String loginId = JwtUtil.extractLoginId(jwt);
                    httpRequest.setAttribute("loginId", loginId);
                }
                chain.doFilter(request, response);
            // 토큰 검증 때 예외가 발생한 경우 UNAUTHORIZED
            } catch (Exception e) {
                HttpServletResponse httpResponse = (HttpServletResponse) ResponseApi.of(ResponseStatus._UNAUTHORIZED);
            }

        }

    }
}
