package com.incidences.entities;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 
 * @author toni Class that represents an device of a technician.
 */

@PersistenceCapable
public class TechnicianDevice {

	public TechnicianDevice(String googleAccountId, String name, String phoneNumber) {
		super();
		this.googleAccountId = googleAccountId;
		this.name = name;
		this.phoneNumber = phoneNumber;
	}

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;

	@Persistent
	private String googleAccountId;

	@Persistent
	private String name;

	@Persistent
	private String phoneNumber;

	@Persistent
	private String latitude;

	@Persistent
	private String longitude;

	@Persistent
	private Date lastRegistrationDate;

	@Persistent
	private String registrationGcmId;
	
	public String getGoogleAccountId() {
		return googleAccountId;
	}

	public void setGoogleAccountId(String googleAccountId) {
		this.googleAccountId = googleAccountId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public Date getLastRegistrationDate() {
		return lastRegistrationDate;
	}

	public void setLastRegistrationDate(Date lastRegistrationDate) {
		this.lastRegistrationDate = lastRegistrationDate;
	}

	public Long getId() {
		return id;
	}

	public String getRegistrationGcmId() {
		return registrationGcmId;
	}

	public void setRegistrationGcmId(String registrationGcmId) {
		this.registrationGcmId = registrationGcmId;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
