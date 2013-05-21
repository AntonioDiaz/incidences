package com.incidences.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.KeyFactory;
import com.incidences.entities.Technician;
import com.incidences.entities.TechnicianDao;
import com.incidences.entities.TechnicianDaoImpJdo;

public class TechnicianDeleteServlet extends BaseServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		TechnicianDao technicianDao = new TechnicianDaoImpJdo();
		String keyStr = getParameter(req, "key");
		Technician technician = new Technician();
		technician.setKey(KeyFactory.stringToKey(keyStr));
		technicianDao.delete(technician);
		RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/techniciansList");
		requestDispatcher.forward(req, resp);		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doPost(req, resp);
	}
}
