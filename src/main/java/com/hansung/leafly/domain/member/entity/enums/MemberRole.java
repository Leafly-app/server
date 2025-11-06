package com.hansung.leafly.domain.member.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MemberRole {
    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN");

    @Getter
    private final String memberRole;

    public String getStringRole() {
        return memberRole;
    }
}
