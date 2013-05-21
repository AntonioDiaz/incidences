package com.incidences.entities;

import java.util.Date;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.incidences.persistence.PMF;

public class TechnicianDaoImpJdo implements TechnicianDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Technician> getAllTechnicians() {
		List<Technician> technicians = null;
		PersistenceManager pm = PMF.get().getPersistenceManager();
		pm.refreshAll();
		Query query = pm.newQuery(Technician.class);
		try {
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
		Key key = KeyFactory.createKey(Technician.class.getSimpleName(), technician.getGoogleAccountId());
		Technician technicianToDelete = pm.getObjectById(Technician.class, key);
		try {
			pm.deletePersistent(technicianToDelete);
		} finally {
			pm.close();
		}
	}

	@Override
	public Technician getTechnician(String idAccount) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Key key = KeyFactory.createKey(Technician.class.getSimpleName(), idAccount);
		Technician technicianToDelete = null;
		try {
			technicianToDelete = pm.getObjectById(Technician.class, key);
		} finally {
			pm.close();
		}
		return technicianToDelete;
	}

	@Override
	public void updateTechnician(String idAccount, Technician technician) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Key key = KeyFactory.createKey(Technician.class.getSimpleName(), idAccount);
		try {
			Technician technicianToUpdate = pm.getObjectById(Technician.class, key);
			technicianToUpdate.setName(technician.getName());
			technicianToUpdate.setPhoneNumber(technician.getPhoneNumber());
			if (technician.getLongitude()!=null && technician.getLatitude()!=null) {
				technicianToUpdate.setLongitude(technician.getLongitude());
				technicianToUpdate.setLatitude(technician.getLatitude());
				technicianToUpdate.setLastRegistrationDate(new Date());
			}
		} finally {
			pm.close();
		}
	}
}
