package com.projects.study.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projects.study.dto.ExceptionDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

public class ExchangerExceptionHandler {
    private static final ObjectMapper jsonMapper = new ObjectMapper();

    public static void handle(HttpServletRequest req, HttpServletResponse res, Throwable t) {
        if (t instanceof CurrencyNotFoundException) {
            handleNotFoundExceptions(res, t);
        } else if (t instanceof CurrencyAlreadyExistException) {
            handleAlreadyExistExceptions(res, t);
        }
    }

    private static void handleAlreadyExistExceptions(HttpServletResponse res, Throwable t) {
        try (PrintWriter writer = res.getWriter()) {
            res.setStatus(HttpServletResponse.SC_CONFLICT);
            String excAsJson = jsonMapper.writeValueAsString(new ExceptionDto(t.getMessage()));
            writer.println(excAsJson);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void handleNotFoundExceptions(HttpServletResponse res, Throwable t) {
        try (PrintWriter writer = res.getWriter()) {
            res.setStatus(HttpServletResponse.SC_NOT_FOUND);
            String excAsJson = jsonMapper.writeValueAsString(new ExceptionDto(t.getMessage()));
            writer.println(excAsJson);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
