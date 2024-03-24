package com.projects.study.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projects.study.dao.CurrencyDao;
import com.projects.study.dao.Dao;
import com.projects.study.entity.Currency;
import com.projects.study.service.CurrencyService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/currency/*")
public class CurrencyServlet extends HttpServlet {
    private final Dao<Currency> currencyDao = CurrencyDao.getInstance();
    private final CurrencyService currencyService = new CurrencyService(currencyDao);
    private final ObjectMapper jsonMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String curCode = req.getPathInfo().substring(1);
        if (curCode.isBlank()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } else {
            Currency currency = currencyService.getByCode(curCode);
            String curAsJson = jsonMapper.writeValueAsString(currency);
            PrintWriter writer = resp.getWriter();
            writer.println(curAsJson);
            writer.close();
        }
    }

}
