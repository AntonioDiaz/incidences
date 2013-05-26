package com.incidences.servlets;

import java.io.IOException;
import java.util.Calendar;
import java.util.TimeZone;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.incidences.entities.Incidence;
import com.incidences.entities.IncidencesDao;
import com.incidences.entities.IncidencesDaoImplJdo;
import com.incidences.entities.Technician;
import com.incidences.entities.TechnicianDao;
import com.incidences.entities.TechnicianDaoImpJdo;

public class IncidenceSaveUpdateServlet extends BaseServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		IncidencesDao incidenceDao = new IncidencesDaoImplJdo();
		TechnicianDao technicianDao = new TechnicianDaoImpJdo();
		String toClose = getParameter(req, "to_close", "");
		String toAssign = getParameter(req, "to_assign", "");
		String idIncidenceAux = getParameter(req, "id_incidence_aux", null);
		if (idIncidenceAux != null) {
			Incidence incidence = incidenceDao.selectIncidence(Long.parseLong(idIncidenceAux));
			if (incidence != null) {
				if ("true".equals(toClose)) {
					incidence.setClosedDate(Calendar.getInstance(TimeZone.getTimeZone("Europe/Madrid")).getTime());
				} else if ("true".equals(toAssign)) {
					String idTechnician = getParameter(req, "id_technician", null);
					Technician technician = technicianDao.getTechnician(idTechnician);
					incidence.setTechnician(technician);
				}
				incidenceDao.update(incidence.getKey(), incidence);
			}
			resp.setStatus(HttpServletResponse.SC_OK);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

}
