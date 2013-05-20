package com.google.android.gcm.demo.app;

import java.util.Date;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

public class MyLocationListener implements LocationListener {


	@Override
	public void onLocationChanged(Location location) {
		Log.d(this.getClass().getName(), "location updated " + location);
		UpdatePositionTimerTask.lastLocation = location;
		UpdatePositionTimerTask.lastLocationTime = new Date();
	}

	@Override
	public void onProviderDisabled(String disabled) {
		Log.d(this.getClass().getName(), "onProviderDisabled " + disabled);
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		Log.d(this.getClass().getName(), "onProviderEnable " + provider);
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		Log.d(this.getClass().getName(), "onStatusChanged " + status);
	}

}
