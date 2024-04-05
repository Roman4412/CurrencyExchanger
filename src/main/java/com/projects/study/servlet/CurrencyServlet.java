package com.projects.study.servlet;

import com.projects.study.dao.CurrencyDao;
import com.projects.study.dao.ExchangerDao;
import com.projects.study.entity.Currency;
import com.projects.study.service.CurrencyService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

import static com.projects.study.ControllerUtils.*;


@WebServlet("/currency/*")
public class CurrencyServlet extends HttpServlet {
    private final ExchangerDao<Currency> currencyExchangerDao = CurrencyDao.getInstance();
    private final CurrencyService currencyService = new CurrencyService(currencyExchangerDao);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        validatePathVar(req.getPathInfo());
        String code = req.getPathInfo().substring(1);
        Currency currency = currencyService.get(code);
        try {
            PrintWriter writer = resp.getWriter();
            writer.println(convertToJson(currency));
            writer.close();
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }

}
