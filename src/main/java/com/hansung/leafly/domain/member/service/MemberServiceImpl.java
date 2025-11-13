package com.hansung.leafly.domain.member.service;

import com.hansung.leafly.domain.member.entity.Genre;
import com.hansung.leafly.domain.member.entity.Member;
import com.hansung.leafly.domain.member.entity.Onboarding;
import com.hansung.leafly.domain.member.entity.enums.GenreType;
import com.hansung.leafly.domain.member.exception.AlreadyOnboardedException;
import com.hansung.leafly.domain.member.exception.InvalidCredentialsException;
import com.hansung.leafly.domain.member.exception.MemberAlreadyExistException;
import com.hansung.leafly.domain.member.exception.MemberErrorCode;
import com.hansung.leafly.domain.member.repository.GenreRepository;
import com.hansung.leafly.domain.member.repository.MemberRepository;
import com.hansung.leafly.domain.member.repository.OnboardingRepository;
import com.hansung.leafly.domain.member.web.dto.LoginReq;
import com.hansung.leafly.domain.member.web.dto.LoginRes;
import com.hansung.leafly.domain.member.web.dto.OnboardingReq;
import com.hansung.leafly.domain.member.web.dto.SignUpReq;
import com.hansung.leafly.global.auth.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final OnboardingRepository onboardingRepository;
    private final GenreRepository genreRepository;

    @Override
    @Transactional
    public void signUp(SignUpReq signUpReq) {
        if(memberRepository.existsByEmail(signUpReq.getEmail())){
            throw new MemberAlreadyExistException();
        }
        String encoded = passwordEncoder.encode(signUpReq.getPassword());
        Member member = Member.toEntity(signUpReq.getEmail(), encoded, signUpReq.getNickname());

        memberRepository.save(member);
    }

    @Override
    @Transactional
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

    @Override
    @Transactional
    public void onboarding(Member member, OnboardingReq req) {
        if (onboardingRepository.existsByMember(member)) {
            throw new AlreadyOnboardedException();
        }

        // 기본 온보딩 정보 생성
        Onboarding onboarding = Onboarding.builder()
                .member(member)
                .birthYear(req.getBirthYear())
                .gender(req.getGender())
                .readingPurpose(req.getReadingPurpose())
                .readingFrequency(req.getReadingFrequency())
                .build();

        // 선택된 장르 조회
        List<GenreType> selectedTypes = req.getFavoriteGenres();
        List<Genre> genres = genreRepository.findAllByTypeIn(selectedTypes);

        // OnboardingGenre 연관관계 추가
        for (Genre genre : genres) {
            onboarding.addGenre(genre);
        }

        // 저장
        onboardingRepository.save(onboarding);
    }
}
