package com.incidences.entities;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

/**
 * Class that represents an incidence.
 * 
 * @author toni
 * 
 */

@PersistenceCapable(detachable = "true") 
public class Incidence {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;

	@Persistent
	private String contactName;

	@Persistent
	private String contactPhone;

	@Persistent
	private String incidenceAddress;

	@Persistent
	private String incidenceDesc;

	@Persistent (defaultFetchGroup = "true")
	private Technician technician;

	@Persistent
	private Date incidenceDate;
	
	@Persistent
	private Date closedDate;
	
	@Persistent(valueStrategy = IdGeneratorStrategy.SEQUENCE)
	private Long idAux;

	public Incidence(String contactName, String contactPhone, String incidenceAddress, String incidenceDesc) {
		super();
		this.contactName = contactName;
		this.contactPhone = contactPhone;
		this.incidenceAddress = incidenceAddress;
		this.incidenceDesc = incidenceDesc;
		this.incidenceDate = new Date();
	}

	public Incidence() {
		super();
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getIncidenceAddress() {
		return incidenceAddress;
	}

	public void setIncidenceAddress(String incidenceAddress) {
		this.incidenceAddress = incidenceAddress;
	}

	public String getIncidenceDesc() {
		return incidenceDesc;
	}

	public void setIncidenceDesc(String incidenceDesc) {
		this.incidenceDesc = incidenceDesc;
	}

	public Date getIncidenceDate() {
		return incidenceDate;
	}

	public void setIncidenceDate(Date incidenceDate) {
		this.incidenceDate = incidenceDate;
	}

	public String getIncidenceAddressNoGPS() {
		Integer position = this.incidenceAddress.lastIndexOf("(");
		position = position == -1 ? this.incidenceAddress.length() : position;
		return this.incidenceAddress.substring(0, position);
	}

	public String[] getGpsCoordinates() {
		String[] gpsCoordinates = null;
		Integer position = this.incidenceAddress.lastIndexOf("(");
		if (position != -1) {
			String substr = this.incidenceAddress.substring(position + 1, this.incidenceAddress.length());
			gpsCoordinates = substr.split(", ");
		}
		return gpsCoordinates;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	public Technician getTechnician() {
		return technician;
	}

	public void setTechnician(Technician technician) {
		this.technician = technician;
	}

	public Long getIdAux() {
		return idAux;
	}

	public void setIdAux(Long idAux) {
		this.idAux = idAux;
	}

	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}
	
	public String getKeyStr(){
		return KeyFactory.keyToString(key);
	}

	public Date getClosedDate() {
		return closedDate;
	}

	public void setClosedDate(Date closedDate) {
		this.closedDate = closedDate;
	}

}
