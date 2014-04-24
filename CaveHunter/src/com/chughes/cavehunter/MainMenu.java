package com.chughes.cavehunter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;

public class MainMenu extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		PreferenceManager.setDefaultValues(this, R.xml.settings, false);
		
	}
	
	public void play(View v) {
		Intent i = new Intent();
		i.setClass(this, Gameplay.class);
		
	    startActivity(i);

	}
	
	public void settings(View v) {
		Intent i = new Intent();
		i.setClass(this, Settings.class);
		
	    startActivity(i);
	}

}
