package com.incidences;

import java.io.IOException;

import javax.jdo.PersistenceManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.incidences.entities.Incidence;
import com.incidences.persistence.PMF;

/**
 * 
 * @author toni
 * Class that create and persist a new incicence, then redirect to jsp to show the list of incicences. 
 *
 */

public class CreateIncidenceServlet extends BaseServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		String contactName = getParameter(req, "contact_name");
		String contactPhone = getParameter(req, "contact_phone");
		String incidenceAddress = getParameter(req, "incidence_address");
		String incidenceDesc = getParameter(req, "incidence_details");
		Incidence incidence = new Incidence(contactName, contactPhone, incidenceAddress, incidenceDesc);
		try {
			pm.makePersistent(incidence);
		} finally {
			pm.close();
		}
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/incidences");
		rd.forward(req, resp);
	}
}
