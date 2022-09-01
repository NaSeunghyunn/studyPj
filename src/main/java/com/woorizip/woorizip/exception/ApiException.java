package com.woorizip.woorizip.exception;

import com.woorizip.woorizip.util.MessageCode;
import com.woorizip.woorizip.util.MessageUtil;

public class ApiException extends RuntimeException {
    public ApiException(String message) {
        super(message);
    }

    public ApiException(MessageCode code, String... params) {
        super(MessageUtil.getMessage(code.code(), params));
    }
}
