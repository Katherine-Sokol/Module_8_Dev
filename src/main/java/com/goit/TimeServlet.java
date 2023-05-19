package com.goit;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@WebServlet(value = "/time")
public class TimeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html; charset=utf-8");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime timeWithZoneId = LocalDateTime.now(getTimezoneParameterFromQuery(req));
        resp.getWriter().write(timeWithZoneId.format(formatter));
        resp.getWriter().close();
    }

    private ZoneId getTimezoneParameterFromQuery(HttpServletRequest req) {
        if (!req.getParameterMap().containsKey("timezone")) {
            return ZoneId.of("UTC");
        } else {
            String timezone = req.getParameter("timezone");
            return ZoneId.of(timezone.replace(" ", "+"));
        }
    }
}
