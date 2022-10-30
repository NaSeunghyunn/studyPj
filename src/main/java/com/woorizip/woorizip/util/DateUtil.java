package com.woorizip.woorizip.util;

import com.woorizip.woorizip.exception.ApiException;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.woorizip.woorizip.util.MessageCode.*;
import static com.woorizip.woorizip.util.NameCode.*;

public class DateUtil {

    public static LocalDate parseDate(String dateStr) {
        if(!StringUtils.hasText(dateStr)) return null;
        try {
            return LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } catch (Exception e) {
            throw new ApiException(BAD_INPUT_ERROR, DATE.code());
        }
    }

    public static String formatDate(LocalDate date) {
        if (date == null) return null;
        return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public static LocalDate getPlusDateFromNow(int days) {
        return LocalDate.now().plusDays(days);
    }
}
