package com.hansung.leafly.domain.member.service;

import com.hansung.leafly.domain.bookmark.entity.Bookmark;
import com.hansung.leafly.domain.library.entity.Library;
import com.hansung.leafly.domain.member.entity.Genre;
import com.hansung.leafly.domain.member.entity.Member;
import com.hansung.leafly.domain.member.entity.Onboarding;
import com.hansung.leafly.domain.member.entity.enums.GenreType;
import com.hansung.leafly.domain.member.exception.AlreadyOnboardedException;
import com.hansung.leafly.domain.member.exception.InvalidCredentialsException;
import com.hansung.leafly.domain.member.exception.MemberAlreadyExistException;
import com.hansung.leafly.domain.member.exception.MemberNotFoundException;
import com.hansung.leafly.domain.member.repository.GenreRepository;
import com.hansung.leafly.domain.member.repository.MemberRepository;
import com.hansung.leafly.domain.member.repository.OnboardingRepository;
import com.hansung.leafly.domain.member.web.dto.*;
import com.hansung.leafly.domain.member.web.dto.info.BookSimple;
import com.hansung.leafly.global.auth.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.hansung.leafly.domain.library.entity.enums.LibraryStatus;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
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
        Onboarding onboarding = Onboarding.from(member, req);

        // 선택된 장르 조회
        List<GenreType> selectedTypes = req.getFavoriteGenres();
        List<Genre> genres = genreRepository.findAllByTypeIn(selectedTypes);

        // OnboardingGenre 연관관계 추가
        for (Genre genre : genres) {
            onboarding.addGenre(genre);
        }

        onboardingRepository.save(onboarding);
    }

    @Override
    public MemberDetailsRes details(Member m) {
        Member member = memberRepository.findDetailById(m.getId())
                .orElseThrow(MemberNotFoundException::new);

        String nickName = member.getNickName();
        String profileImage = member.getImage(); // null 가능

        // 서재, 북마크 가져오기
        Set<Library> libraries = member.getLibraries();
        Set<Bookmark> bookmarks = member.getBookmarks();

        // 서재: 완독 책
        List<BookSimple> finishedBooks = libraries.stream()
                .filter(lib -> lib.getStatus() == LibraryStatus.DONE)
                .map(lib -> new BookSimple(lib.getIsbn(), lib.getCover(), lib.getTitle()))
                .toList();

        int finishedCount = finishedBooks.size();

        // 서재: 읽고 싶음 책
        List<BookSimple> wantBooks = libraries.stream()
                .filter(lib -> lib.getStatus() == LibraryStatus.WANT_TO_READ)
                .map(lib -> new BookSimple(lib.getIsbn(), lib.getCover(), lib.getTitle()))
                .toList();

        int wantCount = wantBooks.size();

        // 좋아요 LIST
        List<BookSimple> likeBooks = bookmarks.stream()
                .map(bm -> new BookSimple(String.valueOf(bm.getIsbn()), bm.getCover(),  bm.getTitle()))
                .toList();

        int likeCount = likeBooks.size();

        return MemberDetailsRes.of(
                nickName,
                profileImage,
                finishedCount,
                finishedBooks,
                wantCount,
                wantBooks,
                likeCount,
                likeBooks
        );
    }
}
