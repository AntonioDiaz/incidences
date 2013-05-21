package com.incidences.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.KeyFactory;
import com.incidences.entities.Incidence;
import com.incidences.entities.IncidencesDao;
import com.incidences.entities.IncidencesDaoImplJdo;

/**
 * 
 * @author toni
 *
 */
public class IncidenceDeleteServlet extends BaseServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		IncidencesDao incidencesDao = new IncidencesDaoImplJdo();
		String keyStr = getParameter(req, "key");
		Incidence incidence = new Incidence();
		incidence.setKey(KeyFactory.stringToKey(keyStr));
		incidencesDao.delete(incidence);
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/incidencesList");
		rd.forward(req, resp);
	}
}
