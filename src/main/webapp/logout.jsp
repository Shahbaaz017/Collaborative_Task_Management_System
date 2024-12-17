<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="jakarta.servlet.http.*" %>
<%
    HttpSession sessions = request.getSession(false);
    if (sessions != null) {
        sessions.invalidate();
    }
    response.sendRedirect("login.html");
%>
