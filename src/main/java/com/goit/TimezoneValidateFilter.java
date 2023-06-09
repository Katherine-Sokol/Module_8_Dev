package com.goit;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.ZoneId;

@WebFilter(value = "/time")
public class TimezoneValidateFilter extends HttpFilter {
    @Override
    public void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        String timezone = req.getParameter("timezone");
        if (!req.getParameterMap().containsKey("timezone") || validateTimezone(timezone)) {
            chain.doFilter(req, res);
        }
        if (!validateTimezone(timezone)) {
            res.setStatus(400);
            res.setContentType("text/html; charset=utf-8");
            res.getWriter().write("Invalid timezone");
            res.getWriter().close();
        }
    }

    private boolean validateTimezone(String timezone) {
        if (timezone == null || "".equals(timezone)) {
            return false;
        }
        try {
            ZoneId.of(timezone.replace(" ", "+"));
        } catch (RuntimeException e) {
            return false;
        }
        return true;
    }
}
