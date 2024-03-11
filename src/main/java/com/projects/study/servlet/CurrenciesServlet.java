package com.projects.study.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projects.study.DAO.CurrencyDao;
import com.projects.study.DAO.Dao;
import com.projects.study.entity.Currency;
import com.projects.study.service.CurrencyService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;

@WebServlet("/currencies")
public class CurrenciesServlet extends HttpServlet {
    private final Dao<Currency> currencyDao = CurrencyDao.getInstance();
    private final CurrencyService currencyService = new CurrencyService(currencyDao);
    private final ObjectMapper jsonMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF8");

        PrintWriter writer = resp.getWriter();
        List<Currency> currencies = currencyService.getAll();
        ObjectMapper objectMapper = new ObjectMapper();
        writer.println(objectMapper.writeValueAsString(currencies));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF8");

        String code = req.getParameter("code");
        String name = req.getParameter("name");
        String sign = req.getParameter("sign");
        if (code.isBlank() || name.isBlank() || sign.isBlank()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } else {
            Currency newCurrency = new Currency();
            newCurrency.setCode(code.toLowerCase().trim());
            newCurrency.setFullName(name.trim());
            newCurrency.setSign(sign.trim());

            Optional<Currency> savedCurrency = currencyService.saveCurrency(newCurrency);
            if (savedCurrency.isPresent()) {
                resp.setStatus(HttpServletResponse.SC_CREATED);
                PrintWriter writer = resp.getWriter();
                newCurrency = savedCurrency.get();
                writer.println(jsonMapper.writeValueAsString(newCurrency));
            } else {
                resp.setStatus(HttpServletResponse.SC_CONFLICT);
            }
        }
    }

}
