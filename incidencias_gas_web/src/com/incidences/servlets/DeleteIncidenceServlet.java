package com.incidences.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.incidences.entities.Incidence;
import com.incidences.entities.IncidencesDao;
import com.incidences.entities.IncidencesDaoImplJdo;

public class DeleteIncidenceServlet extends BaseServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		IncidencesDao incidencesDao = new IncidencesDaoImplJdo();
		String keyStr = getParameter(req, "key");
		Incidence incidence = new Incidence();
		incidence.setId(Long.parseLong(keyStr));
		incidencesDao.delete(incidence);
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/incidences");
		rd.forward(req, resp);
	}
	
}
