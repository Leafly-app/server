package com.hansung.leafly.domain.bookmark.entity;

import com.hansung.leafly.domain.bookmark.web.dto.BookmarkReq;
import com.hansung.leafly.domain.member.entity.Member;
import com.hansung.leafly.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "BOOKMARKS")
public class Bookmark extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookmark_id")
    private Long id;

    @Column(nullable = false)
    private Long isbn;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String cover;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    public static Bookmark of(Member member,Long isbn, BookmarkReq req) {
        return Bookmark.builder()
                .member(member)
                .isbn(isbn)
                .title(req.getTitle())
                .author(req.getAuthor())
                .cover(req.getCover())
                .build();
    }
}
