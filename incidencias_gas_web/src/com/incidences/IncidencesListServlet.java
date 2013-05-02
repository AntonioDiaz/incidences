package com.incidences;

import java.io.IOException;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.incidences.entities.Incidence;
import com.incidences.persistence.PMF;

/**
 * @author toni
 * Servlet that select a list with the incidences. 
 */

public class IncidencesListServlet extends BaseServlet {

	private static final long serialVersionUID = 1L;
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query query = pm.newQuery(Incidence.class);
		@SuppressWarnings("unchecked")
		List<Incidence> lista = (List<Incidence>)query.execute();
		req.setAttribute("incidences_list", lista);
		RequestDispatcher rd = getServletContext().getRequestDispatcher(PATH_JSP + "incidences_list.jsp");
		rd.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet (req, resp);
	}
	
}
