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

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Pattern(
            regexp = "^(?!([A-Za-z]+|\\d+|[!@#$%^&*()_+\\-={}\\[\\]:;\"'<>,.?/`~|\\\\]+)$)[A-Za-z\\d!@#$%^&*()_+\\-={}\\[\\]:;\"'<>,.?/`~|\\\\]{8,16}$",
            message = "영문 대소문자, 숫자, 특수문자 중 2가지 이상을 포함한 8~16자리여야 합니다."
    )
    private String password;

    @NotBlank(message = "비밀번호 확인을 입력해주세요.")
    private String passwordCheck;

    @NotBlank(message = "닉네임을 입력해주세요.")
    @Pattern(
            regexp = "^[가-힣a-zA-Z0-9]{2,10}$",
            message = "닉네임은 한글, 영문, 숫자로 이루어진 2~10자리여야 합니다."
    )
    private String nickname;

    @AssertTrue(message = "비밀번호가 일치하지 않습니다.")
    public boolean isPasswordMatching() {
        if (password == null || passwordCheck == null) return false;
        return password.equals(passwordCheck);
    }
}
