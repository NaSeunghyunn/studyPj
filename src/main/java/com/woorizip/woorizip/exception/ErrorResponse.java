package com.woorizip.woorizip.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ErrorResponse {
    private String error;

    public static ErrorResponse of(String error){
        return ErrorResponse.builder().error(error).build();
    }
}
