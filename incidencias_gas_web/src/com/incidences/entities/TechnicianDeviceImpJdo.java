package com.incidences.entities;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.incidences.persistence.PMF;

public class TechnicianDeviceImpJdo implements TechnicianDeviceDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<TechnicianDevice> getAllTechnicians() {
		List<TechnicianDevice> technicians = null;
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query query = pm.newQuery(TechnicianDevice.class);
		try {
			technicians = (List<TechnicianDevice>) query.execute();
		} finally {
			pm.close();
		}
		return technicians;
	}

	@Override
	public TechnicianDevice create(TechnicianDevice technicianDeviced) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		TechnicianDevice newTechnician = null;
		try {
			newTechnician = pm.makePersistent(technicianDeviced);
		} finally {
			pm.close();
		}
		return newTechnician;
	}

	@Override
	public void delete(TechnicianDevice technicianDeviced) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			pm.deletePersistent(technicianDeviced);
		} finally  {
			pm.close();
		}
		
	}

}
