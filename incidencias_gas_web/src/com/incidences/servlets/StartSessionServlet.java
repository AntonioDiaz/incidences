package com.incidences.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class StartSessionServlet extends BaseServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String jspTarget = "home.jsp";
		RequestDispatcher rd = getServletContext().getRequestDispatcher(PATH_JSP + jspTarget);
		rd.forward(req, resp);
	}
}
