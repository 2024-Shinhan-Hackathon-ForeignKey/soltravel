package com.ssafy.soltravel.dto.user;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginRequestDto {

  @NotNull
  @NotBlank
  private String email;

  @NotNull
  @NotBlank
  private String password;
}
