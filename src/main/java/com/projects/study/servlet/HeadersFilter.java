package com.projects.study.servlet;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;

import java.io.IOException;

@WebFilter("/*")
public class HeadersFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF8");
        chain.doFilter(request, response);
    }

}
