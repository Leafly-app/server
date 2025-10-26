package com.hansung.leafly.domain.member.service;

import com.hansung.leafly.domain.member.entity.Member;
import com.hansung.leafly.domain.member.exception.MemberAlreadyExistException;
import com.hansung.leafly.domain.member.exception.MemberErrorCode;
import com.hansung.leafly.domain.member.repository.MemberRepository;
import com.hansung.leafly.domain.member.web.dto.SignUpReq;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void signUp(SignUpReq signUpReq) {
        if(memberRepository.existsByEmail(signUpReq.getEmail())){
            throw new MemberAlreadyExistException();
        }
        String encoded = passwordEncoder.encode(signUpReq.getPassword());
        Member member = Member.toEntity(signUpReq.getEmail(), encoded, signUpReq.getName());

        memberRepository.save(member);
    }
}
