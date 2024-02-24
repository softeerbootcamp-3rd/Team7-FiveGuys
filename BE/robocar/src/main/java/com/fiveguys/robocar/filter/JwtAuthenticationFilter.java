package com.fiveguys.robocar.filter;

import com.fiveguys.robocar.auth.AuthRequestMappingScanner;
import com.fiveguys.robocar.auth.RequestMappings;
import com.fiveguys.robocar.util.JwtUtil;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.fiveguys.robocar.models.LoginConstant.*;
import static com.fiveguys.robocar.models.TokenConstant.*;


@Component
public class JwtAuthenticationFilter implements Filter {
    private final JwtUtil jwtUtil;
    private final RequestMappings unauthenticatedRequestMappings;

    @Autowired
    public JwtAuthenticationFilter(JwtUtil jwtUtil, AuthRequestMappingScanner scanner) {
        this.jwtUtil = jwtUtil;
        this.unauthenticatedRequestMappings = scanner.getCustomRequestMappings();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String authorization = jwtUtil.getAuthorization(httpRequest);
        String requestUrl = httpRequest.getRequestURI();
        String method = httpRequest.getMethod();

        if (unauthenticatedRequestMappings.contains(requestUrl, method)) {
            String jwtToken = null;
            if (authorization != null && authorization.startsWith(BEARER_TYPE)) {
                jwtToken = jwtUtil.getBearerToken(authorization);
                // 유효한 토큰이 있으면 id를 리퀘스트에 추가
                if (jwtUtil.validateToken(jwtToken)) {
                    Long id = jwtUtil.extractId(jwtToken);
                    httpRequest.setAttribute(LOGIN_USER_ID, id);
                    chain.doFilter(request, response);
                    return;
                }
            }
            // 토큰이 없거나 유효하지 않은 경우 ResponseEntity를 반환
            sendUnauthorizedResponse(response);
            return;
        }

        chain.doFilter(request, response);
    }

    public void sendUnauthorizedResponse(ServletResponse response) throws IOException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setCharacterEncoding("UTF-8"); // 인코딩 설정
        httpResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        httpResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
        String jsonResponse = "{\"message\": \"인증되지 않은 사용자입니다.\"}";
        httpResponse.getWriter().write(jsonResponse);
    }
}
