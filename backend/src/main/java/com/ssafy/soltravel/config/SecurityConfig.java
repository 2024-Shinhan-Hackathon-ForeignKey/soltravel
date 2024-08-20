package com.ssafy.soltravel.config;

import com.ssafy.soltravel.handler.OAuth2SuccessHandler;
import com.ssafy.soltravel.service.user.PrincipalOauthUserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.io.IOException;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final PrincipalOauthUserService principalOauthUserService;
    private final OAuth2UserService oauth2UserService;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(CsrfConfigurer::disable)
                .httpBasic(httpBasicCustomizer -> httpBasicCustomizer.disable())
                .authorizeHttpRequests(
                        request -> request.anyRequest().permitAll()
//                    requestMatchers(
//                                "/", "/api/v1/auth/**", "/oauth2/**", "/swagger-ui/**", "/error", "index.html",
//                                "/api/upload", "api/list", "/api/v1/main/**", "/v3/api-docs/**").permitAll()
//                                .anyRequest().permitAll()
//                            .authenticated()
                        ).oauth2Login(
                                oauth2 -> oauth2
                                        .authorizationEndpoint(endPoiont -> endPoiont.baseUri("/URL"))
                                        .redirectionEndpoint(endPoiont -> endPoiont.baseUri("/redirect URL"))
                                        .userInfoEndpoint(endPoiont -> endPoiont.userService(oauth2UserService))
                                        .successHandler(oAuth2SuccessHandler)
                        ).exceptionHandling(exceptionHandling -> exceptionHandling.authenticationEntryPoint(new FailedAuthenticatoinEntryPoint())
                );//TODO: JWT 필터 추가 필요

        return http.build();
    }

    @Bean
    protected CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*"); // 모든 오리진 허용
        corsConfiguration.addAllowedMethod("*"); // 모든 메소드 허용
        corsConfiguration.addAllowedHeader("*"); // 모든 헤더 허용

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration); // 모든 경로에 CORS 설정 적용

        return source; // CORS 설정 소스 반환
    }

}
// 인증 실패 시 처리할 엔트리 포인트
class FailedAuthenticatoinEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        response.setContentType("application/json"); // 응답 콘텐츠 타입을 JSON으로 설정
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 응답 상태 코드를 401으로 설정
        response.getWriter()
                .write("{\"code\" : \"UA\", \"message\" : \"UnAuthorized.\"}"); // 응답 바디에 JSON 형태로 메시지 작성

    }
}