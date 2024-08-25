package com.ssafy.soltravel.handler;

import com.ssafy.soltravel.dto.ResponseDto;
import com.ssafy.soltravel.exception.InvalidAuthCodeException;
import com.ssafy.soltravel.exception.InvalidCredentialsException;
import com.ssafy.soltravel.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDto> handleGeneralException(Exception e) {

        ResponseDto errorResponseDto = new ResponseDto();
        errorResponseDto.setStatus("INTERNAL_SERVER_ERROR");
        errorResponseDto.setMessage(e.getMessage());

        return new ResponseEntity<>(errorResponseDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(WebClientResponseException.class)
    public ResponseEntity<String> handleWebClientResponseException(WebClientResponseException e) {
        e.printStackTrace();
        return new ResponseEntity<String>(e.getResponseBodyAsString(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ResponseDto> handleUserNotFoundException(UserNotFoundException e) {
        ResponseDto errorResponse = new ResponseDto(
            "NOT_FOUND",
            String.format("DB에 해당 유저가 없습니다: %d", e.getUserId())
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ResponseDto> handleInvalidCredentialsException(
        InvalidCredentialsException e) {
        ResponseDto errorResponse = new ResponseDto(
            "UNAUTHORIZED",
            e.getMessage()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

  @ExceptionHandler(InvalidAuthCodeException.class)
  public ResponseEntity<ResponseDto> handleInvalidAuthCodeException(
      InvalidAuthCodeException e) {
    ResponseDto errorResponse = new ResponseDto(
        "BAD REQUEST",
        e.getMessage()
    );
    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }
}
