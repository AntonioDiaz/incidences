package com.incidences.entities;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;

import com.google.appengine.api.datastore.Key;
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
		pm.getFetchPlan().setMaxFetchDepth(2);
		pm.getFetchPlan().setFetchSize(2);
		// pm.getFetchPlan().setMaxFetchDepth(2);
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

	@SuppressWarnings("unchecked")
	@Override
	public Incidence selectIncidence(long parseLong) {
		List<Incidence> incidences = null;
		PersistenceManager pm = PMF.get().getPersistenceManager();
		final Query query = pm.newQuery(Incidence.class);
		query.setFilter("idAux==" + parseLong);
		try {
			incidences = (List<Incidence>) query.execute();
		} finally {
			pm.close();
		}
		return incidences.size() <= 0 ? null : incidences.get(0);
	}

	@Override
	public void update(Key key, Incidence incidenceNew) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Transaction transaction = pm.currentTransaction();
		try {
			transaction.begin();
			Incidence incidence = pm.getObjectById(Incidence.class, key);
			Technician technician = pm.getObjectById(Technician.class, incidenceNew.getTechnician().getKey());
			incidence.setClosedDate(incidenceNew.getClosedDate());
			incidence.setTechnician(technician);
			transaction.commit();
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
			}
			pm.close();
		}
	}

	@Override
	public Incidence selectIncidence(Key key) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Transaction transaction = pm.currentTransaction();
		Incidence incidence = null;
		try {
			transaction.begin();
			incidence = pm.getObjectById(Incidence.class, key);
			transaction.commit();
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
			}
			pm.close();
		}
		return incidence;
	}
}
