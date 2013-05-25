package com.incidences;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import com.incidences.R;

public class PrefsActivity extends PreferenceActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.prefs);
	}

}