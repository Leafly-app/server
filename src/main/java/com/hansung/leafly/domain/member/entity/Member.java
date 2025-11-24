package com.hansung.leafly.domain.member.entity;

import com.hansung.leafly.domain.bookmark.entity.Bookmark;
import com.hansung.leafly.domain.bookreview.entity.ReviewImage;
import com.hansung.leafly.domain.library.entity.Library;
import com.hansung.leafly.domain.member.entity.enums.MemberRole;
import com.hansung.leafly.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "MEMBERS")
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, name = "nick_name")
    private String nickName;

    private String image;

    @Enumerated(EnumType.STRING)
    private MemberRole role;

    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Onboarding onboarding;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Bookmark> bookmarks = new HashSet<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Library> libraries = new HashSet<>();

    public static Member toEntity(String email, String encoded, String nickName){
        return Member.builder()
                .email(email)
                .password(encoded)
                .nickName(nickName)
                .role(MemberRole.USER)
                .build();
    }
}
