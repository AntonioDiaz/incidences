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
import com.incidences.entities.TechnicianDao;
import com.incidences.entities.TechnicianDaoImpJdo;

/**
 * @author toni Servlet that select a list with the incidences.
 */

public class IncidencesListServlet extends BaseServlet {

	private static final long serialVersionUID = 1L;
	
	//private static final Logger logger = Logger.getLogger(Datastore.class.getName());
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		IncidencesDao incidencesDao = new IncidencesDaoImplJdo();
		TechnicianDao technicianDao = new TechnicianDaoImpJdo();
		List<Incidence> allIncicendesList = incidencesDao.allIncicendesList();
		for (Incidence incidence : allIncicendesList) {
			if (incidence.getTechnician()!=null) {
				incidence.setTechnician(technicianDao.getTechnician(incidence.getTechnician().getKey()));
			}
		}
		req.setAttribute("incidences_list", allIncicendesList);
		RequestDispatcher rd = getServletContext().getRequestDispatcher(PATH_JSP + "incidences_list.jsp");

		rd.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

}
