package com.incidences.entities;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.incidences.persistence.PMF;

/**
 * 
 * @author toni
 * 
 * Implementation of IncidencesDao.
 */
public class IncidencesDaoImplJdo implements IncidencesDao {

	public IncidencesDaoImplJdo(){
		super();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Incidence> allIncicendesList() {
		List<Incidence> incidences = null;
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query query = pm.newQuery(Incidence.class);
		try {
			incidences = (List<Incidence>)query.execute();
		} finally {
			pm.close();
		}
		return incidences;
	}

	@Override
	public Incidence create(Incidence incidence) {
		Incidence incidenceSaved = null;
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			incidenceSaved = pm.makePersistent(incidence);
		} finally {
			pm.close();
		}
		return incidenceSaved;
	}

	@Override
	public void delete(Incidence incidence) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Key key = KeyFactory.createKey(Incidence.class.getSimpleName(), incidence.getId());
		Incidence toDelete = pm.getObjectById(Incidence.class, key);
		try {
			pm.deletePersistent(toDelete);
		} finally {
			pm.close();
		}		
	}
}
