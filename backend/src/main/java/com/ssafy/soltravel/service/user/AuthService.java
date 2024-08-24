package com.ssafy.soltravel.service.user;


import com.ssafy.soltravel.domain.User;
import com.ssafy.soltravel.dto.auth.AuthSMSVerifyRequestDto;
import com.ssafy.soltravel.dto.user.UserLoginRequestDto;
import com.ssafy.soltravel.dto.user.UserLoginResponseDto;
import com.ssafy.soltravel.exception.InvalidCredentialsException;
import com.ssafy.soltravel.repository.UserRepository;
import com.ssafy.soltravel.util.LogUtil;
import com.ssafy.soltravel.util.NCPUtil;
import com.ssafy.soltravel.util.PasswordEncoder;
import java.sql.Timestamp;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final UserRepository userRepository;
  private final TokenService tokenService;
  private final Map<String, String> apiKeys;
  private final NCPUtil ncpUtil;

  public UserLoginResponseDto login(UserLoginRequestDto loginRequestDto) {
    String email = loginRequestDto.getEmail();
    String encryptedPwd = PasswordEncoder.encrypt(email, loginRequestDto.getPassword());
    LogUtil.info("Encrypted Password", encryptedPwd);

    User user = userRepository.findByEmailAndPwd(email, encryptedPwd).orElseThrow(
        () -> new InvalidCredentialsException(loginRequestDto.getEmail())
    );
    //TODO: 정지(탈퇴) 회원 검증

    return tokenService.saveRefreshToken(user.getUserId());
  }

  public void sendSMSForVerification(AuthSMSVerifyRequestDto request) {

    // 헤더 설정
    Timestamp now = new Timestamp(System.currentTimeMillis());
    String accessKey = apiKeys.get("NCP_ACCESS_KEY");
    String secretKey = apiKeys.get("NCP_SECRET_KEY");

  }
}
