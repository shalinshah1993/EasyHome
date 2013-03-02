package com.cepal.easyhome;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;

import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.ToggleButton;
import com.cepal.easyhome.connection;

public class plug extends Activity implements View.OnClickListener {

	ImageButton ib,ib6,ib2,ib8;
	boolean toggle1 = false;
	boolean toggle2 = false;

	// ToggleButton tb;
	// connection conn = new connection();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.plug);
		setvars();
		setlisteners();
	}

	private void setvars() {

		ib = (ImageButton) findViewById(R.id.imageButton1);
		ib2 = (ImageButton) findViewById(R.id.imageButton2);
		
		ib6 = (ImageButton) findViewById(R.id.imageButton6);
		ib6.setBackgroundColor(Color.WHITE);

		ib8 = (ImageButton) findViewById(R.id.imageButton8);
		ib8.setBackgroundColor(Color.WHITE);
		// b2 = (Button) findViewById(R.id.Button2);
		// tb = (ToggleButton) findViewById(R.id.toggleButton1);
	}

	private void setlisteners() {

		// tb.setOnClickListener(this);
		ib.setOnClickListener(this);
		ib2.setOnClickListener(this);
		//ib6.setOnClickListener(this);
		// b2.setOnClickListener(this);
		// tb.setOnClickListener(this);

	}

	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.imageButton1:

			if (!toggle1) {
				if(isconnection())
				{
					if(isbluetooth())
				connection.write_bt("MD8X4Y1N");
					else
					{
					WifiManager manager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
					WifiInfo info = manager.getConnectionInfo();
					String address = info.getMacAddress();
					delete3.send_msg("MACID = "+address+" "+"DATA= "+"MD8X4Y1N");
					}
				}
				//connection.write_bt("MD8X4Y1N");
				ib6.setBackgroundColor(Color.YELLOW);
				Toast.makeText(this, " 1 ON", Toast.LENGTH_SHORT)
						.show();
				toggle1 = true;

			} else {
				//ib.setImageResource(R.drawable.light_off);
				
				if(isconnection())
				{
					if(isbluetooth())
				connection.write_bt("MD8X4Y2N");
					else
					{
					WifiManager manager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
					WifiInfo info = manager.getConnectionInfo();
					String address = info.getMacAddress();
					delete3.send_msg("MACID = "+address+" "+"DATA= "+"MD8X4Y2N");
					}
				}
					//connection.write_bt("MD8X4Y2N");
				ib6.setBackgroundColor(Color.WHITE);
				Toast.makeText(this, "1 OFF", Toast.LENGTH_SHORT)
						.show();
				toggle1= false;
			}
			break;
			
		case R.id.imageButton2:

			if (!toggle2) {
				if(isconnection())
				{
					if(isbluetooth())
				connection.write_bt("MD8X5Y1N");
					else
					{
					WifiManager manager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
					WifiInfo info = manager.getConnectionInfo();
					String address = info.getMacAddress();
					delete3.send_msg("MACID = "+address+" "+"DATA= "+"MD8X5Y1N");
					}
				}
					//connection.write_bt("MD8X5Y1N");
				ib8.setBackgroundColor(Color.YELLOW);
				Toast.makeText(this, "2 ON", Toast.LENGTH_SHORT)
						.show();
				toggle2 = true;

			} else {
				//ib.setImageResource(R.drawable.light_off);
				
				if(isconnection())
				{
					if(isbluetooth())
				connection.write_bt("MD8X5Y2N");
					else
					{
					WifiManager manager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
					WifiInfo info = manager.getConnectionInfo();
					String address = info.getMacAddress();
					delete3.send_msg("MACID = "+address+" "+"DATA= "+"MD8X5Y2N");
					}
				}
				//connection.write_bt("MD8X5Y2N");
				ib8.setBackgroundColor(Color.WHITE);
				Toast.makeText(this, "2 OFF", Toast.LENGTH_SHORT)
						.show();
				toggle2= false;
			}
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
