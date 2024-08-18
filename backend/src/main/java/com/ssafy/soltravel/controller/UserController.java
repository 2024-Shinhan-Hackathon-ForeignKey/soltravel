package com.ssafy.soltravel.controller;


import com.ssafy.soltravel.dto.user.UserLoginRequestDto;
import com.ssafy.soltravel.service.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> userLogin(@RequestBody @Valid UserLoginRequestDto request) {
        log.info("userLogin(UserController): 유저 로그인 요청");
        log.info("email: {}", request.getEmail());
        return ResponseEntity.ok().body(userService.loginUser(request));
    }
}
