package com.incidences;

import android.content.Context;
import android.content.Intent;

/** Helper class providing methods and constants common to other classes in the app. */
public final class CommonUtilities {

	/** Base URL of the Demo Server (such as http://my_host:8080/gcm-demo) */
	 
	// static final String SERVER_URL = "http://192.168.1.35:8080/gcm-demo";
	//static final String SERVER_URL = "http://incidenciasgas.appspot.com";
	public static final String SERVER_URL = "http://192.168.1.35:8888";
	
	public static final String SERVER_URL_JSON = "/incidencesJson";
	public static final String SERVER_URL_UPDATE_LOCATION = SERVER_URL + "/updateLocation";
	/** Google API project id registered to use GCM. */
	static final String SENDER_ID = "5804559361";

	/** Tag used on log messages. */
	static final String TAG = "GCMDemo";

	/** Intent used to display a message in the screen. */
	static final String DISPLAY_MESSAGE_ACTION = "com.google.android.gcm.demo.app.DISPLAY_MESSAGE";

	/** Intent's extra that contains the message to be displayed. */
	static final String EXTRA_MESSAGE = "message";

	/**
	 * Notifies UI to display a message.
	 * <p>
	 * This method is defined in the common helper because it's used both by the
	 * UI and the background service.
	 * 
	 * @param context
	 *            application's context.
	 * @param message
	 *            message to be displayed.
	 */
	static void displayMessage(Context context, String message) {
		Intent intent = new Intent(DISPLAY_MESSAGE_ACTION);
		intent.putExtra(EXTRA_MESSAGE, message);
		context.sendBroadcast(intent);
	}
}