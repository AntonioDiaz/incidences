package com.incidences.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.incidences.entities.Technician;
import com.incidences.entities.TechnicianDao;
import com.incidences.entities.TechnicianDaoImpJdo;

public class TechnicianUpdateServlet extends BaseServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		TechnicianDao technicianDao = new TechnicianDaoImpJdo();
		String key = getParameter(req, "key");
		Technician technician = technicianDao.getTechnician(key);
		RequestDispatcher rd = getServletContext().getRequestDispatcher(PATH_JSP + "technicians_update.jsp");
		req.setAttribute("technicianToModify", technician);
		rd.forward(req, resp);
	}	
}
