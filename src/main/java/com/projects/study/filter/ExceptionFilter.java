package com.projects.study.filter;

import com.projects.study.exception.ExchangerExceptionHandler;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletResponse;


@WebFilter("/*")
public class ExceptionFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
        try {
            chain.doFilter(request, response);
        } catch(Throwable t) {
            ExchangerExceptionHandler.handle((HttpServletResponse) response, t);
        }
    }

}
