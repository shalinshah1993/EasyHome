package com.cepal.easyhome;

import android.app.Activity;
import android.app.AlertDialog;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ViewFlipper;
import java.io.File;
import java.io.IOException;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.ImageButton;
import android.widget.Toast;

public class flipper extends Activity implements OnClickListener {

	ViewFlipper flippy;

	ImageButton ib1, ib2, ib3, ib4, ib5, ib6, ib7, ib8 , ib9;

	PowerManager.WakeLock wl;
	SharedPreferences someData;
	public static String image_path = "MySharedString";
	public int sec;
	int ret;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		super.onCreate(savedInstanceState);
		Log.d("flipper", "on create");
		setContentView(R.layout.flipper);
		flippy = (ViewFlipper) findViewById(R.id.viewFlipper1);
		flippy.setOnClickListener(this);
		setvars_8();
		setlisteners_8();
		//setimages_8();

		//Stay awake phone call
		
		if(getsec())
			sec = ret;
		else
			sec = 2;

		flippy.setFlipInterval(sec * 1000);
		flippy.startFlipping();
	}

	// public void onClick(View arg0) {
	// // TODO Auto-generated method stub
	// Log.d("click", "came");
	// flippy.showNext();
	//
	// }

	private boolean getsec() {

		SharedPreferences sp = PreferenceManager
				.getDefaultSharedPreferences(getBaseContext());

		String sec1 = sp.getString("time", "2");
		
		try {
			ret = Integer.parseInt(sec1);
			 return true;
		} catch (NumberFormatException e) {
			
			Toast.makeText(flipper.this,
					"Enter Numeric value in Transition time",
					Toast.LENGTH_SHORT).show();
			return false;
		}
		
			

	}



	private void setlisteners_8() {
		ib1.setOnClickListener(this);
		ib2.setOnClickListener(this);
		ib3.setOnClickListener(this);
		ib4.setOnClickListener(this);
		ib5.setOnClickListener(this);
		ib6.setOnClickListener(this);
		ib7.setOnClickListener(this);
		ib8.setOnClickListener(this);
		ib9.setOnClickListener(this);

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

	private void setvars_8() {

		ib1 = (ImageButton) findViewById(R.id.imageButton1);
		ib2 = (ImageButton) findViewById(R.id.imageButton2);
		ib3 = (ImageButton) findViewById(R.id.imageButton3);
		ib4 = (ImageButton) findViewById(R.id.imageButton4);
		ib5 = (ImageButton) findViewById(R.id.imageButton5);
		ib6 = (ImageButton) findViewById(R.id.imageButton6);
		ib7 = (ImageButton) findViewById(R.id.imageButton7);
		ib8 = (ImageButton) findViewById(R.id.imageButton8);
		ib9 = (ImageButton) findViewById(R.id.imageButton9);
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

	public void onClick(View v) {

		Intent i = null;
		switch(v.getId())
		{
		case R.id.imageButton1:
			Log.d("im1","Sent");
			i = new Intent("com.cepal.easyhome.LIGHT_FLIPPER");
			break;
		case R.id.imageButton2:
			i = new Intent("com.cepal.easyhome.FAN_FLIPPER");
			break;
		case R.id.imageButton3:
			i = new Intent("com.cepal.easyhome.AC_FLIPPER");
			break;
		case R.id.imageButton4:
			i = new Intent("com.cepal.easyhome.TV_FLIPPER");
			break;
		case R.id.imageButton6:
			i = new Intent("com.cepal.easyhome.DVD_FLIPPER");
			break;
		case R.id.imageButton5:
			i = new Intent("com.cepal.easyhome.CURTAIN_FLIPPER");
			break;
		case R.id.imageButton7:
			i = new Intent("com.cepal.easyhome.TOY_FLIPPER");
			break;
		case R.id.imageButton8:
			i = new Intent("com.cepal.easyhome.PLUG_FLIPPER");
			break;
		case R.id.imageButton9:
			i = new Intent("com.cepal.easyhome.VOICERECOG");
			break;
		}
		startActivity(i);
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
						//connection.write_bt("MD6X5Y1N");
				
					wl.release();
					//Toast.makeText(flipper.this, "Wake lock :"+wl.isHeld(), Toast.LENGTH_SHORT).show();
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


	private boolean isconnection() {
		SharedPreferences sharedData;
		String image_path = "MySharedString";
		sharedData = getSharedPreferences(image_path, 0);
		int chck = sharedData.getInt("check", 1);
		if (chck == 2)
			return true;
		else
			return false;
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
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		
	}
}