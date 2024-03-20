package com.projects.study.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projects.study.DAO.CurrencyDao;
import com.projects.study.DAO.Dao;
import com.projects.study.entity.Currency;
import com.projects.study.exception.CurrencyAlreadyExistException;
import com.projects.study.exception.ExchangerExceptionHandler;
import com.projects.study.service.CurrencyService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/currencies")
public class CurrenciesServlet extends HttpServlet {
    private final Dao<Currency> currencyDao = CurrencyDao.getInstance();
    private final CurrencyService currencyService = new CurrencyService(currencyDao);
    private final ObjectMapper jsonMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF8");

        try (PrintWriter writer = resp.getWriter()) {
            List<Currency> currencies = currencyService.getAll();
            writer.println(jsonMapper.writeValueAsString(currencies));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF8");

        String code = req.getParameter("code");
        String name = req.getParameter("name");
        String sign = req.getParameter("sign");
        if (code.isBlank() || name.isBlank() || sign.isBlank()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } else {
            try {
                Currency currency = new Currency();
                currency.setCode(code.toUpperCase().trim());
                currency.setFullName(name.trim());
                currency.setSign(sign.trim());

                Currency createdCurrency = currencyService.saveCurrency(currency);
                resp.setStatus(HttpServletResponse.SC_CREATED);
                PrintWriter writer = resp.getWriter();
                writer.println(jsonMapper.writeValueAsString(createdCurrency));
                writer.close();
            } catch (CurrencyAlreadyExistException e) {
                ExchangerExceptionHandler.handle(req, resp, e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
