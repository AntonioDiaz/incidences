package com.incidences.entities;

import java.util.Date;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.google.appengine.api.datastore.Key;
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
		Technician technicianToDelete = pm.getObjectById(Technician.class, technician.getKey());
		try {
			pm.deletePersistent(technicianToDelete);
		} finally {
			pm.close();
		}
	}

	@Override
	public Technician getTechnician(Key key) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Technician technicianToDelete = null;
		try {
			technicianToDelete = pm.getObjectById(Technician.class, key);
		} finally {
			pm.close();
		}
		return technicianToDelete;
	}

	@Override
	public void updateTechnician(Key key, Technician technician) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
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
