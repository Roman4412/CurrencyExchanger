package com.projects.study.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projects.study.DAO.Dao;
import com.projects.study.DAO.ExchangeRateDao;
import com.projects.study.entity.ExchangeRate;
import com.projects.study.service.ExchangeRateService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;
@WebServlet("/exchangeRate/*")
public class ExchangeRateServlet extends HttpServlet {
    private final Dao<ExchangeRate> dao = ExchangeRateDao.getInstance();
    private final ExchangeRateService exchangeRateService = new ExchangeRateService(dao);
    ObjectMapper jsonMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF8");

        String curPair = req.getPathInfo().substring(1);
        if (curPair.isBlank()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } else {
            Optional<ExchangeRate> rateOptional = exchangeRateService.getExchangeRateByCode(curPair);
            if (rateOptional.isPresent()) {
                PrintWriter writer = resp.getWriter();
                writer.println(jsonMapper.writeValueAsString(rateOptional.get()));
                writer.close();
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        }
    }

}
