<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="jakarta.servlet.http.HttpSession"%>
<%@ page import="jakarta.servlet.ServletException"%>
<%@ page import="java.io.IOException"%>
<%
// Retrieve current session, if exists
if (session != null) {
	session.invalidate(); // Invalidate the session (logout)
}
response.sendRedirect("login.jsp"); // Redirect to login page after logout
%>
