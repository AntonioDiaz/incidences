package com.incidences.servlets;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UpdateLocationServlet extends BaseServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(UpdateLocationServlet.class.getName());
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		/* update technician location. */
		String idTechnician = getParameter(req, "id_technician");
		String newLocation = getParameter(req, "new_location");
		logger.info("technician..." + idTechnician);
		logger.info("technician..." + newLocation);
		resp.setStatus(HttpServletResponse.SC_OK);
		
	}
	
}
