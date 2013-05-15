package com.incidences.entities;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.incidences.persistence.PMF;

public class TechnicianImpJdo implements TechnicianDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Technician> getAllTechnicians() {
		List<Technician> technicians = null;
		PersistenceManager pm = PMF.get().getPersistenceManager();
		pm.refreshAll();
		Query query = pm.newQuery(Technician.class);
		try {
			pm.refreshAll();
			technicians = (List<Technician>) query.execute();
		} finally {
			pm.close();
		}
		return technicians;
	}

	@Override
	public Technician create(Technician technician) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Technician newTechnician = null;
		try {
			newTechnician = pm.makePersistent(technician);
		} finally {
			pm.close();
		}
		return newTechnician;
	}

	@Override
	public void delete(Technician technician) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Key key = KeyFactory.createKey(Technician.class.getSimpleName(), technician.getId());
		Technician technicianToDelete = pm.getObjectById(Technician.class, key);
		try {
			pm.deletePersistent(technicianToDelete);
			pm.refreshAll();
			//pm.currentTransaction().commit();
		} finally  {
			pm.close();
		}		
	}
}
