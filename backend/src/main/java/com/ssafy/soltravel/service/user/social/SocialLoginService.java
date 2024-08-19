package com.ssafy.soltravel.service.user.social;

import com.ssafy.soltravel.dto.user.UserType;
import com.ssafy.soltravel.dto.user.social.SocialAuthResponseDto;
import com.ssafy.soltravel.dto.user.social.SocialUserResponseDto;

public interface SocialLoginService {
    UserType getServiceName();
    SocialAuthResponseDto getAccessToken(String code);
    SocialAuthResponseDto refreshAccessToken(String refreshToken);
    SocialUserResponseDto getUserInfo(String accessToken);
}
