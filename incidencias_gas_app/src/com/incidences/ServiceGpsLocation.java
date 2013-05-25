package com.incidences;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

public class ServiceGpsLocation extends Service {

	private static final String TAG = ServiceGpsLocation.class.getName();

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		LocationListener locationListener = new MyLocationListener();
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 300000, 100, locationListener);
	}

	private void updatePositionOnServer(Location location) {
		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(ActivityMain.getUrlServer() + "/updateLocation");
		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		pairs.add(new BasicNameValuePair("id_technician", ActivityMain.userId));
		pairs.add(new BasicNameValuePair("new_latitude", String.valueOf(location.getLatitude())));
		pairs.add(new BasicNameValuePair("new_longitude", String.valueOf(location.getLongitude())));
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(pairs));
			httpclient.execute(httpPost);
		} catch (IOException e) {
			Log.e(TAG, "Error: " + e.getMessage());
		}
	}

	public class MyLocationListener implements LocationListener {
		@Override
		public void onLocationChanged(Location myLocation) {
			updatePositionOnServer(myLocation);
		}

		@Override
		public void onProviderDisabled(String provider) { }

		@Override
		public void onProviderEnabled(String provider) { }

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) { }
	}
}
