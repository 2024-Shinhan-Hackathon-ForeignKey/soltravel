package com.ssafy.soltravel.controller;


import com.ssafy.soltravel.dto.user.UserCreateRequestDto;
import com.ssafy.soltravel.service.user.UserService;
import com.ssafy.soltravel.util.LogUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

  private final UserService userService;

  @PostMapping("/join")
  public ResponseEntity<?> createUser(@RequestBody UserCreateRequestDto joinDto) {
    LogUtil.info(joinDto.toString());
    userService.createUser(joinDto);
    return new ResponseEntity<String>("유저 생성 완료", HttpStatus.CREATED);
  }

  @PostMapping("/search")
  public ResponseEntity<?> searchUser(@RequestParam String email) {
    LogUtil.info("requested", email.toString());
    userService.requestSearchUser(email);
    return new ResponseEntity<String>("유저 조회", HttpStatus.CREATED);
  }
}
