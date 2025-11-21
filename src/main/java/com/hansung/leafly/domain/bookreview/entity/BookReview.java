package com.hansung.leafly.domain.bookreview.entity;

import com.hansung.leafly.domain.bookreview.web.dto.ReviewReq;
import com.hansung.leafly.domain.member.entity.Member;
import com.hansung.leafly.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "BOOKREVIEWS")
public class BookReview extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookreview_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String thumbnail;

    @Column(nullable = false)
    private Integer rating;

    @Column(nullable =true, name = "review_title")
    private String reviewTitle;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @OneToMany(mappedBy = "bookReview", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BookTag> tags = new ArrayList<>();

    @OneToMany(mappedBy = "bookReview", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReviewImage> images = new ArrayList<>();

    public static BookReview toEntity(Member member, ReviewReq req) {
        return BookReview.builder()
                .member(member)
                .title(req.getTitle())
                .author(req.getAuthor())
                .thumbnail(req.getThumbnail())
                .rating(req.getRating())
                .reviewTitle(req.getReviewTitle())
                .content(req.getContent())
                .build();
    }

}
