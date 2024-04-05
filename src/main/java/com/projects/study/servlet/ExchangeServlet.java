package com.projects.study.servlet;

import com.projects.study.dao.ExchangerDao;
import com.projects.study.dao.ExchangeRateDao;
import com.projects.study.dto.ExchangeResponse;
import com.projects.study.entity.ExchangeRate;
import com.projects.study.service.ExchangeRateService;
import com.projects.study.service.ExchangeService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;

import static com.projects.study.ControllerUtils.*;

@WebServlet("/exchange")
public class ExchangeServlet extends HttpServlet {
    ExchangeService exchangeService = new ExchangeService();
    ExchangerDao<ExchangeRate> exchangerDao = ExchangeRateDao.getInstance();
    ExchangeRateService exchangeRateService = new ExchangeRateService(exchangerDao);


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        validateParams(req.getParameterMap());
        String base = req.getParameter("base");
        String target = req.getParameter("target");
        BigDecimal amount = new BigDecimal(req.getParameter("amount"));


        BigDecimal converted = exchangeService.exchange(base, target, amount);
        ExchangeResponse response = new ExchangeResponse();
        response.setExchangeRate(exchangeRateService.get(base + target));
        response.setAmount(amount);
        response.setConvertedAmount(converted);
        try(PrintWriter writer = resp.getWriter()) {
            writer.println(convertToJson(response));
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }

}
