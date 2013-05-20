package com.incidences.entities;

import java.util.List;

public interface TechnicianDao {

	public List<Technician> getAllTechnicians();

	public Technician create(Technician technician);

	public void delete(Technician technician);
	
	public void updateTechnician(String idAccount, Technician technician);

	public Technician getTechnician(String key);

}
