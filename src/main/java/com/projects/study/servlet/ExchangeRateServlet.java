package com.projects.study.servlet;

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

import static com.projects.study.ControllerUtils.*;

@WebServlet("/exchangeRate/*")
public class ExchangeRateServlet extends HttpServlet {
    private final Dao<ExchangeRate> dao = ExchangeRateDao.getInstance();
    private final ExchangeRateService exchangeRateService = new ExchangeRateService(dao);

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
        validateParams(req.getParameterMap());
        validatePathVar(req.getPathInfo());
        String code = req.getPathInfo().substring(1);
        String rate = req.getParameter("rate");
        System.out.println(rate);
        ExchangeRate updatedRate = exchangeRateService.update(code, rate);
        try {
            PrintWriter writer = resp.getWriter();
            writer.println(convertToJson(updatedRate));
            writer.close();
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        validatePathVar(req.getPathInfo());
        String code = req.getPathInfo().substring(1);
        ExchangeRate rate = exchangeRateService.get(code);
        try {
            PrintWriter writer = resp.getWriter();
            writer.println(convertToJson(rate));
            writer.close();
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }

}
