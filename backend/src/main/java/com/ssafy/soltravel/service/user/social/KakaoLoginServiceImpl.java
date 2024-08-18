package com.ssafy.soltravel.service.user.social;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.ssafy.soltravel.dto.user.UserType;
import com.ssafy.soltravel.dto.user.social.SocialAuthResponseDto;
import com.ssafy.soltravel.dto.user.social.SocialUserResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class KakaoLoginServiceImpl implements SocialLoginService {

    // 스프링부트 빈 DI
    private RestTemplate restTemplate;
    // yml 설정 파일에서 주입
    @Value("${social.client.kakao.grant-type}")
    private String grantType;
    @Value("${social.client.kakao.grant-type-refresh}")
    private String grantTypeRefresh;
    @Value("${social.client.kakao.client-id}")
    private String clientId;
    @Value("${social.client.kakao.redirect-uri}")
    private String redirectUri;
    @Value("${social.client.kakao.client-secret}")
    private String clientSecret;

    @Override
    public UserType getServiceName() {
        return UserType.KAKAO;
    }

    @Override
    public SocialAuthResponseDto getAccessToken(String code) {
        String url = "https://kauth.kakao.com/oauth/token";

        //헤더 셋팅
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Content-Type", "application/x-www-form-urlencoded");

        //바디 셋팅(code 제외 나머지는 고정값)
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", grantType);
        body.add("client_id", clientId);
        body.add("client_secret", clientSecret);
        body.add("redirect_uri", redirectUri);
        body.add("code", code);

        //요청
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, headers);
        ResponseEntity<SocialAuthResponseDto> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                requestEntity,
                SocialAuthResponseDto.class
        );
        log.info("Kakao Access Token Headers: {}", response.getHeaders());
        log.info("Kakao Access Token Response: {}", response.getBody());
        return response.getBody();
    }

    @Override
    public SocialAuthResponseDto refreshAccessToken(String refreshToken) {
        String url = "https://kauth.kakao.com/oauth/token";

        //헤더 셋팅
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf("pplication/x-www-form-urlencoded;charset=utf-8"));

        //바디 셋팅
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", grantTypeRefresh);
        body.add("client_id", clientId);
        body.add("client_secret", clientSecret);
        body.add("refresh_token", refreshToken);

        //요청
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, headers);
        ResponseEntity<SocialAuthResponseDto> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                requestEntity,
                SocialAuthResponseDto.class
        );

        //응답
        return response.getBody();
    }

    @Override
    public SocialUserResponseDto getUserInfo(String accessToken) {

        //쿼리 파라미터 & URI 셋팅
        String url = "https://kapi.kakao.com/v2/user/me";

        //헤더 셋팅
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        //요청 보내기
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                requestEntity,
                String.class
        );
        log.info("Kakao user response: {}", response.getBody());

        //Json 파싱
        String jsonString = response.getBody();
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();

        //파싱한 Json으로 유저정보 뽑아내기
//        KakaoUserResponseDto kakaoUserResponseDTO = gson.fromJson(jsonString, KakaoUserResponseDto.class);
//        KakaoUserResponseDto.KakaoUserData kakaoUserData =
//                Optional.ofNullable(kakaoUserResponseDTO.getKakao_account())
//                        .orElse(KakaoUserResponseDTO.KakaoUserData.builder().build());


        //유저정보를 DTO에 감싸서 반환
        return null;
    }
}
