package com.google.android.gcm.demo.app;

import static com.google.android.gcm.demo.app.CommonUtilities.DISPLAY_MESSAGE_ACTION;
import static com.google.android.gcm.demo.app.CommonUtilities.EXTRA_MESSAGE;
import static com.google.android.gcm.demo.app.CommonUtilities.SENDER_ID;
import static com.google.android.gcm.demo.app.CommonUtilities.SERVER_URL;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gcm.GCMRegistrar;

/**
 * Main UI for the demo app.
 */
public class DemoActivity extends Activity {

	TextView mDisplay;
	AsyncTask<Void, Void, Void> mRegisterTask;
	private Context mContext;
	private boolean mError = false;
	public static final Integer TIMER_TASK_PERIOD = 10000;
	public static final Integer TIMER_TASK_DELAY = 3000;
	public static final String LIST_TYPE_ARG = "LIST_TYPE_ARG";
	public static String userId = null;
	public static final Integer PENDING = 0;
	public static final Integer ORPHAN = 1;
	public static final Integer CLOSED = 2;
	static final String TAG = DemoActivity.class.getSimpleName();

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = this;
		inicializeUserText();
		/* google login is needed. */
		if (userId == null || !isConnectingToInternet()) {
			setContentView(R.layout.error);
			TextView errorDetail = (TextView) findViewById(R.id.error_detail);
			if (userId == null) {
				errorDetail.setText(getString(R.string.error_login));
			}
			if (!isConnectingToInternet()) {
				errorDetail.append("\n\n" + getString(R.string.error_internet));
			}
			mError = true;
		} else {
			setContentView(R.layout.main);
			TextView myTextViewUser = (TextView) findViewById(R.id.user);
			myTextViewUser.setText(DemoActivity.userId);
			checkNotNull(SERVER_URL, "SERVER_URL");
			checkNotNull(SENDER_ID, "SENDER_ID");
			/* Make sure the device has the proper dependencies. */
			GCMRegistrar.checkDevice(this);
			/*
			 * Make sure the manifest was properly set - comment out this line
			 * while developing the app, then uncomment it when it's ready.
			 */
			GCMRegistrar.checkManifest(this);
			mDisplay = (TextView) findViewById(R.id.display);
			registerReceiver(mHandleMessageReceiver, new IntentFilter(DISPLAY_MESSAGE_ACTION));
			final String regId = GCMRegistrar.getRegistrationId(this);
			if (regId.equals("")) {
				/* Automatically registers application on startup. */
				GCMRegistrar.register(this, SENDER_ID);
			} else {
				/* Device is already registered on GCM, check server. */
				if (GCMRegistrar.isRegisteredOnServer(this)) {
					/* Skips registration. */
					mDisplay.append(getString(R.string.already_registered) + "\n");
				} else {
					/*
					 * Try to register again, but not in the UI thread. It's
					 * also necessary to cancel the thread onDestroy(), hence
					 * the use of AsyncTask instead of a raw thread.
					 */
					final Context context = this;
					mRegisterTask = new AsyncTask<Void, Void, Void>() {
						@Override
						protected Void doInBackground(Void... params) {
							TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
							String number = telephonyManager.getLine1Number();
							boolean registered = ServerUtilities.register(context, regId, number);
							/*
							 * At this point all attempts to register with the
							 * app server failed, so we need to unregister the
							 * device from GCM - the app will try to register
							 * again when it is restarted. Note that GCM will
							 * send an unregistered callback upon completion,
							 * but GCMIntentService.onUnregistered() will ignore
							 * it.
							 */
							if (!registered) {
								GCMRegistrar.unregister(context);
							}
							return null;
						}

						@Override
						protected void onPostExecute(Void result) {
							mRegisterTask = null;
						}
					};
					mRegisterTask.execute(null, null, null);
				}
			}
			Button button = (Button) findViewById(R.id.button_my_incidences_opened);
			button.setOnClickListener(this.createShowListListener(PENDING));
			button = (Button) findViewById(R.id.button_orphans);
			button.setOnClickListener(this.createShowListListener(ORPHAN));
			button = (Button) findViewById(R.id.button_my_incidences_closed);
			button.setOnClickListener(this.createShowListListener(CLOSED));
			startService(new Intent(this, MyServiceGpsLocation.class));
		}
	}

	private void inicializeUserText() {
		AccountManager am = AccountManager.get(this);
		Account[] accounts = am.getAccountsByType("com.google");
		if (accounts.length > 0) {
			DemoActivity.userId = accounts[0].name;
		}
	}

	private OnClickListener createShowListListener(final Integer listType) {
		return new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mContext, ActivityList.class);
				intent.putExtra(LIST_TYPE_ARG, listType);
				mContext.startActivity(intent);
			}
		};
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (!mError) {
			MenuInflater inflater = getMenuInflater();
			inflater.inflate(R.menu.options_menu, menu);
		}
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		/*
		 * Typically, an application registers automatically, so options below
		 * are disabled. Uncomment them if you want to manually register or
		 * unregister the device (you will also need to uncomment the equivalent
		 * options on options_menu.xml).
		 */
		case R.id.options_register:
			GCMRegistrar.register(this, SENDER_ID);
			return true;
		case R.id.options_unregister:
			GCMRegistrar.unregister(this);
			return true;
		case R.id.options_clear:
			mDisplay.setText(null);
			return true;
		case R.id.options_exit:
			finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	protected void onDestroy() {
		if (mRegisterTask != null) {
			mRegisterTask.cancel(true);
		}
		try {
			unregisterReceiver(mHandleMessageReceiver);			
			GCMRegistrar.onDestroy(this);
		} catch (Exception e) {
			Log.e(TAG, TAG + "error: " + e.getMessage());
		}
		super.onDestroy();
	}

	private void checkNotNull(Object reference, String name) {
		if (reference == null) {
			throw new NullPointerException(getString(R.string.error_config, name));
		}
	}

	private final BroadcastReceiver mHandleMessageReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String newMessage = intent.getExtras().getString(EXTRA_MESSAGE);
			mDisplay.append(newMessage + "\n");
		}
	};

	public boolean isConnectingToInternet() {
		ConnectivityManager connectivity = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity != null) {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null) {
				for (int i = 0; i < info.length; i++) {
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		}
		return false;
	}
}