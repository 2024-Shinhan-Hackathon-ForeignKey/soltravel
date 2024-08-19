package com.ssafy.soltravel.service.user;

import com.ssafy.soltravel.dto.user.social.KakaoUserInfo;
import com.ssafy.soltravel.dto.user.social.NaverUserInfo;
import com.ssafy.soltravel.dto.user.social.OAuth2UserInfo;
import com.ssafy.soltravel.dto.user.social.PrincipalDetailsDto;
import com.ssafy.soltravel.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class PrincipalOauthUserService extends DefaultOAuth2UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        //registraionId로 로그인한 소셜 서비스 확인 가능
        log.info("loadUser(PrincipalOauthUserService): getClientRegistration");
        log.info("{}", userRequest.getClientRegistration());
        log.info("loadUser(PrincipalOauthUserService): getAccessToken");
        log.info("{}", userRequest.getAccessToken());
        log.info("loadUser(PrincipalOauthUserService): getAttributes");
        log.info("{}", super.loadUser(userRequest).getAttributes());
        OAuth2User oAuth2User = super.loadUser(userRequest);

        //OAuth2 로그인 & 회원가입
        OAuth2UserInfo oAuth2UserInfo =
        switch (userRequest.getClientRegistration().getRegistrationId()) {
            case "kakao":
                yield new KakaoUserInfo(oAuth2User.getAttributes());
            case "naver":
                yield new NaverUserInfo((Map<String, Object>) oAuth2User.getAttributes().get("response"));
            default:
                throw new IllegalStateException("Unexpected value: " + userRequest.getClientRegistration().getRegistrationId());
        };
        log.info("{}", oAuth2UserInfo);


        return new PrincipalDetailsDto();
    }
}
