package com.ssafy.soltravel.service.user.social;


import com.ssafy.soltravel.dto.user.UserLoginRequestDto;
import com.ssafy.soltravel.dto.user.UserLoginResponseDto;
import com.ssafy.soltravel.dto.user.UserType;
import com.ssafy.soltravel.dto.user.social.SocialAuthResponseDto;
import com.ssafy.soltravel.dto.user.social.SocialUserResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class SocialUserService {

    private final List<SocialLoginService> socialLoginServices;

    public UserLoginResponseDto socialLoginUser(UserLoginRequestDto request) {
        // 소셜 서비스 식별(NAVER, KAKAO)
        SocialLoginService socialLoginService = getLoginService(request.getUserType());

        // 유저 토큰 정보 얻기
        SocialAuthResponseDto authResponse = socialLoginService.getAccessToken(request.getCode());

        //TODO: 토큰 유효성 검사

        // 유저 개인 정보 얻기
        SocialUserResponseDto userResponse = socialLoginService.getUserInfo(authResponse.getAccessToken());

        //TODO: 회원가입 여부 검사

        //TODO: 회원 엔티티로 JWT 생성, 반환

        return null;
    }


    /* 여러 로그인 서비스 API 중에 어떤 서비스인지 확인하는 메서드 */
    private SocialLoginService getLoginService(UserType type){
        for(SocialLoginService loginService: socialLoginServices){
            if(loginService.getServiceName().equals(type)){
                log.info("Selected login service: {}", loginService.getServiceName());
                return loginService;
            }
        }
        throw new RuntimeException("");
    }
}
