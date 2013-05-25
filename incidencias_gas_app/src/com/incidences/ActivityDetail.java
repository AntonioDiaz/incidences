package com.incidences;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import com.incidences.R;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ActivityDetail extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.incidence_detail);
		Intent intent = getIntent();
		if (intent!=null) {
			int index = intent.getIntExtra(ActivityList.INDEX_ELEMENT, 0);
			Incidence incidence = ActivityList.getIncidences().get(index);
			TextView textView = (TextView)findViewById(R.id.incidence_date);
			DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.US);
			textView.setText(sdf.format(incidence.getIncidenceDate()));
			textView = (TextView)findViewById(R.id.incidence_name);
			textView.setText(incidence.getContactName());
			textView = (TextView)findViewById(R.id.incidence_phone);
			textView.setText(incidence.getContactPhone());			
			textView = (TextView)findViewById(R.id.incidence_address);
			textView.setText(incidence.getIncidenceAddressNoGPS());
			textView = (TextView)findViewById(R.id.incidence_desc);
			textView.setText(incidence.getIncidenceDesc());
			
			Button button = (Button)findViewById(R.id.button_call_contact);
			button.setOnClickListener(createListenerCall(incidence.getContactPhone()));
			
			button = (Button)findViewById(R.id.button_map);			
			String latitude = incidence.getGpsCoordinates()[0]; 
			String longitude = incidence.getGpsCoordinates()[1];
			button.setOnClickListener(createListenerMap(latitude, longitude));
			
			/* filling specific dates for the incidence state.*/
			Integer listType = intent.getIntExtra(ActivityMain.LIST_TYPE_ARG, ActivityMain.PENDING);
			textView = (TextView)findViewById(R.id.incidence_detail_title);
			textView.append(": " + incidence.getIdAux().toString());
			if (listType == ActivityMain.PENDING) {
				
			} else if (listType == ActivityMain.CLOSED) {
				
			} else if (listType == ActivityMain.CLOSED) {
				
			} 
		}
	}

	private OnClickListener createListenerMap(final String latitude, final String longitude) {
		return new OnClickListener() {			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setAction(Intent.ACTION_VIEW);				
				String myLatitude = latitude;
				String myLongitude = longitude;
				intent.setData(Uri.parse ("geo:" + myLatitude  + "," + myLongitude + "?z=18" ));
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
