package com.hansung.leafly.global.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hansung.leafly.global.response.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import com.hansung.leafly.global.auth.exception.AuthErrorCode;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    /**
     * 인증 자체가 실패했을 때(토큰 없음, 만료, 유효하지 않음 등)
     * JWT 필터에서 던져진 예외를 바탕으로 커스텀 JSON 응답 생성
     */
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

        ErrorResponse body = ErrorResponse.from(AuthErrorCode.INVALID_TOKEN);

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(body));
    }
}

