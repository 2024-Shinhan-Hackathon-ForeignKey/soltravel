package com.ssafy.soltravel.service.user;


import com.ssafy.soltravel.domain.User;
import com.ssafy.soltravel.dto.user.UserCreateRequestDto;
import com.ssafy.soltravel.dto.user.UserDetailDto;
import com.ssafy.soltravel.dto.user.UserLoginRequestDto;
import com.ssafy.soltravel.dto.user.UserLoginResponseDto;
import com.ssafy.soltravel.dto.user.api.UserCreateRequestBody;
import com.ssafy.soltravel.dto.user.api.UserSearchRequestBody;
import com.ssafy.soltravel.repository.UserRepository;
import com.ssafy.soltravel.util.LogUtil;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

  private final String API_URI = "/member";
  private final UserRepository userRepository;
  private final Map<String, String> apiKeys;
  private final WebClient webClient;


  //로그인
  public UserLoginResponseDto loginUser(UserLoginRequestDto request) {
    LogUtil.info(request.getCode());
    return UserLoginResponseDto.builder()
        .accessToken("testAccessToken")
        .refreshToken("testRefreshToken")
        .build();
  }

  //회원가입
  public void createUser(UserCreateRequestDto createDto) {
    User user = convertCreateDtoToUser(createDto);
    UserCreateRequestBody body = UserCreateRequestBody.builder()
        .apiKey(apiKeys.get("API_KEY"))
        .userId(createDto.getEmail())
        .build();

    String response = webClient.post()
        .uri(API_URI)
        .contentType(MediaType.APPLICATION_JSON)
        .body(Mono.just(body), UserCreateRequestBody.class)
        .retrieve()                 // HTTP 요청을 실행하고 응답을 가져옴
        .bodyToMono(String.class)   // 응답 본문을 String으로 변환
        .block();                   // 비동기 결과를 동기식으로 처리

    LogUtil.info("request(user create) to api", response);
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//    User user = userRepository.findByEmail(email).orElseThrow(
//        () -> new RuntimeException(String.format("loadUserByUsername Failed: %s", email))
//    );

    User user = userRepository.findByEmail(email).orElse(createTestUserIfNotExists());

    return UserDetailDto.builder()
        .id(user.getId())
        .name(user.getName())
        .email(user.getEmail())
        .password(user.getPassword())
        .role(user.getRole())
        .build();
  }

  // 회원가입 요청 DTO를 유저 엔티티로 변환
  private User convertCreateDtoToUser(UserCreateRequestDto dto) {
    return User.createUser(
        dto.getName(),
        dto.getPassword(),
        dto.getEmail(),
        dto.getPhone(),
        dto.getAddress()
    );
  }

  /*
   *  Test용 메서드
   */
  //유저가 존재하지 않으면 임의로 테스트 유저 생성
  private User createTestUserIfNotExists() {
    User user = userRepository.findByEmail("test@email.com").orElse(
        User.createUser(
            "test",
            "pass",
            "test@email.com",
            "010-1111-1111",
            "testAddress"
        ));
    return user;
  }

  //외부 API에 유저 조회 요청
  public void requestSearchUser(String email) {
    UserSearchRequestBody body = UserSearchRequestBody.builder()
        .apiKey(apiKeys.get("API_KEY"))
        .userId(email)
        .build();

    ResponseEntity<Map<String, Object>> response = webClient.post()
        .uri(API_URI + "/search")
        .contentType(MediaType.APPLICATION_JSON)
        .body(Mono.just(body), UserCreateRequestBody.class)
        .retrieve()
        .toEntity(new ParameterizedTypeReference<Map<String, Object>>() {
        })
        .block();

    LogUtil.info("request(user search) to api", response);
  }
}
