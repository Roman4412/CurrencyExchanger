package com.projects.study.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.projects.study.exception.InvalidParameterException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;


public final class ControllerUtils {

    private ControllerUtils() {
    }


    public static String formatParam(String param) {
        return param.trim().toUpperCase();
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
            throw new InvalidParameterException("Missing code in the query string");
        }
    }

}
