package com.incidences.entities;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;

import com.incidences.persistence.PMF;

/**
 * 
 * @author toni
 * 
 *         Implementation of IncidencesDao.
 */
public class IncidencesDaoImplJdo implements IncidencesDao {

	public IncidencesDaoImplJdo() {
		super();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Incidence> allIncicendesList() {
		List<Incidence> incidences = null;
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query query = pm.newQuery(Incidence.class);
		try {
			incidences = (List<Incidence>) query.execute();
		} finally {
			pm.close();
		}
		return incidences;
	}

	@Override
	public Incidence create(Incidence incidence, Technician technician) {
		Incidence incidenceSaved = null;
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			Transaction transaction = pm.currentTransaction();
			transaction.begin();
			if (technician != null) {
				incidence.setTechnician(technician);
			}
			incidenceSaved = pm.makePersistent(incidence);
			transaction.commit();
		} finally {
			pm.close();
		}
		return incidenceSaved;
	}

	@Override
	public void delete(Incidence incidence) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Incidence toDelete = pm.getObjectById(Incidence.class, incidence.getKey());
		try {
			pm.deletePersistent(toDelete);
		} finally {
			pm.close();
		}
	}
}
