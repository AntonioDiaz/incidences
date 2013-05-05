package com.incidences.entities;

import java.util.List;

/**
 * @author toni
 * Manage incidences persistence.
 */
public interface IncidencesDao {

	/**
	 * 
	 * @return
	 */
	public List<Incidence> allIncicendesList();
	
	
	/**
	 * 
	 * @return
	 */
	public Incidence create(Incidence incidence);
	
	
	/**
	 * 
	 * @param incidence
	 */
	public void delete(Incidence incidence);
	
}
