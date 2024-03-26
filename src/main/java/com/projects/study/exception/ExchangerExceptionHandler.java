package com.projects.study.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projects.study.dto.ExceptionDto;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

public class ExchangerExceptionHandler {
    private static final ObjectMapper jsonMapper = new ObjectMapper();

    public static void handle(HttpServletResponse res, Throwable t) {
        PrintWriter writer = null;
        try {
            writer = res.getWriter();
            setResponseStatus(res, t);
            String excAsJson = jsonMapper.writeValueAsString(new ExceptionDto(t.getMessage()));
            writer.println(excAsJson);
        } catch(IOException e) {
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            throw new RuntimeException(e);
        } finally {
            if (writer != null) {
                writer.close();
            }
            t.printStackTrace();
        }
    }

    private static void setResponseStatus(HttpServletResponse res, Throwable t) {
        if (t instanceof CurrencyNotFoundException || t instanceof ExchangeRateNotFoundException) {
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } else if (t instanceof CurrencyAlreadyExistException || t instanceof ExchangeRateAlreadyExistException) {
            res.setStatus(HttpServletResponse.SC_CONFLICT);
        } else if (t instanceof RuntimeException) {
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } else {
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

}
