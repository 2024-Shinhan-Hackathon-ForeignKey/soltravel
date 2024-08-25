package com.ssafy.soltravel.dto.user;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserProfileResponseDto {

    private Long userId;

    private String username;

    private String email;

    private String phone;
    
    private String address;

    private LocalDate birth;

    private LocalDateTime registerAt;

}
