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
import java.sql.SQLException;
import java.util.List;

@WebServlet("/currencies")
public class CurrenciesServlet extends HttpServlet {
    private final Dao<Currency> currencyDao = CurrencyDao.getInstance();
    private final CurrencyService currencyService = new CurrencyService(currencyDao);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF8");

        PrintWriter writer = null;
        try {
            List<Currency> currencies = currencyService.getAll();
            writer = resp.getWriter();
            ObjectMapper objectMapper = new ObjectMapper();
            writer.println(objectMapper.writeValueAsString(currencies));
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (SQLException e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        } finally {
            if ((writer != null)) {
                writer.close();
            }
        }
    }

}