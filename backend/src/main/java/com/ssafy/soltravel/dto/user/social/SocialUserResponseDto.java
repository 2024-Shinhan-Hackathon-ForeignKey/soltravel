package com.ssafy.soltravel.dto.user.social;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;


@Builder
@Data
public class SocialUserResponseDto {
    private String name;
    private String email;
    private String phone;
}
