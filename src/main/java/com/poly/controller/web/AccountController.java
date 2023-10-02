package com.poly.controller.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = {"/sign-in", "/sign-up","/forget-password"})
public class AccountController extends HttpServlet{

	private static final long serialVersionUID = 7606686516166318682L;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String uri = req.getRequestURI();
		if (uri.contains("sign-in")) {
			req.getRequestDispatcher("/views/web/sign-in.jsp").forward(req, resp);
		} else if (uri.contains("sign-up")) {
			req.getRequestDispatcher("/views/web/sign-up.jsp").forward(req, resp);
		} else if (uri.contains("forget-password")) {
			req.getRequestDispatcher("/views/web/forget-password.jsp").forward(req, resp);
		} 
	}

	
}
