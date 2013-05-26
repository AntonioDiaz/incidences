package com.incidences.entities;

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;

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
			for (Technician technician : technicians) {
				technician.getIncidencesList().size();
			}
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

	@SuppressWarnings("unchecked")
	@Override
	public Technician getTechnician(String idGoogleAccount) {
		List<Technician> technicians = null;
		PersistenceManager pm = PMF.get().getPersistenceManager();
		pm.refreshAll();
		Query query = pm.newQuery(Technician.class);
		query.setFilter("googleAccountId == '"+ idGoogleAccount +"'");
		try {
			technicians = (List<Technician>) query.execute();
		} finally {
			pm.close();
		}
		return technicians.size()>0?technicians.get(0):null;
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
			technicianToUpdate.setGoogleAccountId(technician.getGoogleAccountId());
			technicianToUpdate.setName(technician.getName());
			technicianToUpdate.setPhoneNumber(technician.getPhoneNumber());
			technicianToUpdate.setRegistrationGcmId(technician.getRegistrationGcmId());
			if (technician.getLongitude()!=null && technician.getLatitude()!=null) {
				technicianToUpdate.setLongitude(technician.getLongitude());
				technicianToUpdate.setLatitude(technician.getLatitude());
				Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Europe/Madrid"));
				technicianToUpdate.setLastRegistrationDate(calendar.getTime());
			}
		} finally {
			pm.close();
		}
	}
	
	@Override
	public Technician updateTechnicianAddIncidence(Key key, Incidence incidence) {
		Technician technicianToUpdate = null;
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Transaction transaction = pm.currentTransaction();
		try {
			transaction.begin();
			technicianToUpdate = pm.getObjectById(Technician.class, key);
			technicianToUpdate.getIncidencesList().add(incidence);
			transaction.commit();
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
			}
			pm.close();
		}
		return technicianToUpdate;
	}
	
}
