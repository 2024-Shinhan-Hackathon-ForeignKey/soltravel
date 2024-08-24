package com.ssafy.soltravel.controller;

import com.ssafy.soltravel.dto.auth.AuthSMSSendRequestDto;
import com.ssafy.soltravel.dto.auth.AuthSMSVerificationRequestDto;
import com.ssafy.soltravel.dto.user.UserLoginRequestDto;
import com.ssafy.soltravel.dto.user.UserLoginResponseDto;
import com.ssafy.soltravel.service.user.AuthService;
import com.ssafy.soltravel.util.LogUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

  private final AuthService authService;

  /*
   * 일반 회원 로그인
   * */
  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody UserLoginRequestDto loginRequestDto) {
    LogUtil.info(loginRequestDto.toString());
    UserLoginResponseDto response = authService.login(loginRequestDto);
    return ResponseEntity.ok().body(response);
  }

  /*
   * SMS 코드 전송 요청
   * */
  @PostMapping("/verify/phone/send")
  public ResponseEntity<?> sendSMSVerification(
      @RequestBody AuthSMSSendRequestDto sendRequestDto) {

    LogUtil.info("requested", sendRequestDto.toString());
    return ResponseEntity.ok().body(authService.sendSMSForVerification(sendRequestDto));
  }

  /*
   * SMS 코드 인증
   * */
  @PostMapping("/verify/phone/code")
  public ResponseEntity<?> codeVerification(
      @RequestBody AuthSMSVerificationRequestDto verifyRequestDto) {

    LogUtil.info("requested", verifyRequestDto.toString());
    return ResponseEntity.ok().body(authService.verifySMSAuthCode(verifyRequestDto));
  }

  @GetMapping("/test")
  public String test() {
    return "test";
  }

  @GetMapping("/test-ok")
  public String testOk() {
    return "testOk";
  }
}
