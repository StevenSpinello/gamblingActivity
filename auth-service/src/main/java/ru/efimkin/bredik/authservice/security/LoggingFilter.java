package ru.efimkin.bredik.authservice.security;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
public class LoggingFilter implements Filter {

    @PostConstruct
    public void init() {
        System.out.println("✅ LoggingFilter инициализирован!");
    }
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        System.out.println("➡️ Incoming request: " + req.getMethod() + " " + req.getRequestURI());
        System.out.println("   Headers: " + req.getHeaderNames());

        chain.doFilter(request, response);

        System.out.println("⬅️ Response status: " + res.getStatus());
    }
}