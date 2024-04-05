package com.projects.study.exception;

import com.projects.study.dto.ExceptionResponse;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

import static com.projects.study.ControllerUtils.*;

public class ExchangerExceptionHandler {

    public static void handle(HttpServletResponse res, Throwable t) {
        PrintWriter writer = null;
        try {
            writer = res.getWriter();
            setResponseStatus(res, t);
            String excAsJson = convertToJson(new ExceptionResponse(t.getMessage()));
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
        switch(t.getClass().getSimpleName()) {
            case "CurrencyNotFoundException":
            case "ExchangeRateNotFoundException":
                res.setStatus(HttpServletResponse.SC_NOT_FOUND);
                break;
            case "IllegalParameterException":
            case "ConvertibleAmountException":
                res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                break;
            case "CurrencyAlreadyExistException":
            case "ExchangeRateAlreadyExistException":
                res.setStatus(HttpServletResponse.SC_CONFLICT);
                break;
            case "RuntimeException":
            default:
                res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

}
