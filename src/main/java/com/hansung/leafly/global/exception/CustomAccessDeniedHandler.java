package com.hansung.leafly.global.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hansung.leafly.global.response.BaseResponse;
import com.hansung.leafly.global.response.code.ErrorResponseCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper; // 혹은 빈 주입 가능

    /**
     * 인증은 되었지만, 인가가 부족한 요청이 들어올 때 처리하는 핸들러
     */
    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {

        response.setStatus(ErrorResponseCode.ACCESS_DENIED_REQUEST.getHttpStatus());
        response.setContentType("application/json;charset=UTF-8");

        BaseResponse body = BaseResponse.of(false, ErrorResponseCode.ACCESS_DENIED_REQUEST);
        response.getWriter().write(objectMapper.writeValueAsString(body));
    }
}
