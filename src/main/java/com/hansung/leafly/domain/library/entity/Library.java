package com.hansung.leafly.domain.library.entity;

import com.hansung.leafly.domain.bookmark.entity.Bookmark;
import com.hansung.leafly.domain.bookmark.web.dto.BookmarkReq;
import com.hansung.leafly.domain.library.entity.enums.LibraryStatus;
import com.hansung.leafly.domain.library.web.dto.LibraryReq;
import com.hansung.leafly.domain.member.entity.Member;
import com.hansung.leafly.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "BOOKLIBRARY")
public class Library extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="booklibrary_id")
    private Long id;

    @Column(nullable = false)
    private String isbn;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String cover;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LibraryStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    public static Library of(Member member, String isbn, LibraryReq req) {
        return Library.builder()
                .member(member)
                .isbn(isbn)
                .title(req.getTitle())
                .author(req.getAuthor())
                .cover(req.getCover())
                .status(req.getStatus())
                .build();
    }
}
