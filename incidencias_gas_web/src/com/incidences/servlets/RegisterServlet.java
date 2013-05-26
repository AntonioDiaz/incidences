package com.incidences.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.incidences.entities.Technician;
import com.incidences.entities.TechnicianDao;
import com.incidences.entities.TechnicianDaoImpJdo;


/**
 * Servlet that registers a device, whose registration id is identified by
 * {@link #PARAMETER_REG_ID}.
 * 
 * <p>
 * The client app should call this servlet everytime it receives a
 * {@code com.google.android.c2dm.intent.REGISTRATION C2DM} intent without an
 * error or {@code unregistered} extra.
 */
@SuppressWarnings("serial")
public class RegisterServlet extends BaseServlet {

	private static final String PARAMETER_REG_ID = "regId";

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
		String regId = getParameter(req, PARAMETER_REG_ID);
		String idGoogle = getParameter (req, "account_id", "");
		/* Only user with acces should be registered. */
		TechnicianDao technicianDao = new TechnicianDaoImpJdo();
		Technician technician = technicianDao.getTechnician(idGoogle);
		if (technician!=null){
			technician.setRegistrationGcmId(regId);
			technicianDao.updateTechnician(technician.getKey(), technician);
		}
		Datastore.register(regId);
		setSuccess(resp);
	}
}
