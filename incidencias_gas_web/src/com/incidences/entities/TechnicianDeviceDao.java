package com.incidences.entities;

import java.util.List;

public interface TechnicianDeviceDao {

	public List<TechnicianDevice> getAllTechnicians();
	
	public TechnicianDevice create(TechnicianDevice technicianDeviced);
	
	public void delete(TechnicianDevice technicianDeviced);
	
}
