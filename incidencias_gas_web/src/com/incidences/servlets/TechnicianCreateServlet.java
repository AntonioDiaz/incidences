package com.incidences.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.incidences.entities.Technician;
import com.incidences.entities.TechnicianDao;
import com.incidences.entities.TechnicianImpJdo;

public class TechnicianCreateServlet extends BaseServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		TechnicianDao technicianDeviceDao = new TechnicianImpJdo();
		String googleAccountId = getParameter(req, "id_account");
		String name = getParameter(req, "name");
		String phoneNumber = getParameter(req, "phone");
		Technician technicianDeviced = new Technician(googleAccountId, name, phoneNumber);
		technicianDeviceDao.create(technicianDeviced);
		RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/techniciansList");
		requestDispatcher.forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}
