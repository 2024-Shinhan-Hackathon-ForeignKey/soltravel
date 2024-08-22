package com.ssafy.soltravel.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class ResponseDto {

    private String status;
    private String message;

    public ResponseDto() {
        this.status = "SUCCESS";
        this.message = "요청 처리 완료";
    }

    public static ResponseEntity<ResponseDto> databaseError(String message) {
        ResponseDto responseBody = new ResponseDto("Database Error", message);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
    }

    public static ResponseEntity<ResponseDto> validationFail(String message) {
        ResponseDto responseBody = new ResponseDto("Validation Failed", message);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
    }
}
