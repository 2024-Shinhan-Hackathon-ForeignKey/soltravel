package com.ssafy.soltravel.service.user;


import com.ssafy.soltravel.domain.User;
import com.ssafy.soltravel.dto.auth.AuthSMSVerifyRequestDto;
import com.ssafy.soltravel.dto.user.UserLoginRequestDto;
import com.ssafy.soltravel.dto.user.UserLoginResponseDto;
import com.ssafy.soltravel.exception.InvalidCredentialsException;
import com.ssafy.soltravel.repository.UserRepository;
import com.ssafy.soltravel.util.LogUtil;
import com.ssafy.soltravel.util.PasswordEncoder;
import jakarta.annotation.PostConstruct;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final UserRepository userRepository;
  private final TokenService tokenService;
  private DefaultMessageService messageService;
  private final Map<String, String> apiKeys;
  private final String SERVICE_PHONE_NUM = "01062966409";

  @PostConstruct
  public void init() {
    this.messageService = NurigoApp.INSTANCE.initialize(
        apiKeys.get("SMS_API_KEY"),
        apiKeys.get("SMS_SECRET_KEY"),
        "https://api.coolsms.co.kr"
    );
  }

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

    Message message = new Message();
    message.setFrom(SERVICE_PHONE_NUM);
    message.setTo(request.getPhone());
    message.setText("한글 45자 이하");

    SingleMessageSentResponse response = messageService.sendOne(
        new SingleMessageSendingRequest(message));

    LogUtil.info("SMS Sent Successfully", response);
  }
}
