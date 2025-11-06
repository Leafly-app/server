package com.hansung.leafly.global.auth.jwt;

import com.hansung.leafly.global.exception.BaseException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        try {
            String token = extractToken(request);

            // 토큰이 존재할 경우에만 검증 시도
            if (token != null && !token.isBlank()) {
                // JwtTokenProvider 내부에서 BaseException (Expired, Invalid, Missing) 발생 가능
                jwtTokenProvider.validateToken(token);

                // 인증 정보 생성 및 SecurityContext에 등록
                Authentication authentication = jwtTokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

            // 다음 필터로 이동
            filterChain.doFilter(request, response);

        } catch (BaseException e) {
            // JWT 관련 커스텀 예외(BaseException 하위 클래스)가 발생한 경우
            SecurityContextHolder.clearContext();

            // 나중에 CustomAuthenticationEntryPoint에서 다시 꺼내서 사용할 수 있도록 저장
            request.setAttribute("exception", e);

            // Spring Security가 감지 가능한 AuthenticationException으로 변환
            throw new AuthenticationServiceException(e.getMessage(), e);
        }
    }

    private String extractToken(HttpServletRequest request) {
        String bearer = request.getHeader("Authorization");
        if (bearer != null && bearer.startsWith("Bearer ")) {
            return bearer.substring(7);
        }
        return null;
    }
}
