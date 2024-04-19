package com.projects.study.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.projects.study.constant.ExceptionMessage;
import com.projects.study.exception.InvalidParameterException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.regex.Pattern;


public final class ControllerUtils {

    private ControllerUtils() {
    }

    public static String convertToJson(Object value) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(value);
        } catch(JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static void sendResponse(String body, HttpServletResponse resp) {
        try {
            PrintWriter writer = resp.getWriter();
            writer.println(body);
            writer.close();
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String parsePathVar(HttpServletRequest request) {
        String pathInfo = request.getPathInfo();
        pathVarValidate(pathInfo);
        return pathInfo.substring(1);
    }

    private static void pathVarValidate(String path) {
        if (path == null || path.substring(1).isBlank()) {
            throw new InvalidParameterException(ExceptionMessage.MISS_CUR_CODE);
        }
    }

    public static boolean isValidString(String pattern, String... strings) {
        for(String s : strings) {
            if (s == null || !Pattern.matches(pattern, s)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isValidString(String pattern, String s) {
        return Pattern.matches(pattern, s);
    }

    public static boolean isValidDecimalInString(String s, BigDecimal min, String pattern) {
        if (!isValidString(pattern, s)) {
            return false;
        } else {
            BigDecimal decimal = new BigDecimal(s.replace(",", "."));
            return decimal.compareTo(min) >= 0;
        }
    }

}
