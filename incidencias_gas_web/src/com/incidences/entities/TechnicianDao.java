package com.incidences.entities;

import java.util.List;

import com.google.appengine.api.datastore.Key;

public interface TechnicianDao {

	public List<Technician> getAllTechnicians();

	public Technician create(Technician technician);

	public void delete(Technician technician);
	
	public void updateTechnician(Key key, Technician technician);

	public Technician getTechnician(Key key);
	
	public Technician getTechnician(String idAccount);

	public Technician updateTechnicianAddIncidence(Key key, Incidence incidence);

}
