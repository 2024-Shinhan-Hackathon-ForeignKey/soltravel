package com.ssafy.soltravel.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

  @GetMapping("/test")
  public String test() {
    return "test";
  }

  @GetMapping("/test-ok")
  public String testOk() {
    return "testOk";
  }
}
