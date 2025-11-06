package com.hansung.leafly.domain.member.service;

import com.hansung.leafly.domain.member.entity.Member;
import com.hansung.leafly.domain.member.exception.InvalidCredentialsException;
import com.hansung.leafly.domain.member.exception.MemberAlreadyExistException;
import com.hansung.leafly.domain.member.exception.MemberErrorCode;
import com.hansung.leafly.domain.member.repository.MemberRepository;
import com.hansung.leafly.domain.member.web.dto.LoginReq;
import com.hansung.leafly.domain.member.web.dto.LoginRes;
import com.hansung.leafly.domain.member.web.dto.SignUpReq;
import com.hansung.leafly.global.auth.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void signUp(SignUpReq signUpReq) {
        if(memberRepository.existsByEmail(signUpReq.getEmail())){
            throw new MemberAlreadyExistException();
        }
        String encoded = passwordEncoder.encode(signUpReq.getPassword());
        Member member = Member.toEntity(signUpReq.getEmail(), encoded, signUpReq.getName());

        memberRepository.save(member);
    }

    @Override
    public LoginRes login(LoginReq loginReq) {
        // 아이디 확인
        Member member = memberRepository.findByEmail(loginReq.getEmail())
                .orElseThrow(InvalidCredentialsException::new);

        // 비밀번호 검증
        if (!passwordEncoder.matches(loginReq.getPassword(), member.getPassword())) {
            throw new InvalidCredentialsException();
        }

        // 토큰 생성
        String token = jwtTokenProvider.createToken(member);

        // 반환
        return new LoginRes(token);
    }
}
