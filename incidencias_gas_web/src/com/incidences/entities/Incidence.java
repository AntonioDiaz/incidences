package com.incidences.entities;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Class that represents an incidence.
 * 
 * @author toni
 * 
 */

@PersistenceCapable
public class Incidence {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;

	@Persistent
	private String contactName;

	@Persistent
	private String contactPhone;

	@Persistent
	private String incidenceAddress;

	@Persistent
	private String incidenceDesc;

	@Persistent
	private Date incidenceDate;

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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

}
