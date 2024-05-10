package com.projects.study.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.projects.study.constant.ExceptionMessage;
import com.projects.study.exception.InvalidParameterException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;


public final class ServletUtils {
    private static final String DELIMITER = "=";

    private ServletUtils() {
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
            throw new InvalidParameterException(ExceptionMessage.CUR_MISS_CODE);
        }
    }

    public static boolean isValidStringParam(String pattern, String... strings) {
        for(String s : strings) {
            if (s == null || !Pattern.matches(pattern, s)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isValidDecimalParam(String s, BigDecimal min, String pattern) {
        if (!isValidStringParam(pattern, s)) {
            System.out.println("aaa");
            return false;
        } else {
            BigDecimal decimal = new BigDecimal(s.replace(",", "."));
            return decimal.compareTo(min) >= 0;
        }
    }

    public static String getParamFromBody(HttpServletRequest request, String name) {
        Map<String, String> params = new HashMap<>();
        try(BufferedReader reader = request.getReader()) {
            List<String> strings = reader.lines().toList();
            for(String s : strings) {
                String[] split = s.split(DELIMITER);
                params.put(split[0], URLDecoder.decode(split[1], StandardCharsets.UTF_8));
            }
        } catch(IOException e) {
            throw new RuntimeException(e);
        }

        return params.get(name);
    }

}
