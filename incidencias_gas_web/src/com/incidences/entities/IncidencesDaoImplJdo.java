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
		final Query query = pm.newQuery(Incidence.class);
		try {
			incidences = (List<Incidence>) query.execute();
			incidences.size();
		} finally {
			pm.close();
		}
		return incidences;
	}

	@Override
	public Incidence create(Incidence incidence) {
		Incidence incidenceSaved = null;
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Transaction transaction = pm.currentTransaction();
		try {
			transaction.begin();
			incidenceSaved = pm.makePersistent(incidence);
			transaction.commit();
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
			}
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
