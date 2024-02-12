package com.fiveguys.robocar.filter;

import com.fiveguys.robocar.util.JwtUtil;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;

public class JwtRequestFilter implements Filter {
    private JwtUtil jwtUtil = new JwtUtil();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        final String authorizationHeader = httpRequest.getHeader("Authorization");

        String jwt = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
        }

        String loginId = null;
        //TODO
        //userId를 어떻게 전달해줄껀지 고민해봐야함
        if (jwt != null && jwtUtil.validateToken(jwt)) {
            loginId = jwtUtil.extractLoginId(jwt);
        }

        chain.doFilter(request,response);

    }
}
