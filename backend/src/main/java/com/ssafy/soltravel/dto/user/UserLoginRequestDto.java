package com.ssafy.soltravel.dto.user;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginRequestDto {

    private String email;
    private String password;
    private String code;

    @NotNull
    private UserType userType;
}
