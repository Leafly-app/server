package com.hansung.leafly.domain.member.web.dto;

import com.hansung.leafly.domain.member.web.dto.info.BookSimple;
import com.hansung.leafly.domain.member.web.dto.info.LibrarySection;
import com.hansung.leafly.domain.member.web.dto.info.LikeSection;

import java.util.List;

public record MemberDetailsRes (
        String nickName,
        String profileImage,
        LibrarySection library,
        LikeSection likes
){
    public static MemberDetailsRes of(
            String nickName,
            String profileImage,
            int finishedCount,
            List<BookSimple> finishedBooks,
            int wantCount,
            List<BookSimple> wantBooks,
            int likeCount,
            List<BookSimple> likeBooks
    ) {
        return new MemberDetailsRes(
                nickName,
                profileImage,
                new LibrarySection(
                        finishedCount,
                        finishedBooks,
                        wantCount,
                        wantBooks
                ),
                new LikeSection(
                        likeCount,
                        likeBooks
                )
        );
    }
}
