package com.chughes.cavehunter;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

public class Settings extends PreferenceActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.settings);
		
	}

//	@Override
//	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
//			String key) {
//		Preference connectionPref = findPreference(key);
//        // Set summary to be the user-description for the selected value
//        connectionPref.setSummary(sharedPreferences.getString(key, ""));
//		
//	}
	
	

}
