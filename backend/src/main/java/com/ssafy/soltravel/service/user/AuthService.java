package com.ssafy.soltravel.service.user;


import com.ssafy.soltravel.domain.User;
import com.ssafy.soltravel.domain.redis.RedisPhone;
import com.ssafy.soltravel.dto.auth.AuthSMSVerifyRequestDto;
import com.ssafy.soltravel.dto.user.UserLoginRequestDto;
import com.ssafy.soltravel.dto.user.UserLoginResponseDto;
import com.ssafy.soltravel.exception.InvalidCredentialsException;
import com.ssafy.soltravel.repository.UserRepository;
import com.ssafy.soltravel.repository.redis.PhoneRepository;
import com.ssafy.soltravel.util.LogUtil;
import com.ssafy.soltravel.util.PasswordEncoder;
import jakarta.annotation.PostConstruct;
import java.util.Map;
import java.util.Random;
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
  private final PhoneRepository phoneRepository;
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

  /*
   * 일반회원 로그인
   */
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

  /*
   * 휴대폰 인증
   */
  public void sendSMSForVerification(AuthSMSVerifyRequestDto request) {

    // 메세지 기본 설정(from, to)
    Message message = new Message();
    message.setFrom(SERVICE_PHONE_NUM);
    message.setTo(request.getPhone());

    // 메세지 내용 설정
    String authCode = makeRandomAuthCode();
    message.setText(String.format("[솔레는 여행, 신나는 통장] 인증 코드는 '%s' 입니다.", authCode));

    // 메세지 전송
    SingleMessageSentResponse response = messageService.sendOne(
        new SingleMessageSendingRequest(message)
    );

    // 결과 저장
    phoneRepository.save(new RedisPhone(request.getPhone(), authCode));
    LogUtil.info("SMS Sent Successfully", response);
  }

  public String makeRandomAuthCode() {
    Random r1 = new Random();
    Random r2 = new Random();
    StringBuilder randomNumber = new StringBuilder();
    for (int i = 0; i < 6; i++) {
      if (r1.nextBoolean()) {
        randomNumber.append(Integer.toString(r2.nextInt(10)));
      } else {
        randomNumber.append((char) (r2.nextInt(26) + 97));
      }
    }
    return randomNumber.toString();
  }

}
