package com.incidences.entities;

import java.util.List;

public interface TechnicianDao {

	public List<Technician> getAllTechnicians();
	
	public Technician create(Technician technicianDeviced);
	
	public void delete(Technician technicianDeviced);
	
}
