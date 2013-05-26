package com.incidences;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityDetail extends Activity {

	protected static final String TAG = ActivityDetail.class.getSimpleName();
	private Context mContext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = this;
		setContentView(R.layout.incidence_detail);
		Intent intent = getIntent();
		if (intent != null) {
			int index = intent.getIntExtra(ActivityList.INDEX_ELEMENT, 0);
			Incidence incidence = ActivityList.getIncidences().get(index);
			TextView textView = (TextView) findViewById(R.id.incidence_date);
			DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.US);
			textView.setText(sdf.format(incidence.getIncidenceDate()));
			textView = (TextView) findViewById(R.id.incidence_name);
			textView.setText(incidence.getContactName());
			textView = (TextView) findViewById(R.id.incidence_phone);
			textView.setText(incidence.getContactPhone());
			textView = (TextView) findViewById(R.id.incidence_address);
			textView.setText(incidence.getIncidenceAddressNoGPS());
			textView = (TextView) findViewById(R.id.incidence_desc);
			textView.setText(incidence.getIncidenceDesc());

			Button button = (Button) findViewById(R.id.button_call_contact);
			button.setOnClickListener(createListenerCall(incidence.getContactPhone()));

			button = (Button) findViewById(R.id.button_map);
			String latitude = incidence.getGpsCoordinates()[0];
			String longitude = incidence.getGpsCoordinates()[1];
			button.setOnClickListener(createListenerMap(latitude, longitude));

			/* filling specific dates for the incidence state. */
			Integer listType = intent.getIntExtra(ActivityMain.LIST_TYPE_ARG, ActivityMain.PENDING);
			textView = (TextView) findViewById(R.id.incidence_detail_title);
			textView.append(": " + incidence.getIdAux().toString());

			Button buttonState = (Button) findViewById(R.id.button_state);

			textView = (TextView) findViewById(R.id.incidence_state);
			TextView textViewCloseDate = (TextView) findViewById(R.id.incidence_close_date);
			LinearLayout linearLayout = (LinearLayout) findViewById(R.id.date_closed_layout);
			if (listType == ActivityMain.PENDING) {
				buttonState.setText(R.string.detail_button_state);
				buttonState.setOnClickListener(this.createListenerCloseIncidence(incidence.getIdAux()));
				textView.setText(getString(R.string.detail_title_open));
				((ViewGroup) linearLayout.getParent()).removeView(linearLayout);
			} else if (listType == ActivityMain.CLOSED) {
				textView.setText(getString(R.string.detail_title_close));
				((ViewGroup) buttonState.getParent()).removeView(buttonState);
				if (incidence.getClosedDate() != null) {
					textViewCloseDate.setText(sdf.format(incidence.getClosedDate()));
				}
			} else if (listType == ActivityMain.ORPHAN) {
				((ViewGroup) linearLayout.getParent()).removeView(linearLayout);
				textView.setText(getString(R.string.detail_title_orphan));
				buttonState.setText(R.string.detail_button_assgnation);
				buttonState.setOnClickListener(this.createListenerAssingIncidence(incidence.getIdAux()));
			}
		}
	}

	private OnClickListener createListenerAssingIncidence(final Long idIncidence) {
		return new OnClickListener() {
			@Override
			public void onClick(View v) {
				DefaultHttpClient httpclient = new DefaultHttpClient();
				HttpPost httpPost = new HttpPost(ActivityMain.getUrlServer() + "/saveUpdateIncidence");
				List<NameValuePair> pairs = new ArrayList<NameValuePair>();
				pairs.add(new BasicNameValuePair("id_technician", ActivityMain.userId));
				pairs.add(new BasicNameValuePair("id_incidence_aux", idIncidence.toString()));
				pairs.add(new BasicNameValuePair("to_assign", "true"));
				try {
					httpPost.setEntity(new UrlEncodedFormEntity(pairs));
					httpclient.execute(httpPost);
				} catch (Exception e) {
					Log.e(TAG, TAG + " Error: " + e.getMessage());
					Toast.makeText(mContext, mContext.getString(R.string.error_updating_incidence), Toast.LENGTH_SHORT).show();
				}
			}
		};
	}
	
	private OnClickListener createListenerCloseIncidence(final Long idIncidence) {
		return new OnClickListener() {
			@Override
			public void onClick(View v) {
				DefaultHttpClient httpclient = new DefaultHttpClient();
				HttpPost httpPost = new HttpPost(ActivityMain.getUrlServer() + "/saveUpdateIncidence");
				List<NameValuePair> pairs = new ArrayList<NameValuePair>();
				pairs.add(new BasicNameValuePair("id_technician", ActivityMain.userId));
				pairs.add(new BasicNameValuePair("id_incidence_aux", idIncidence.toString()));
				pairs.add(new BasicNameValuePair("to_close", "true"));
				try {
					httpPost.setEntity(new UrlEncodedFormEntity(pairs));
					httpclient.execute(httpPost);
				} catch (Exception e) {
					Log.e(TAG, TAG + " Error: " + e.getMessage());
					Toast.makeText(mContext, mContext.getString(R.string.error_updating_incidence), Toast.LENGTH_SHORT).show();
				}
			}
		};
	}

	private OnClickListener createListenerMap(final String latitude, final String longitude) {
		return new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setAction(Intent.ACTION_VIEW);
				String myLatitude = latitude;
				String myLongitude = longitude;
				intent.setData(Uri.parse("geo:" + myLatitude + "," + myLongitude + "?z=18"));
				startActivity(intent);
			}
		};
	}

	private OnClickListener createListenerCall(final String phone) {
		return new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setAction(Intent.ACTION_DIAL);
				intent.setData(Uri.parse("tel:" + phone));
				startActivity(intent);
			}
		};
	}

}
