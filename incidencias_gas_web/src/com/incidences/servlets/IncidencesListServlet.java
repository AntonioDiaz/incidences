package com.incidences.servlets;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.incidences.entities.Delete;
import com.incidences.entities.Incidence;
import com.incidences.entities.IncidencesDao;
import com.incidences.entities.IncidencesDaoImplJdo;
import com.incidences.persistence.PMF;

/**
 * @author toni Servlet that select a list with the incidences.
 */

public class IncidencesListServlet extends BaseServlet {

	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = Logger.getLogger(Datastore.class.getName());
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

		PersistenceManager pm = PMF.get().getPersistenceManager();
		Transaction transaction = pm.currentTransaction();
		try {
			transaction.begin();
			Delete delete = new Delete();
			delete.setNombre("vamos...");
			pm.makePersistent(delete);
			transaction.commit();
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
			}
			pm.close();
		}

		pm = PMF.get().getPersistenceManager();
		final Query query = pm.newQuery(Delete.class);
		List<Delete> listaDeletes = null;
		try {
			listaDeletes = (List<Delete>) query.execute();
			listaDeletes.size();
		} finally {
			pm.close();
		}
		logger.info("Registering " + listaDeletes);
		
		
		
		IncidencesDao incidencesDao = new IncidencesDaoImplJdo();
		List<Incidence> allIncicendesList = incidencesDao.allIncicendesList();
		req.setAttribute("incidences_list", allIncicendesList);
		RequestDispatcher rd = getServletContext().getRequestDispatcher(PATH_JSP + "incidences_list.jsp");

		rd.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

}
