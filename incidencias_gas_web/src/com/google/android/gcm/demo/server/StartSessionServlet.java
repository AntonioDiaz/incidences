package com.google.android.gcm.demo.server;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class StartSessionServlet extends BaseServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		/* Should check user and password. */
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/home.jsp");
		rd.forward(req, resp);
	}
}
