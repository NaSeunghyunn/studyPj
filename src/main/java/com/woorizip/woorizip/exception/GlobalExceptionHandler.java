package com.woorizip.woorizip.exception;

import com.woorizip.woorizip.util.MessageUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.woorizip.woorizip.util.MessageCode.INTERNAL_SERVER_ERROR;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<Object> exceptionHandler(final ApiException e){
        log.error("BadException", e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse.of(e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> exceptionHandler(final Exception e){
        log.error("BadException", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ErrorResponse.of(MessageUtil.getMessage(INTERNAL_SERVER_ERROR)));
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> exceptionHandler(final MethodArgumentNotValidException e) {
        log.error("MethodArgumentNotValidException", e);
        String message = MessageUtil.getMessage(e.getFieldErrors().get(0).getCode(), e.getFieldErrors().get(0).getField());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse.of(message));
    }
}
