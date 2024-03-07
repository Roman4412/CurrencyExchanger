package com.projects.study.servlet;

import com.projects.study.DAO.CurrencyDAO;
import com.projects.study.DAO.DAO;
import com.projects.study.entity.Currency;
import com.projects.study.service.CurrencyService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/currencies")
public class CurrenciesServlet extends HttpServlet {
    private final DAO<Currency> currencyDAO = CurrencyDAO.getInstance();
    private final CurrencyService currencyService = new CurrencyService(currencyDAO);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        writer.println(currencyService.getAll());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

}
