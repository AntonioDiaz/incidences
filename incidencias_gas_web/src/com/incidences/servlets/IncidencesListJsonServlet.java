package com.incidences.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.incidences.entities.Incidence;
import com.incidences.entities.IncidencesDao;
import com.incidences.entities.IncidencesDaoImplJdo;
import com.incidences.entities.Technician;
import com.incidences.entities.TechnicianDao;
import com.incidences.entities.TechnicianDaoImpJdo;

public class IncidencesListJsonServlet extends BaseServlet {

	private static final long serialVersionUID = 1L;
	public static final Integer PENDING = 0;
	public static final Integer ORPHAN = 1;
	public static final Integer CLOSED = 2;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		doPost(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		IncidencesDao incidencesDao = new IncidencesDaoImplJdo();
		TechnicianDao technicianDao = new TechnicianDaoImpJdo();
		String idTechnician = getParameter(req, "id_technician", "");
		Technician technician = technicianDao.getTechnician(idTechnician);
		Integer listType = Integer.parseInt(getParameter (req, "list_type", "0"));
		List<Incidence> allIncicendesList = incidencesDao.allIncicendesList();
		List<Incidence> listReturn = new ArrayList<Incidence>();
		if (technician!=null) {
			for (Incidence incidence : allIncicendesList) {
				if (listType==ORPHAN) {
					if (incidence.getClosedDate()==null && incidence.getTechnician()==null){
						listReturn.add(incidence);
					}								
				} else if (listType == CLOSED) {
					if (incidence.getClosedDate()!=null && incidence.getTechnician()!=null 
							&& idTechnician.equals(incidence.getTechnician().getGoogleAccountId())) {
						listReturn.add(incidence);
					}				
				} else {
					if (incidence.getClosedDate()==null && incidence.getTechnician()!=null 
							&& technician.getKey().equals(incidence.getTechnician().getKey())) {
						listReturn.add(incidence);
					}
				}
			}
		}
		Gson gson = new Gson();
		String json = gson.toJson(listReturn);  
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
		PrintWriter out = resp.getWriter();
		out.print(json);
		out.flush();
		resp.setStatus(HttpServletResponse.SC_OK);
	}
	
}
