package com.hansung.leafly.global.auth.jwt;

import com.hansung.leafly.domain.member.entity.Member;
import com.hansung.leafly.domain.member.repository.MemberRepository;
import com.hansung.leafly.global.auth.exception.ExpiredAccessTokenException;
import com.hansung.leafly.global.auth.exception.InvalidTokenException;
import com.hansung.leafly.global.auth.security.CustomMemberDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import com.hansung.leafly.domain.member.exception.MemberNotFoundException;


import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {
    private final MemberRepository memberRepository;

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long expiration;

    private Key key;

    @PostConstruct
    public void init() {this.key = Keys.hmacShaKeyFor(secretKey.getBytes()); }

    public String createToken(Member member) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + expiration);

        return Jwts.builder()
                .subject(member.getEmail())
                .claim("name", member.getNickName())
                .claim("role", member.getRole())
                .issuedAt(now)
                .expiration(expiry)
                .signWith(key)
                .compact();
    }

    // 토큰 유효성 검사 + 만료 검사
    public boolean validateToken(String token) {
        try{
            getClaims(token);
            return true;
        }catch (ExpiredJwtException e) { // 토큰이 만료되었을 경우 발생
            throw new ExpiredAccessTokenException();
        } catch (JwtException e) { // 기타 토큰 오류 시 발생
            throw new InvalidTokenException();
        }
    }

    // Claim 파싱
    public Claims getClaims(String token) {
        try {
            return Jwts.parser()
                    .verifyWith((SecretKey) key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (ExpiredJwtException e) {
            throw new ExpiredAccessTokenException();
        } catch (JwtException | IllegalArgumentException e) {
            throw new InvalidTokenException();
        }
    }

    public Authentication getAuthentication(String token) {
        Claims claims = getClaims(token);
        String email = claims.getSubject();

        Member member = memberRepository.findByEmail(email)
                .orElseThrow(MemberNotFoundException::new);

        CustomMemberDetails memberDetails = new CustomMemberDetails(member);

        return new UsernamePasswordAuthenticationToken(
                memberDetails,
                null,
                memberDetails.getAuthorities()
        );
    }
}
