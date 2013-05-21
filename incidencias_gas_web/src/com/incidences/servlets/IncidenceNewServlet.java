package com.incidences.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.incidences.entities.Technician;
import com.incidences.entities.TechnicianDao;
import com.incidences.entities.TechnicianDaoImpJdo;

public class IncidenceNewServlet extends BaseServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		TechnicianDao technicianDao = new TechnicianDaoImpJdo();
		List<Technician> allTechnicians = technicianDao.getAllTechnicians();
		req.setAttribute("technicians_list", allTechnicians);
		RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher(PATH_JSP + "incidences_new.jsp");
		requestDispatcher.forward(req, resp);
	}
}
