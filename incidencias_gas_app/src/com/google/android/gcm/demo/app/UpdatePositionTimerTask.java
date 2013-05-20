package com.google.android.gcm.demo.app;

import java.util.Date;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.util.Log;
import android.widget.Toast;

public class UpdatePositionTimerTask extends TimerTask {

	private Context context;
	public static Date lastLocationTime;
	public static Location lastLocation;
	
	public UpdatePositionTimerTask(Context context) {
		super();
		Log.d(this.getClass().getName(), "constructor");
		LocationManager mLocationManager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
		LocationListener mLocationListener = new MyLocationListener();
		mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 15, mLocationListener);
		this.context = context;
	}

	@Override
	public void run() {
		((Activity)context).runOnUiThread(new Runnable() {			
			@Override
			public void run() {
				String string = lastLocation + "-" + lastLocationTime;
				Toast.makeText(context, string, Toast.LENGTH_SHORT).show();
			}
		} );
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

}
