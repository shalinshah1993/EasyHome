package com.cepal.easyhome;

import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;

public class Prefs extends PreferenceActivity implements OnItemSelectedListener{

	@Override
	protected void onCreate(Bundle savedInstanceState)  {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.prefs);
	}
	


	@Override
	public void onBackPressed() {
 
		Intent in1 = new Intent("com.cepal.easyhome.HOME_MENU");
		startActivity(in1);
		finish();
	}



	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {

	}



	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}
	
	


}
