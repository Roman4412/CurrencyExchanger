package com.projects.study;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.projects.study.exception.IllegalParameterException;

import java.util.Map;


public class ControllerUtils {
    public static void validateParams(Map<String, String[]> params) {
        for(String p : params.keySet()) {
            System.out.println(params.get(p)[0]);
            if ((params.get(p)[0].isBlank() || params.get(p)[0] == null)) {
                throw new IllegalParameterException(String.format("Missing parameter %s", p));
            }
        }
    }

    public static void validatePathVar(String path) {
        if (path == null || path.substring(1).isBlank()) {
            throw new IllegalParameterException("Missing code in the query string");
        }
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

}
