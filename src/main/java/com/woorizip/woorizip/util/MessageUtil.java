package com.woorizip.woorizip.util;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Locale;

@Component
public class MessageUtil {

    private static MessageSource messageSource;

    public MessageUtil(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public static String getMessage(MessageCode code, String... params) {
        return messageSource.getMessage(code.code(), getMessages(params), getLocale());
    }

    public static String getMessage(String code, String... params) {
        return messageSource.getMessage(code, getMessages(params), getLocale());
    }

    public static String[] getMessages(String... codes) {
        if (codes == null) {
            return null;
        }
        String[] messages = Arrays.stream(codes).map(MessageUtil::getMessage).toArray(String[]::new);
        return messages;
    }

    public static String getMessage(String code) {
        return messageSource.getMessage(code, null, getLocale());
    }

    private static Locale getLocale() {
        return LocaleContextHolder.getLocale();
    }
}
