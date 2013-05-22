package com.incidences.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.incidences.entities.Incidence;
import com.incidences.entities.IncidencesDao;
import com.incidences.entities.IncidencesDaoImplJdo;
import com.incidences.entities.TechnicianDao;
import com.incidences.entities.TechnicianDaoImpJdo;

/**
 * 
 * @author toni Class that create and persist a new incicence, then redirect to
 *         jsp to show the list of incicences.
 * 
 */

public class IncidenceCreateServlet extends BaseServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {		
		IncidencesDao incidencesDao = new IncidencesDaoImplJdo();
		TechnicianDao technicianDao = new TechnicianDaoImpJdo();
		String contactName = getParameter(req, "contact_name");
		String contactPhone = getParameter(req, "contact_phone");
		String incidenceAddress = getParameter(req, "incidence_address");
		String incidenceDesc = getParameter(req, "incidence_details");
		Incidence incidence = new Incidence(contactName, contactPhone, incidenceAddress, incidenceDesc);
		String technicianStr = getParameter(req, "technician", "");
		if (technicianStr.equals("")) {
			incidencesDao.create(incidence);
		} else {
			Key key = KeyFactory.stringToKey(technicianStr);
			technicianDao.updateTechnicianAddIncidence(key, incidence);
		}
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/incidencesList");
		rd.forward(req, resp);
	}
}
