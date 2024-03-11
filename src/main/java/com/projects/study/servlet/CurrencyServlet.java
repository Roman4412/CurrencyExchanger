package com.projects.study.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projects.study.DAO.CurrencyDao;
import com.projects.study.DAO.Dao;
import com.projects.study.entity.Currency;
import com.projects.study.service.CurrencyService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

@WebServlet("/currency/*")
public class CurrencyServlet extends HttpServlet {
    private final Dao<Currency> currencyDao = CurrencyDao.getInstance();
    private final CurrencyService currencyService = new CurrencyService(currencyDao);
    private final ObjectMapper jsonMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF8");

        String curCode = req.getPathInfo().substring(1);
        if (curCode.isBlank()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } else {
            Optional<Currency> currencyOpt = currencyService.getCurrencyByCode(curCode);
            if (currencyOpt.isPresent()) {
                PrintWriter writer = resp.getWriter();
                writer.println(jsonMapper.writeValueAsString(currencyOpt.get()));
                writer.close();
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doDelete(req, resp);
    }

}
