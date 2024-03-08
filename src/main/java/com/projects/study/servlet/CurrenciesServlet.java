package com.projects.study.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projects.study.DAO.CurrencyDAO;
import com.projects.study.DAO.DAO;
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
    private final DAO<Currency> currencyDAO = CurrencyDAO.getInstance();
    private final CurrencyService currencyService = new CurrencyService(currencyDAO);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter writer = null;
        try {
            writer = resp.getWriter();
            resp.setContentType("application/json");
            List<Currency> currencies = currencyService.getAll();
            ObjectMapper objectMapper = new ObjectMapper();
            writer.println(objectMapper.writeValueAsString(currencies));
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (SQLException e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        } finally {
            if ((writer != null)) {
                writer.close();
            }
        }
    }

}
