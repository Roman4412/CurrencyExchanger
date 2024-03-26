package com.projects.study.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projects.study.dao.Dao;
import com.projects.study.dao.ExchangeRateDao;
import com.projects.study.entity.ExchangeRate;
import com.projects.study.service.ExchangeRateService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/exchangeRate/*")
public class ExchangeRateServlet extends HttpServlet {
    private final Dao<ExchangeRate> dao = ExchangeRateDao.getInstance();
    private final ExchangeRateService exchangeRateService = new ExchangeRateService(dao);
    ObjectMapper jsonMapper = new ObjectMapper();

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
        String code = req.getPathInfo().substring(1);
        String rate = req.getParameter("rate");
        if (code.isBlank() || rate.isBlank()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } else {
            ExchangeRate updatedRate = exchangeRateService.update(code, rate);
            try {
                String rateAsJson = jsonMapper.writeValueAsString(updatedRate);
                PrintWriter writer = resp.getWriter();
                writer.println(rateAsJson);
                writer.close();
            } catch(IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        String code = req.getPathInfo().substring(1);
        if (code.isBlank()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } else {
            ExchangeRate rate = exchangeRateService.getByCode(code);
            try {
                String rateAsJson = jsonMapper.writeValueAsString(rate);
                PrintWriter writer = resp.getWriter();
                writer.println(rateAsJson);
                writer.close();
            } catch(IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
