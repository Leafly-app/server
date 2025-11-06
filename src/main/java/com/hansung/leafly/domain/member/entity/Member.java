package com.hansung.leafly.domain.member.entity;

import com.hansung.leafly.domain.member.entity.enums.MemberRole;
import com.hansung.leafly.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
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

    @Enumerated(EnumType.STRING)
    private MemberRole role;

    public static Member toEntity(String email, String encoded, String nickName){
        return Member.builder()
                .email(email)
                .password(encoded)
                .nickName(nickName)
                .role(MemberRole.USER)
                .build();
    }
}
