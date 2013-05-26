package com.incidences.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.incidences.entities.Incidence;
import com.incidences.entities.IncidencesDao;
import com.incidences.entities.IncidencesDaoImplJdo;
import com.incidences.entities.Technician;
import com.incidences.entities.TechnicianDao;
import com.incidences.entities.TechnicianDaoImpJdo;


public class TechniciansListServlet extends BaseServlet {

	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		TechnicianDao technicianDeviceDao = new TechnicianDaoImpJdo();
		IncidencesDao incidencesDao = new IncidencesDaoImplJdo();
		List<Technician> allTechnicians = technicianDeviceDao.getAllTechnicians();
		for (Technician technician : allTechnicians) {
			for (Incidence incidence : technician.getIncidencesList()) {
				incidence = incidencesDao.selectIncidence(incidence.getKey());
			}
		}
		req.setAttribute("technicians_list", allTechnicians);		
		RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher(PATH_JSP + "technicians_list.jsp");
		requestDispatcher.forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}
