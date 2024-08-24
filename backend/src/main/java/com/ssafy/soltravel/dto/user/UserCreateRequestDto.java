package com.ssafy.soltravel.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateRequestDto {

  @NotNull
  @NotBlank
  private String name;

  @NotNull
  @NotBlank
  private String email;

  @NotNull
  @NotBlank
  private String password;

  @NotNull
  @NotBlank
  private String phone;

  @NotNull
  @NotBlank
  private String address;

  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate birth;
}
