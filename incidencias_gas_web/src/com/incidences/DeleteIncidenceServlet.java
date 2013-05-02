package com.incidences;

import java.io.IOException;

import javax.jdo.PersistenceManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.incidences.entities.Incidence;
import com.incidences.persistence.PMF;

public class DeleteIncidenceServlet extends BaseServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		String keyStr = getParameter(req, "key");
		Key key = KeyFactory.createKey(Incidence.class.getSimpleName(), Long.parseLong(keyStr));
		Incidence toDelete = pm.getObjectById(Incidence.class, key);
		try {
			pm.deletePersistent(toDelete);
		} finally {
			pm.close();
		}		
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/incidences");
		rd.forward(req, resp);
	}
	
}
