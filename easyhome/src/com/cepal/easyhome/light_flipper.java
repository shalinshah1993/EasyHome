package com.cepal.easyhome;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;

import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.ViewFlipper;

import com.cepal.easyhome.connection;

public class light_flipper extends Activity implements View.OnClickListener {

	ImageButton ib1,ib2;
	boolean toggle = false;
	ViewFlipper flippy;

	

	
	SharedPreferences someData;
	public static String image_path = "MySharedString";
	public int sec;
	int ret;

	// ToggleButton tb;
	// connection conn = new connection();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Log.d("came	00","sad");
		setContentView(R.layout.light_flipper);
		setvars();
		setlisteners();
		
		flippy = (ViewFlipper) findViewById(R.id.viewFlipper1);
		flippy.setOnClickListener(this);
		//setimages_8();

		if(getsec())
			sec = ret;
		else
			sec = 2;

		flippy.setFlipInterval(sec * 1000);
		flippy.startFlipping();

	}
	
	private boolean getsec() {

		SharedPreferences sp = PreferenceManager
				.getDefaultSharedPreferences(getBaseContext());

		String sec1 = sp.getString("time", "2");
		
		try {
			ret = Integer.parseInt(sec1);
			 return true;
		} catch (NumberFormatException e) {
			
			Toast.makeText(light_flipper.this,
					"Enter Numeric value in Transition time",
					Toast.LENGTH_SHORT).show();
			return false;
		}
		
			

	}



	private void setvars() {

		ib1 = (ImageButton) findViewById(R.id.imageButton1);
		ib2 = (ImageButton) findViewById(R.id.imageButton2);
		//ib6.setBackgroundColor(Color.WHITE);
		// b2 = (Button) findViewById(R.id.Button2);
		// tb = (ToggleButton) findViewById(R.id.toggleButton1);
	}

	private void setlisteners() {

		// tb.setOnClickListener(this);
		ib1.setOnClickListener(this);
		ib2.setOnClickListener(this);
		//ib6.setOnClickListener(this);
		// b2.setOnClickListener(this);
		// tb.setOnClickListener(this);

	}

	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.imageButton1:

			if (!toggle) {
				if(isconnection())
				{
					if(isbluetooth())
				connection.write_bt("MD2X4Y1N");
					else
					{
					WifiManager manager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
					WifiInfo info = manager.getConnectionInfo();
					String address = info.getMacAddress();
					delete3.send_msg("MACID = "+address+" "+"DATA= "+"MD2X4Y1N");
					}

				}
				//connection.write_bt("MD2X4Y1N");
			//	ib6.setBackgroundColor(Color.YELLOW);
				Toast.makeText(this, "ON", Toast.LENGTH_SHORT)
						.show();
				toggle = true;

			} else {
				//ib.setImageResource(R.drawable.light_off);
				
				if(isconnection())
				{
					if(isbluetooth())
				connection.write_bt("MD2X4Y2N");
					else
					{
					WifiManager manager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
					WifiInfo info = manager.getConnectionInfo();
					String address = info.getMacAddress();
					delete3.send_msg("MACID = "+address+" "+"DATA= "+"MD2X4Y2N");
					}

				}
				//connection.write_bt("MD2X4Y2N");
				//ib6.setBackgroundColor(Color.WHITE);
				Toast.makeText(this, "OFF", Toast.LENGTH_SHORT)
						.show();
				toggle= false;
			}
			break;
		case R.id.imageButton2:
			
			super.onBackPressed(); 
			break;

		}
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
	

}
