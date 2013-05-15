package com.incidences.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.incidences.entities.TechnicianDevice;
import com.incidences.entities.TechnicianDeviceDao;
import com.incidences.entities.TechnicianDeviceImpJdo;

public class TechnicianNewServlet extends BaseServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		TechnicianDeviceDao technicianDeviceDao = new TechnicianDeviceImpJdo();
		String googleAccountId = getParameter(req, "id_account");
		String name = getParameter(req, "name");
		String phoneNumber = getParameter(req, "phone");
		TechnicianDevice technicianDeviced = new TechnicianDevice(googleAccountId, name, phoneNumber);
		technicianDeviceDao.create(technicianDeviced);
		RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/technician");
		requestDispatcher.forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}
