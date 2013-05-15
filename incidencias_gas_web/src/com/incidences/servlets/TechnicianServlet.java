package com.incidences.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.incidences.entities.TechnicianDeviceDao;
import com.incidences.entities.TechnicianDeviceImpJdo;


public class TechnicianServlet extends BaseServlet {

	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		TechnicianDeviceDao technicianDeviceDao = new TechnicianDeviceImpJdo();
		req.setAttribute("technicians_list", technicianDeviceDao.getAllTechnicians());		
		RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher(PATH_JSP + "/thecnicians.jsp");
		requestDispatcher.forward(req, resp);
	}
}
