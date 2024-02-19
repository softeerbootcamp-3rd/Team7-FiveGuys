package com.fiveguys.robocar.filter;

import com.fiveguys.robocar.apiPayload.ResponseStatus;
import com.fiveguys.robocar.util.JwtUtil;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class JwtRequestFilter implements Filter {
    private final String AUTHORIZATION =  "Authorization";
    private final String BEARER = "Bearer ";
    private final String ID = "id";
    private final JwtUtil jwtUtil;
    @Autowired
    public JwtRequestFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String authorizationHeader = httpRequest.getHeader(AUTHORIZATION);
        String requestUrl = httpRequest.getRequestURI();

        String jwt = null;

        //로그인 필요한 경로
        if(isLoginCheckPath(requestUrl)){
            if (authorizationHeader != null && authorizationHeader.startsWith(BEARER)) {
                jwt = authorizationHeader.substring(BEARER.length());

                if(jwtUtil.validateToken(jwt)){
                    String id = jwtUtil.extractId(jwt);
                    httpRequest.setAttribute(ID, id);
                    chain.doFilter(request, response);
                    return;
                }

            }
            //유효한 토큰이 없는 경우
            httpResponse.setStatus(401);
            httpResponse.getWriter().write(ResponseStatus._UNAUTHORIZED.getMessage());
            httpResponse.getWriter().flush();
        }
        // 로그인이 필요 없으면 그대로 통과
        else{
            chain.doFilter(request, response);
        }

    }

    private boolean isLoginCheckPath(String requestURL){
        return false;
    }
}
