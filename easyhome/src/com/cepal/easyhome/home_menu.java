package com.cepal.easyhome;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.PowerManager;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class home_menu extends Activity implements View.OnClickListener {
	/** Called when the activity is first created. */

	ImageButton ib1, ib2, ib3, ib4, ib5, ib6,ib7,ib8 ,ib9;
	PowerManager.WakeLock wl;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		if(getlayout()==2)
		{
			Log.d("layout", "2 --> autoscroll");
			
			Intent a1 = new Intent("com.cepal.easyhome.FLIPPER");
			Log.d("layout", "2 --> autoscroll");
			startActivity(a1);	
			Log.d("layout", "2 --> autoscroll");
			finish();
		}
		else
		{
			Log.d("layout", "1 -->no  autoscroll");
			setContentView(R.layout.home_menu);
			Log.d("layout", "in");
			setvars();
			setlisteners();
		}
		

	}

	private void setvars() {

		ib1 = (ImageButton) findViewById(R.id.imageButton1);
		ib2 = (ImageButton) findViewById(R.id.imageButton2);
		ib3 = (ImageButton) findViewById(R.id.imageButton3);
		ib4 = (ImageButton) findViewById(R.id.imageButton4);
		ib5 = (ImageButton) findViewById(R.id.imageButton5);
		ib6 = (ImageButton) findViewById(R.id.imageButton6);
		ib7 = (ImageButton) findViewById(R.id.imageButton7);
		ib8 = (ImageButton) findViewById(R.id.imageButton8);
		ib9 = (ImageButton) findViewById(R.id.imageButton9);
		ib1.setBackgroundColor(Color.BLACK);
		ib2.setBackgroundColor(Color.BLACK);
		ib3.setBackgroundColor(Color.BLACK);
		ib4.setBackgroundColor(Color.BLACK);
		ib5.setBackgroundColor(Color.BLACK);
		ib6.setBackgroundColor(Color.BLACK);
		ib7.setBackgroundColor(Color.BLACK);
		ib8.setBackgroundColor(Color.BLACK);
		ib9.setBackgroundColor(Color.BLACK);

	}

	private void setlisteners() {

		ib1.setOnClickListener(this);
		ib2.setOnClickListener(this);
		ib3.setOnClickListener(this);
		ib4.setOnClickListener(this);
		ib5.setOnClickListener(this);
		ib6.setOnClickListener(this);
		ib7.setOnClickListener(this);
		ib8.setOnClickListener(this);
		ib9.setOnClickListener(this);
	}

	private int  getlayout() {
		SharedPreferences sp = PreferenceManager
				.getDefaultSharedPreferences(getBaseContext());

		Boolean auto = sp.getBoolean("checkbox", false);
		int  no;
		if (auto) {
			no = 2;

		} else {
			no = 1;
		}
		return no;
	}

	public void onClick(View v) {

		Intent i = null;
		switch(v.getId())
		{
		case R.id.imageButton1:
			i = new Intent("com.cepal.easyhome.LIGHT");
			break;
		case R.id.imageButton2:
			i = new Intent("com.cepal.easyhome.FAN");
			break;
		case R.id.imageButton3:
			i = new Intent("com.cepal.easyhome.AC");
			break;
		case R.id.imageButton4:
			i = new Intent("com.cepal.easyhome.TV");
			break;
		case R.id.imageButton6:
			i = new Intent("com.cepal.easyhome.DVD");
			break;
		case R.id.imageButton5:
			i = new Intent("com.cepal.easyhome.CURTAIN");
			break;
		case R.id.imageButton7:
			i = new Intent("com.cepal.easyhome.TOY");
			break;
		case R.id.imageButton8:
			i = new Intent("com.cepal.easyhome.PLUG");
			break;
		case R.id.imageButton9:
			i = new Intent("com.cepal.easyhome.VOICERECOG");
			break;
		}
		startActivity(i);
	}
	
	public boolean onCreateOptionsMenu(android.view.Menu menu) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu);
		MenuInflater blowUp = getMenuInflater();
		blowUp.inflate(R.menu.home_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
	
		case R.id.settings:
			
			Intent setting = new Intent("com.cepal.easyhome.PREFS");
			startActivity(setting);
			finish();
			break;

		}
		return false;
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		//super.onBackPressed();
		ask();
	}
	
	
	private void ask() {
		DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				//Intent int1 = null;
				switch (which) {
				case DialogInterface.BUTTON_POSITIVE:
					// Yes button clicked
					
					if(isconnection())
					{
						if(isbluetooth())
					connection.write_bt("RESET_ME");
						else
						{
						WifiManager manager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
						WifiInfo info = manager.getConnectionInfo();
						String address = info.getMacAddress();
						delete3.send_msg("MACID = "+address+" "+"DATA= "+"RESET_ME");
						delete3.s_close();
						}

					}
				
					wl.release();
					//Toast.makeText(home_menu.this, "Wake lock :"+wl.isHeld(), Toast.LENGTH_SHORT).show();
					finish();
					break;

				case DialogInterface.BUTTON_NEGATIVE:
					// No button clicked
				
					break;
				}
				
			}
		};
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(
				"Really want to Quit ? ")
				.setPositiveButton("Yes", dialogClickListener)
				.setNegativeButton("No", dialogClickListener).show();

	}

	
	private   boolean isbluetooth() {
		  SharedPreferences sharedData;
		    String image_path = "MySharedString";
		    sharedData = getSharedPreferences(image_path, 0);
		int chck = sharedData.getInt("check2", 1);
		if(chck==2)
			return true;
		else
		return false;
	}
	
	private   boolean isconnection() {
		  SharedPreferences sharedData;
		    String image_path = "MySharedString";
		    sharedData = getSharedPreferences(image_path, 0);
		int chck = sharedData.getInt("check", 1);
		if(chck==2)
			return true;
		else
		return false;
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
		wl = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK,
				"My Tag");
		if(!wl.isHeld())
			wl.acquire();
		
	}
}
