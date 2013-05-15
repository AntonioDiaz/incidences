package com.incidences.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.incidences.entities.TechnicianDao;
import com.incidences.entities.TechnicianImpJdo;


public class TechniciansListServlet extends BaseServlet {

	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		TechnicianDao technicianDeviceDao = new TechnicianImpJdo();
		req.setAttribute("technicians_list", technicianDeviceDao.getAllTechnicians());		
		RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher(PATH_JSP + "thecnicians_list.jsp");
		requestDispatcher.forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}
