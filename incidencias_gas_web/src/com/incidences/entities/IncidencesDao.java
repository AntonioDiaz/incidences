package com.incidences.entities;

import java.util.List;

import com.google.appengine.api.datastore.Key;

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


	public Incidence selectIncidence(long parseLong);


	void update(Key key, Incidence incidence);
	
	public Incidence selectIncidence (Key key);
	
}
