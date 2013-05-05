package com.incidences;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.incidences.entities.IncidencesDao;
import com.incidences.entities.IncidencesDaoImplJdo;

/**
 * @author toni
 * Servlet that select a list with the incidences. 
 */

public class IncidencesListServlet extends BaseServlet {

	private static final long serialVersionUID = 1L;
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		IncidencesDao incidencesDao = new IncidencesDaoImplJdo();
		req.setAttribute("incidences_list", incidencesDao.allIncicendesList());
		RequestDispatcher rd = getServletContext().getRequestDispatcher(PATH_JSP + "incidences_list.jsp");
		rd.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet (req, resp);
	}
	
}
