package com.hansung.leafly.domain.member.web.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class SignUpReq {
    @NotBlank(message = "이메일을 입력해주세요.")
    @Email(message = "올바른 이메일 형식으로 작성해주세요.")
    private String email;

    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d\\S]{8,20}$", message = "영문, 숫자를 포함한 8~20자리 이내로 입력해주세요.")
    private String password;

    private String passwordCheck;

    @NotBlank(message = "이름을 입력해주세요.")
    private String name;

    @AssertTrue(message = "비밀번호가 일치하지 않습니다.")
    public boolean isPasswordMatching() {
        if (password == null || passwordCheck == null) return false;
        return password.equals(passwordCheck);
    }
}
